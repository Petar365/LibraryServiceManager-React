package com.example.libraryservicemanager.event;

import com.example.libraryservicemanager.model.UserEntity;
import com.example.libraryservicemanager.model.enumeration.EventType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserEvent {
    private UserEntity user;
    private EventType type;
    private Map<?,?> data;
}
