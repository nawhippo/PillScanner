package Nate.PillScanner.Security;
import Nate.PillScanner.Nurse.Nurse;
import Nate.PillScanner.Nurse.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private NurseRepository nurseRepository;


    @Autowired
    private JwtUtil jwtUtil;

    //returns user and jwt info
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Nurse nurse = nurseRepository.findByUsername(loginDto.getUsername());
        if (nurse == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Nurse not found");
        }

        final String jwt = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

        Map<String, Object> response = new HashMap<>();
        response.put("user", nurse);
        response.put("jwt", jwt);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("Logout successful. Please delete the token on the client side.");
    }


//    @GetMapping("/login")
//    public String login() {
//            return "login";
//    }
//

}

