package com.equinorte.backend_equinorte.service;

import java.util.List;


import com.equinorte.backend_equinorte.dto.UsersDto;
import com.equinorte.backend_equinorte.entity.TipoUsuario;



public interface UsersService {

    UsersDto crearUser(UsersDto user);

    List<UsersDto> listarUsers();

    UsersDto obtenerUserPorId(Long id);

    UsersDto obtenerUserPorEmail(String email);

    List<UsersDto> listarPorTipoUsuario(TipoUsuario tipoUsuario);

    UsersDto actualizarUser(Long id, UsersDto user);

    void eliminarUser(Long id);

}