package com.example.cmsapi.config.security;

import com.example.cmsapi.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService;

    // Processes the request to check for a valid JWT token and sets the authentication in the context.
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Retrieve the "Authorization" header from the request
        final String authHeader = request.getHeader("Authorization");

        // If the header is missing or does not start with "Bearer ", pass the request along the filter chain
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // Extract JWT from the "Bearer " prefix
            final String jwt = authHeader.substring(7);
            // Extract the username from the JWT
            final String username = jwtService.extractUsername(jwt);

            // Get the current authentication object from the security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // If the username is present and no authentication is set, validate and set authentication
            if (username != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                // Check if the JWT is valid
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Create a new authentication token
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    // Set additional details for the authentication token
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Set the authentication token in the security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // Pass the request and response to the next filter in the chain
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            // Handle exceptions and resolve errors
            handlerExceptionResolver.resolveException(request, response, null, exception);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"UNAUTHORIZED\", \"message\": \"You are not authorized to access this resource.\"}");
            response.getWriter().flush();
        }
    }
}