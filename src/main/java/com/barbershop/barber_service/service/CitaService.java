package com.barbershop.barber_service.service;

import com.barbershop.barber_service.model.Cita;

import java.util.List;

public interface CitaService {

    List<Cita> listar();

    Cita obtenerPorId(Long id);

    List<Cita> listarPorBarberoId(Long barberoId);

    List<Cita> listarPorClienteId(Long clienteId);

    Cita insertar(Cita cita);

    Cita actualizar(Long id, Cita cita);

    void eliminar(Long id);
}

