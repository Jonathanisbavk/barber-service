package com.barbershop.barber_service.service;

import com.barbershop.barber_service.model.Barbero;

import java.util.List;

public interface BarberoService {

    List<Barbero> listar();

    Barbero obtenerPorId(Long id);

    Barbero insertar(Barbero barbero);

    Barbero actualizar(Long id, Barbero barbero);

    void eliminar(Long id);
}

