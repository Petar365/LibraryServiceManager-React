package com.example.libraryservicemanager.model.enumeration.converter;

import com.example.libraryservicemanager.model.enumeration.Authority;
import jakarta.persistence.AttributeConverter;

import java.util.stream.Stream;

public class RoleConverter implements AttributeConverter<Authority,String> {

    @Override
    public String convertToDatabaseColumn(Authority authority) {
        if (authority==null){
            return null;
        }
        return authority.getValue();
    }

    @Override
    public Authority convertToEntityAttribute(String s) {
        if (s.equals(null)){
            return null;
        }
        return Stream.of(Authority.values())
                .filter(authority -> authority.getValue().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
