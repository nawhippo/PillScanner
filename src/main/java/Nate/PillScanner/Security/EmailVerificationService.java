package Nate.PillScanner.Security;

import Nate.PillScanner.Nurse.Nurse;
import Nate.PillScanner.Nurse.NurseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class EmailVerificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromAddress;

    private static final Logger log = LoggerFactory.getLogger(EmailVerificationService.class);
    @Autowired
    private NurseRepository nurseRepository;
    @Autowired
    private VerifyTokenRepository verifyTokenRepository;

    public void sendVerificationMail(String toEmail, String subject, String content) {
        if (toEmail == null || subject == null || content == null) {
            log.error("sendVerificationMail received null parameter");
            return;
        }
        log.info("Sending email to: {}", toEmail);
        log.info("Email content: {}", content);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Error sending email", e);
        }
    }

    private String generateRandomString(int length) {
        if (length <= 0) {
            log.error("generateRandomString received invalid length: {}", length);
            return "";
        }
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            result.append(characters.charAt(index));
        }

        return result.toString();
    }

    public ResponseEntity<Void> sendEmailWithToken(String nurseEmail, String action) {
        if (nurseEmail == null || nurseEmail.trim().isEmpty()) {
            log.error("sendEmailWithToken received null or empty nurseEmail for action: {}", action);
            return ResponseEntity.badRequest().build();
        }
        log.info("Generating Link for: {}", action);
        String randomStr = generateRandomString(16);
        VerifyToken token = new VerifyToken();
        token.setContent(randomStr);
        verifyTokenRepository.save(token);
        Nurse nurse = nurseRepository.findByEmail(nurseEmail);
        if (nurse == null) {
            log.error("No nurse found with email: {}", nurseEmail);
            return ResponseEntity.notFound().build();
        }

        nurse.setVerifyTokenId(token.getId());

        String urlBase = "https://www.PillScanner.renderat.com/";
        String path = action.equals("recover") ? "reset-password?token=" : "verify-email?token=";
        String url = urlBase + path + randomStr;
        String emailSubject = action.equals("recover") ? "Account Recovery" : "Verify Your Email";
        String emailContent = "Please click on the following link to " + (action.equals("recover") ? "recover your account" : "verify your email") + ": " + url;
        sendVerificationMail(nurseEmail, emailSubject, emailContent);

        return ResponseEntity.ok().build();
    }


    @Transactional
    public ResponseEntity<Void> verifyUser(String token) {
        VerifyToken verifyToken = verifyTokenRepository.findByContent(token);
        if (verifyToken == null) {
            log.error("No verifyToken found with content: {}", token);
            return ResponseEntity.notFound().build();
        }

        Nurse nurse = nurseRepository.findByVerifyTokenId(verifyToken.getId());
        if (nurse == null) {
            log.error("No nurse found with verifyTokenId: {}", verifyToken.getId());
            return ResponseEntity.notFound().build();
        }


        nurse.addRole("ROLE_NURSE");
        nurse.setVerifyTokenId(null);
        nurseRepository.save(nurse);
        verifyTokenRepository.delete(verifyToken);

        return ResponseEntity.ok().build();
    }
}