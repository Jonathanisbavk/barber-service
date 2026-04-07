package com.barbershop.barber_service.service;

import com.barbershop.barber_service.model.Servicio;

import java.util.List;

public interface ServicioService {

    List<Servicio> listar();

    Servicio obtenerPorId(Long id);

    Servicio insertar(Servicio servicio);

    Servicio actualizar(Long id, Servicio servicio);

    void eliminar(Long id);
}

