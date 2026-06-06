package com.equinorte.backend_equinorte.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.equinorte.backend_equinorte.dto.UsersDto;
import com.equinorte.backend_equinorte.entity.Users;

@Component
public class UsersMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Users toEntity(UsersDto usersDto) {

        return modelMapper.map(usersDto, Users.class);

    }

    public void toEntity(UsersDto usersDto, Users userExistente) {

        modelMapper.map(usersDto, userExistente);

    }

    public UsersDto toDto(Users user) {

        return modelMapper.map(user, UsersDto.class);

    }

}