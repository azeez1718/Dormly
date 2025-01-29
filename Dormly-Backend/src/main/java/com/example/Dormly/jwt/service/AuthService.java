package com.example.Dormly.jwt.service;

import com.example.Dormly.constants.Role;
import com.example.Dormly.repository.UserRepository;
import com.example.Dormly.jwt.dto.AuthResponse;
import com.example.Dormly.jwt.dto.LoginDto;
import com.example.Dormly.jwt.dto.RegisterDto;
import com.example.Dormly.jwt.model.Users;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        user.setRole(List.of(Role.USER));
        userRepository.save(user);

        //call the Jwt service to generate a token for the user based off of this information

        HashMap<String,Object> roles = setAdditionalClaims(user);
        String jwt = jwtService.generateToken(user, roles);
        return new AuthResponse(jwt);

    }


    private HashMap<String,Object> setAdditionalClaims(UserDetails userDetails){

        /**
         * getAuthorities() returns a collection of granted authrotities
         * simplegrantedauthority is an impl of granted authority hence why the return type is GrantedAuthority
         * when a class impl an interface the type is always the interface
         * so when we iterate over a collection of grantedauthorities which just contain roles,  we call the getAuthority method
         * the get authority method returns the role as a string from each object
         */
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        HashMap<String,Object> userRoles = new HashMap<>();
        userRoles.put("roles", roles);
        return userRoles;

    }


    public AuthResponse userLogin(LoginDto loginDto) {
       try {
           UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                   loginDto.getEmail(),
                   loginDto.getPassword()

           );
           authenticationManager.authenticate(auth);
           /**
            * we delegate the authentication to our DaoProvider which the provider manager handles
            * the provider manager is an impl of the authentication manager in which it delegates the validation to the correct auth provier
            * the daoprovider needs a usernamepass object in which it uses the userdetailsservice and passwordencoder
            */
       }catch (Exception e){
           throw new BadCredentialsException("Please enter the correct credentials");
       }

       //if we reach this point, then the user credentials were correct.
         UserDetails userDetails = userRepository.findByEmail(loginDto.getEmail())
                 .orElseThrow(()-> new UsernameNotFoundException("user does not exist"));


       //Helper method to set extra claims
       HashMap<String,Object> roles = setAdditionalClaims(userDetails);

         String jwt = jwtService.generateToken(userDetails,roles);
         return new AuthResponse(jwt);
    }
}
