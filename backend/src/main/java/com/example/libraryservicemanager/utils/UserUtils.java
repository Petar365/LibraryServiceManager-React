package com.example.libraryservicemanager.utils;

import com.example.libraryservicemanager.dto.User;
import com.example.libraryservicemanager.model.CredentialEntity;
import com.example.libraryservicemanager.model.RoleEntity;
import com.example.libraryservicemanager.model.UserEntity;
import java.time.LocalDateTime;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

public class UserUtils {
    public static UserEntity creatUserEntity(String firstName, String lastName, String email, RoleEntity role){
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .lastLogin(LocalDateTime.now())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .mfa(false)
                .enabled(false)
                .loginAttempts(0)
                .qrCodeSecret(StringUtils.EMPTY)
                .phone(StringUtils.EMPTY)
                .bio(Strings.EMPTY)
                .imageUrl("https://static.vecteezy.com/system/resources/thumbnails/009/292/244/small/default-avatar-icon-of-social-media-user-vector.jpg")
                .role(role)
                .build();
    }


    public static User mapToUserDto(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .bio(userEntity.getBio())
                .imageUrl(userEntity.getImageUrl())
                .qrCodeImgUrl(userEntity.getQrCodeImgUrl())
                .lastLogin(userEntity.getLastLogin() != null ? userEntity.getLastLogin().toString() : null)
                .createdAt(userEntity.getCreatedAt() != null ? userEntity.getCreatedAt().toString() : null)
                .updatedAt(userEntity.getUpdatedAt() != null ? userEntity.getUpdatedAt().toString() : null)
                .role(userEntity.getRole() != null ? userEntity.getRole().getName() : null)
                .accountNonExpired(userEntity.isAccountNonExpired())
                .accountNonLocked(userEntity.isAccountNonLocked())
                .credentialsNonExpired(true)
                .enabled(userEntity.isEnabled())
                .mfa(userEntity.isMfa())
                .loginAttempts(userEntity.getLoginAttempts())
                .build();
    }

    public static boolean isCredentialsNonExpired(CredentialEntity credentialEntity){
        return credentialEntity.getUpdatedAt().plusDays(90).isAfter(LocalDateTime.now());
    }
}
