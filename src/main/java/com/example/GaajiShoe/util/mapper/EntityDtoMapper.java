package com.example.GaajiShoe.util.mapper;/*  gaajiCode
    99
    09/09/2024
    */


import com.example.GaajiShoe.dto.UserDTO;
import com.example.GaajiShoe.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EntityDtoMapper {
    public UserDTO mapUserToDtoBasic(User user){
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().name());
        userDto.setName(user.getName());
        return userDto;

    }
    //Address to DTO Basic
























}
