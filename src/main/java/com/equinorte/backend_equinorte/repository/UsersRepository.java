package com.equinorte.backend_equinorte.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.equinorte.backend_equinorte.entity.TipoUsuario;
import com.equinorte.backend_equinorte.entity.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByIdUser(Long idUser);

    Optional<Users> findByNombre(String nombre);

    Optional<Users> findByEmail(String email);

    List<Users> findByTipoUsuario(TipoUsuario tipoUsuario);



}