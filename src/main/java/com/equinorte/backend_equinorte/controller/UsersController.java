package com.equinorte.backend_equinorte.controller;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUser(@RequestBody UsersDto usersDto) {
        try {
            UsersDto usuarioRegistrado = usersService.crearUser(usersDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<UsersDto>> listarUsers() {
        List<UsersDto> usuarios = usersService.listarUsers();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/buscar/id/{id}")
    public ResponseEntity<?> buscarUserPorId(@PathVariable Long id) {

        Optional<UsersDto> usuario =
                usersService.obtenerUserPorId(id);

        return usuario.isPresent()
                ? ResponseEntity.ok(usuario.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado");
    }

    @GetMapping("/buscar/email/{email}")
    public ResponseEntity<?> buscarUserPorEmail(
            @PathVariable String email) {

        Optional<UsersDto> usuario =
                usersService.obtenerUserPorEmail(email);

        return usuario.isPresent()
                ? ResponseEntity.ok(usuario.get())
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Usuario no encontrado");
    }

    @GetMapping("/tipo/{tipoUsuario}")
    public ResponseEntity<List<UsersDto>> listarPorTipoUsuario(
            @PathVariable TipoUsuario tipoUsuario) {

        List<UsersDto> usuarios =
                usersService.listarPorTipoUsuario(tipoUsuario);

        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarUser(
            @PathVariable Long id,
            @RequestBody UsersDto usersDto) {

        try {

            usersDto.setIdUser(id);

            UsersDto usuarioActualizado =
                    usersService.actualizarUser(id, usersDto);

            return ResponseEntity.ok(usuarioActualizado);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUser(@PathVariable Long id) {

        try {

            usersService.eliminarUser(id);

            return ResponseEntity.ok(
                    "Usuario eliminado exitosamente");

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}