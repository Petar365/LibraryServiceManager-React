package com.example.libraryservicemanager.service.impl;

import com.example.libraryservicemanager.cache.CacheStore;
import com.example.libraryservicemanager.domain.RequestContext;
import com.example.libraryservicemanager.dto.User;
import com.example.libraryservicemanager.event.UserEvent;
import com.example.libraryservicemanager.model.ConfirmationEntity;
import com.example.libraryservicemanager.model.CredentialEntity;
import com.example.libraryservicemanager.model.RoleEntity;
import com.example.libraryservicemanager.model.UserEntity;
import com.example.libraryservicemanager.model.enumeration.Authority;
import com.example.libraryservicemanager.model.enumeration.EventType;
import com.example.libraryservicemanager.model.enumeration.LoginType;
import com.example.libraryservicemanager.model.exception.ApiException;
import com.example.libraryservicemanager.repository.ConfirmationRepository;
import com.example.libraryservicemanager.repository.CredentialRepository;
import com.example.libraryservicemanager.repository.RoleRepository;
import com.example.libraryservicemanager.repository.UserRepository;
import com.example.libraryservicemanager.security.SecurityConfig;
import com.example.libraryservicemanager.service.UserService;
import com.example.libraryservicemanager.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

import static com.example.libraryservicemanager.utils.UserUtils.creatUserEntity;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CredentialRepository credentialRepository;
    private final ConfirmationRepository confirmationRepository;
    private final CacheStore<String, Integer> userCache;
    private final ApplicationEventPublisher publisher;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createUser(String firstname, String lastname, String email, String password) {
        var encodedPassword = passwordEncoder.encode(password);
        var userEntity = userRepository.save(createNewUser(firstname, lastname, email));
        var credentialEntity = new CredentialEntity(encodedPassword, userEntity);
        credentialRepository.save(credentialEntity);
        var confirmationEntity = new ConfirmationEntity(userEntity);
        confirmationRepository.save(confirmationEntity);
        publisher.publishEvent(new UserEvent(userEntity, EventType.REGISTRATION, Map.of("key", confirmationEntity.getKey())));
    }

    @Override
    public RoleEntity getRoleName(String name) {
        var role = roleRepository.findByNameIgnoreCase(name);
        return role.orElseThrow(() -> new ApiException("Role not found"));
    }

    @Override
    public void verifyAccount(String key) {
        ConfirmationEntity confirmationEntity = getUserConfirmation(key);
        UserEntity userEntity = getUserEntityByEmail(confirmationEntity.getUserEntity().getEmail());
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
        confirmationRepository.delete(confirmationEntity);
    }

    @Override
    public void updateLoginAttempt(String email, LoginType loginType) {
        var userEntity = getUserEntityByEmail(email);
        RequestContext.setUserId(userEntity.getId());
        switch (loginType) {
            case LOGIN_ATTEMPT -> {
                if (userCache.get(email) == null) {
                    userEntity.setLoginAttempts(0);
                    userEntity.setAccountNonLocked(true);
                }
                userEntity.setLoginAttempts(userEntity.getLoginAttempts() + 1);
                userCache.put(userEntity.getEmail(), userEntity.getLoginAttempts());
                if (userCache.get(userEntity.getEmail()) > 5) {
                    userEntity.setAccountNonLocked(false);
                }
            }
            case LOGIN_SUCCESS -> {
                userEntity.setAccountNonLocked(true);
                userEntity.setLoginAttempts(0);
                userEntity.setLastLogin(LocalDateTime.now());
                userCache.evict(userEntity.getEmail());
            }
        }
        userRepository.save(userEntity);
    }

    @Override
    public User getUserById(Long id) {
        UserEntity userEntity = userRepository.findUserByUserId(String.valueOf(id))
                .orElseThrow(() -> new ApiException("User Not Found"));

        return UserUtils.mapToUserDto(userEntity);
    }

    @Override
    public User getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ApiException("User Not Found"));

        return UserUtils.mapToUserDto(userEntity);
    }


    public CredentialEntity getUserCredentialById(Long userId) {
        var credentialById = credentialRepository.getCredentialsByUserEntityId(userId);
        return credentialById.orElseThrow(() -> new ApiException("Credentials Not Found"));
    }

    private UserEntity getUserEntityByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new ApiException("User Not Found"));
    }

    private ConfirmationEntity getUserConfirmation(String key) {
        return confirmationRepository.findByKey(key).orElse(null);
    }


    private UserEntity createNewUser(String firstname, String lastname, String email) {
        var role = getRoleName(Authority.USER.name());
        return creatUserEntity(firstname, lastname, email, role);
    }

}
