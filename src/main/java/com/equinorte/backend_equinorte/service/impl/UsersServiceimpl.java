package com.equinorte.backend_equinorte.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.equinorte.backend_equinorte.dto.UsersDto;
import com.equinorte.backend_equinorte.entity.TipoUsuario;
import com.equinorte.backend_equinorte.entity.Users;
import com.equinorte.backend_equinorte.mapper.UsersMapper;
import com.equinorte.backend_equinorte.repository.UsersRepository;
import com.equinorte.backend_equinorte.service.UsersService;

import lombok.SneakyThrows;

@Service
public class UsersServiceimpl implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public UsersDto crearUser(UsersDto userDto) {

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
    public Optional<UsersDto> obtenerUserPorId(Long id) {

        return usersRepository.findByIdUser(id)
                .map(usersMapper::toDto);
    }

    @Override
    public Optional<UsersDto> obtenerUserPorEmail(String email) {

        return usersRepository.findByEmail(email)
                .map(usersMapper::toDto);
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

        Users userExistente = usersRepository.findByIdUser(id)
                .orElseThrow(() ->
                        new Exception("Usuario no encontrado con id: " + id));

        usersMapper.toEntity(userDto, userExistente);

        return usersMapper.toDto(
                usersRepository.save(userExistente)
        );
    }

    @Override
    @SneakyThrows
    public void eliminarUser(Long id) {

        Users userExistente = usersRepository.findByIdUser(id)
                .orElseThrow(() ->
                        new Exception("Usuario no encontrado con id: " + id));

        usersRepository.deleteById(id);
    }

}