package com.example.project.login.service;

import com.example.project.login.dto.UserDto;
import com.example.project.login.dto.UserLogin;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;



@Service
public class UserService {

    Map<String,UserDto> db = new LinkedHashMap<>();

//    public String addUser(UserDto userDto){
//        db.put(userDto.getEmailId(),userDto);
//        System.out.println(db);
//        return "success";
//    }


    public Map<String,UserDto> addUser(UserDto userDto){

        UserDto userDto1 =new UserDto();
        userDto1.setName(userDto.getName());
        userDto1.setEmailId(userDto.getEmailId());
        userDto1.setPassword(userDto.getPassword());
        try{
            db.put(userDto.getName(),userDto);
            System.out.println(db);
        }catch (Exception e) {
            System.out.println("something went wrong");
        }
        return db;
    }

    public Boolean isPresent(UserLogin userLogin){
        if (db.containsKey(userLogin.getEmailId())){
            UserDto user = db.get(userLogin.getEmailId());
            if (user.getPassword().equals(userLogin.getPassword())){
                return true;
            }
        }
        return false;
    }

    public List<UserDto> getUsers(){
        return db.values().stream().limit(5).collect(Collectors.toList());
    }

    public UserDto getUserName(String name) {
        try {
            if (db.containsKey(name)){

                System.out.println("The Username is Exist");
            } else {
                System.out.println("The Username is not Exist");
            }
        } catch (Exception e) {
            System.out.println("Something went wrong.");
        }

        return db.get(name);
    }




}
