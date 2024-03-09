package Nate.PillScanner.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private NurseAuthenticationProvider authProvider;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private NurseUserDetailsService nurseUserDetailsService;

    @Bean
    public CustomTokenAuthenticationFilter customTokenAuthenticationFilter() {
        return new CustomTokenAuthenticationFilter(jwtUtil, nurseUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //shouldn't be able to access anything except gets unless you are logged in.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                //for demo purposes
                .requestMatchers(HttpMethod.GET, "/**").permitAll()
                .requestMatchers("/login","/error", "/logout", "/nurse/createNurse").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .and()
                .formLogin().disable();

        http.addFilterBefore(new CustomTokenAuthenticationFilter(jwtUtil, nurseUserDetailsService), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authProvider);

        http.cors().configurationSource(request -> {
            var cors = new org.springframework.web.cors.CorsConfiguration();
            cors.setAllowedOrigins(java.util.List.of("https://pillscanner-frontend.onrender.com/"));
            cors.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            cors.setAllowedHeaders(java.util.List.of("Authorization", "Content-Type", "Accept"));
            cors.setAllowCredentials(true);
            return cors;
        });

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}