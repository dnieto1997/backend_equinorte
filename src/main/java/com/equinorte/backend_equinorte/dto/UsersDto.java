package com.equinorte.backend_equinorte.dto;

import com.equinorte.backend_equinorte.entity.TipoUsuario;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

    private Long idUser;

    
    private String nombre;

   
    private String email;

    private TipoUsuario tipoUsuario;

}