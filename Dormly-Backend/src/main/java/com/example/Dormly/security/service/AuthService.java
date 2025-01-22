package com.example.Dormly.security.service;

import com.example.Dormly.constants.Role;
import com.example.Dormly.repository.UserRepository;
import com.example.Dormly.security.dto.AuthResponse;
import com.example.Dormly.security.dto.LoginDto;
import com.example.Dormly.security.dto.RegisterDto;
import com.example.Dormly.security.model.Users;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthResponse giveToken(RegisterDto registerDto) {
        /**
         * ensure the email is a valid university email by checking the domain name as Queens mary University
         */

        Optional<UserDetails> checkUserExists = userRepository.findByEmail(registerDto.getEmail());

        if(checkUserExists.isPresent() && !registerDto.getEmail().contains("@qmul.ac.uk")){
            throw new IllegalArgumentException("please check the entered credentials again");
        }

        /**
         *if the user does not exist, convert the registerDto to a userDetails object and save the user
         *hash the incoming password and set a default role for the user
         */

        Users user = new Users(
                registerDto.getFirstname(),
                registerDto.getLastname(),
                registerDto.getEmail()
        );

        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        //call the Jwt service to generate a token for the user based off of this information
        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);

    }


    public AuthResponse userLogin(LoginDto loginDto) {
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        );
        /**
         * we delegate the authentication to our DaoProvider which the provider manager handles
         * the provider manager is an impl of the authentication manager in which it delegates the validation to the correct auth provier
         * the daoprovider needs a usernamepass object in which it uses the userdetailsservice and passwordencoder
         */
         authenticationManager.authenticate(auth);
         UserDetails userDetails = userRepository.findByEmail(loginDto.getEmail())
                 .orElseThrow(()-> new UsernameNotFoundException("user does not exist"));

         String jwt = jwtService.generateToken(userDetails);
         return new AuthResponse(jwt);
    }
}
