package com.example.libraryservicemanager.domain;

import com.example.libraryservicemanager.dto.User;
import com.example.libraryservicemanager.model.exception.ApiException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class LibraryServiceAuthentication extends AbstractAuthenticationToken {

    private static final String PASSWORD_PROTECTED = "[PASSWORD PROTECTED]";
    private static final String EMAIL_PROTECTED = "[EMAIL PROTECTED]";
    private User user;
    private String email;
    private String password;
    private boolean authenticated;

    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new ApiException("You cannot set authentication bro");
    }

    private LibraryServiceAuthentication(String email , String password) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.email= email;
        this.password = password;
        this.authenticated = false;
    }

    private LibraryServiceAuthentication(User user,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user=user;
        this.password = PASSWORD_PROTECTED;
        this.email= EMAIL_PROTECTED;
        this.authenticated = true;
    }

    public static LibraryServiceAuthentication unauthenticated(String email , String password) {
        return new LibraryServiceAuthentication(email,password);
    }

    public static LibraryServiceAuthentication authenticated(User user,Collection<? extends GrantedAuthority> authorities) {
        return new LibraryServiceAuthentication(user,authorities);
    }

    @Override
    public Object getCredentials() {
        return PASSWORD_PROTECTED;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

    public String getPassword(){
        return password;
    }

    public String getEmail(){
        return email;
    }
}
