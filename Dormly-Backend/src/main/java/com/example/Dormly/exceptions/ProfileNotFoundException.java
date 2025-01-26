package com.example.Dormly.exceptions;

import com.example.Dormly.models.Profile;
import org.springframework.security.core.parameters.P;

public class ProfileNotFoundException extends RuntimeException{

    public ProfileNotFoundException(String message){
        super(message);
    }
}
