package com.example.libraryservicemanager.service;


import com.example.libraryservicemanager.dto.User;
import com.example.libraryservicemanager.model.CredentialEntity;
import com.example.libraryservicemanager.model.RoleEntity;
import com.example.libraryservicemanager.model.enumeration.LoginType;

public interface UserService {

    void createUser(String firstname,String lastname,String email,String password);

    RoleEntity getRoleName(String name);

    void verifyAccount(String key);

    void updateLoginAttempt(String email, LoginType loginType);

    User getUserById(Long id);

    User getUserByEmail(String email);

    CredentialEntity getUserCredentialById(Long id);

}
