package Nate.PillScanner.Security;

import Nate.PillScanner.Nurse.Nurse;
import Nate.PillScanner.Nurse.NurseRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.authentication.AuthenticationProvider;

@Component
public class NurseAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Nurse nurse = nurseRepository.findByUsername(username);
        if (nurse != null && passwordEncoder.matches(password, nurse.getPassword())) {
            return new UsernamePasswordAuthenticationToken(nurse, null, nurse.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}