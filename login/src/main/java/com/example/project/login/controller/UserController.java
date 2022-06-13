package com.example.project.login.controller;

import com.example.project.login.dto.UserDto;
import com.example.project.login.dto.UserLogin;
import com.example.project.login.dto.UserResponse;
import com.example.project.login.service.UserService;
import com.example.project.login.service.UserServices;
import com.example.project.login.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;


    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServices userServices;



    @PostMapping("/authenticate")
    public UserResponse authenticate(@RequestBody UserDto userDto) throws Exception{

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getName(),
                            userDto.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        final UserDetails userDetails
                = userServices.loadUserByUsername(userDto.getName());

        final String token =
                jwtUtility.generateToken(userDetails);

        return  new UserResponse(token);
    }




    @PostMapping("/add-user")
    public ResponseEntity register(@RequestBody UserDto userDto){
        Map<String,String> response = new HashMap<>();
        userService.addUser(userDto);
        response.put(userDto.getName()," User has been successfully created!");
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLogin userLogin){
        Map<String,String> response = new HashMap<>();
        response.put(userLogin.getEmailId()," You are not authorized!");
        if(userService.isPresent(userLogin)){
            response.replace(userLogin.getEmailId(), " You are successfully logged in!");
        }
        return ResponseEntity.status(404).body(response);
    }

    @GetMapping("/get-users")
    public ResponseEntity getUsers(){
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @GetMapping("/getUsers/{name}")
    public UserDto getUserName(@PathVariable String name){
        System.out.println(name);
//        userService.getUserName(name);

        return userService.getUserName(name);
    }



}
