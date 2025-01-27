package com.example.Dormly.exceptions;

public class ProfileNotFoundException extends RuntimeException{

    public ProfileNotFoundException(String message){
        super(message);
    }
}
