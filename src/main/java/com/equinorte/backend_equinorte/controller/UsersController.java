package com.equinorte.backend_equinorte.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.equinorte.backend_equinorte.dto.UsersDto;
import com.equinorte.backend_equinorte.entity.TipoUsuario;
import com.equinorte.backend_equinorte.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    //Crear usuario
    @PostMapping("/crear")
    public ResponseEntity<?> registrarUser(@RequestBody UsersDto usersDto) {

        UsersDto usuarioRegistrado = usersService.crearUser(usersDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioRegistrado);
    }

    //Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsersDto>> listarUsers() {
        List<UsersDto> usuarios = usersService.listarUsers();
        return ResponseEntity.ok(usuarios);
    }

    //Buscar usuario por ID
    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<UsersDto> buscarUserPorId(@PathVariable Long id) {

        UsersDto usuario = usersService.obtenerUserPorId(id);

        return ResponseEntity.ok(usuario);
    }

    //Buscar usuario por email
    @GetMapping("/buscar/email/{email}")
    public ResponseEntity<?> buscarUserPorEmail(@PathVariable String email) {

        UsersDto usuario = usersService.obtenerUserPorEmail(email);

        return ResponseEntity.ok(usuario);
    }

    //Buscar usuario por tipo de usuario
    @GetMapping("/tipo/{tipoUsuario}")
    public ResponseEntity<?> listarPorTipoUsuario(@PathVariable String tipoUsuario) {

        TipoUsuario tipo;

        try {
            tipo = TipoUsuario.valueOf(tipoUsuario.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException(

                    "Solo se permite OPERADOR o SUPERVISOR");
        }

        return ResponseEntity.ok(
                usersService.listarPorTipoUsuario(tipo));
    }

    //Actualizar usuario por ID
 @PutMapping("/actualizar/{id}")
public ResponseEntity<UsersDto> actualizarUser(
        @PathVariable Long id,
        @RequestBody UsersDto usersDto) {

    usersDto.setIdUser(id);

    UsersDto usuarioActualizado = usersService.actualizarUser(id, usersDto);

    return ResponseEntity.ok(usuarioActualizado);
}

//Eliminar usuario por ID
 @DeleteMapping("/{id}")
public ResponseEntity<?> eliminarUser(@PathVariable Long id) {

    usersService.eliminarUser(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT)
            .body(Map.of(
                    "status", HttpStatus.NO_CONTENT
            ));
}
}