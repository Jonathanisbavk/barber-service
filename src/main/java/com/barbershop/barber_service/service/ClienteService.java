package com.barbershop.barber_service.service;

import com.barbershop.barber_service.model.Cliente;

import java.util.List;

public interface ClienteService {

    List<Cliente> listar();

    Cliente obtenerPorId(Long id);

    Cliente insertar(Cliente cliente);

    Cliente actualizar(Long id, Cliente cliente);

    void eliminar(Long id);
}

