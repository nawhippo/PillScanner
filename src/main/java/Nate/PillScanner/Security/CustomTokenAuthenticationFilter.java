package Nate.PillScanner.Security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class CustomTokenAuthenticationFilter extends OncePerRequestFilter {

        private JwtUtil jwtUtil;
        private NurseUserDetailsService nurseUserDetailsService;

        public CustomTokenAuthenticationFilter(JwtUtil jwtUtil, NurseUserDetailsService nurseUserDetailsService) {
            this.jwtUtil = jwtUtil;
            this.nurseUserDetailsService = nurseUserDetailsService;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            final String authorizationHeader = request.getHeader("Authorization");
            String path = request.getRequestURI();
            String username = null;
            String jwt = null;
            //bypass jwt process
            if (path.equals("/login") || path.equals("*/logout") || path.equals("/nurse/createNurse")) {
                filterChain.doFilter(request, response);
                return;
            }
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.nurseUserDetailsService.loadUserByUsername(username);

                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);
        }


        private void unauthorizedResponse(ServletResponse response, int statusCode, String message) throws IOException {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(statusCode);
            httpResponse.getWriter().write(message);
        }

        @Override
        public void destroy() {

        }
    }