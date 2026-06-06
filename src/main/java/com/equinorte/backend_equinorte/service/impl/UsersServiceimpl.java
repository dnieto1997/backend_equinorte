package com.equinorte.backend_equinorte.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equinorte.backend_equinorte.dto.UsersDto;
import com.equinorte.backend_equinorte.entity.TipoUsuario;
import com.equinorte.backend_equinorte.entity.Users;
import com.equinorte.backend_equinorte.exception.ResourceNotFoundException;
import com.equinorte.backend_equinorte.mapper.UsersMapper;
import com.equinorte.backend_equinorte.repository.UsersRepository;
import com.equinorte.backend_equinorte.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import lombok.SneakyThrows;

@Service
public class UsersServiceimpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Override
    @SneakyThrows
    public UsersDto crearUser(UsersDto userDto) {
        if (userDto.getNombre() == null || userDto.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre no puede estar vacío");
        }

        if (userDto.getNombre().length() < 3 || userDto.getNombre().length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre debe tener entre 3 y 100 caracteres");
        }

        if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email no puede estar vacío");
        }

        if (!userDto.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe ingresar un correo electrónico válido");
        }

        if (userDto.getTipoUsuario() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe seleccionar un tipo de usuario");
        }

        if (usersRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Ya existe un usuario con ese correo electrónico");
        }

        Users user = usersMapper.toEntity(userDto);

        Users userGuardado = usersRepository.save(user);

        return usersMapper.toDto(userGuardado);
    }

    @Override
    public List<UsersDto> listarUsers() {

        List<Users> usuarios = usersRepository.findAll();

        return usuarios.stream()
                .map(usersMapper::toDto)
                .toList();
    }

    @Override
    public UsersDto obtenerUserPorId(Long id) {

        return usersRepository.findById(id)
                .map(usersMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

   @Override
public UsersDto obtenerUserPorEmail(String email) {

    return usersRepository.findByEmail(email)
            .map(usersMapper::toDto)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Usuario no encontrado con email: " + email)
            );
}

    @Override
    public List<UsersDto> listarPorTipoUsuario(TipoUsuario tipoUsuario) {
     

        return usersRepository.findByTipoUsuario(tipoUsuario)
                .stream()
                .map(usersMapper::toDto)
                .toList();
    }

    @Override
    @SneakyThrows
    public UsersDto actualizarUser(Long id, UsersDto userDto) {

        
        Users userExistente = usersRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuario no encontrado con id: " + id
            ));

    if (userDto.getNombre() == null || userDto.getNombre().trim().isEmpty()) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El nombre es obligatorio"
        );
    }

    if (userDto.getEmail() == null || userDto.getEmail().trim().isEmpty()) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El email es obligatorio"
        );
    }

    if (userDto.getTipoUsuario() == null) {
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El tipo de usuario es obligatorio"
        );
    }
    if (usersRepository.findByEmail(userDto.getEmail()).isPresent()) {
    throw new ResponseStatusException(
            HttpStatus.BAD_REQUEST,
            "Ya existe un usuario con ese email"
    );
}

        usersMapper.toEntity(userDto, userExistente);

        return usersMapper.toDto(
                usersRepository.save(userExistente));
    }

    @Override
    public void eliminarUser(Long id) {

        if (!usersRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado con id: " + id);
        }

        usersRepository.deleteById(id);
    }

}