package Nate.PillScanner.Security;

import Nate.PillScanner.Nurse.Nurse;
import Nate.PillScanner.Nurse.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NurseUserDetailsService implements UserDetailsService {

    @Autowired
    private NurseRepository nurseRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Nurse nurse = nurseRepository.findByUsername(username);
        if (nurse == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new User(nurse.getUsername(), nurse.getPassword(), nurse.getAuthorities());
    }



}