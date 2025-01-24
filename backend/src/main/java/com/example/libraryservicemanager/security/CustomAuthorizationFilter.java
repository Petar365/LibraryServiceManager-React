package com.example.libraryservicemanager.security;

import com.example.libraryservicemanager.domain.TokenData;
import com.example.libraryservicemanager.domain.UserPrincipal;
import com.example.libraryservicemanager.dto.User;
import com.example.libraryservicemanager.model.CredentialEntity;
import com.example.libraryservicemanager.service.JwtService;
import com.example.libraryservicemanager.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Optional<String> tokenOptional = jwtService.extractToken(request, "ACCESS_TOKEN");

        if (tokenOptional.isPresent()) {
            String token = tokenOptional.get();
            try {
                TokenData tokenData = jwtService.getTokenData(token, data -> data);

                if (tokenData.isValid()) {
                    User user = tokenData.getUser();
                    CredentialEntity credentials = userService.getUserCredentialById(user.getId());

                    UserPrincipal userPrincipal = new UserPrincipal(user, credentials);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new Exception("Token is invalid or expired.");
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: " + e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
