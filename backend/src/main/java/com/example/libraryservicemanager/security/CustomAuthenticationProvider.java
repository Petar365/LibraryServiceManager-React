package com.example.libraryservicemanager.security;

import com.example.libraryservicemanager.domain.LibraryServiceAuthentication;
import com.example.libraryservicemanager.domain.UserPrincipal;
import com.example.libraryservicemanager.dto.User;
import com.example.libraryservicemanager.model.exception.ApiException;
import com.example.libraryservicemanager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    private final BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var apiAuthentication = authenticationfunction.apply(authentication);
        User user = userService.getUserByEmail(apiAuthentication.getEmail());
        if (user != null) {
            var userCredential = userService.getUserCredentialById(user.getId());
            if (userCredential.getUpdatedAt().minusDays(90).isAfter(LocalDateTime.now())) {
                throw new ApiException("Credentials Expired");
            }
            var userPrincipal = new UserPrincipal(user, userCredential);
            validAccount.accept(userPrincipal);
            if (encoder.matches(apiAuthentication.getPassword(), userCredential.getPassword())) {
                return LibraryServiceAuthentication.authenticated(user, userPrincipal.getAuthorities());
            } else throw new BadCredentialsException("Email or Password incorrect");
        }
        throw new ApiException("Unable to authenticate");
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return LibraryServiceAuthentication.class.isAssignableFrom(authentication);
    }


    private final Function<Authentication, LibraryServiceAuthentication> authenticationfunction = authentication -> (LibraryServiceAuthentication) authentication;

    private final Consumer<UserPrincipal> validAccount = userPrincipal -> {

        if (!userPrincipal.isAccountNonLocked()) {
            throw new LockedException("Your account is locked");
        }
        if (!userPrincipal.isEnabled()) {
            throw new DisabledException("Your account is disabled");
        }
        if (!userPrincipal.isAccountNonExpired()) {
            throw new CredentialsExpiredException("Your password has expired. Update your password");
        }
        if (!userPrincipal.isCredentialsNonExpired()) {
            throw new DisabledException("Your account is expired. Contact administrator");
        }

    };
}