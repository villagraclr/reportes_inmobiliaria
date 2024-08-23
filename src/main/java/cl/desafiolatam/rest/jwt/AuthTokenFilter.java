package cl.desafiolatam.rest.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import cl.desafiolatam.rest.services.impl.UserDetailsServiceImpl;

import java.io.IOException;
import java.util.logging.Logger;

public class AuthTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = Logger.getLogger(AuthTokenFilter.class.getName());

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info("Calling Auth token filter for url --> " + request.getRequestURI());

        String jwtToken = jwtUtils.getJwtDetailsFromHeader(request);

        if (jwtToken != null && jwtUtils.validateJwtToken(jwtToken)) {
            String userName = jwtUtils.extractUserNameFromJwtToken(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            logger.info("Token Roles extracted from JWT --> " + userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
            logger.info("Jwt token seems invalid.");
        }

        filterChain.doFilter(request, response);
    }
}
