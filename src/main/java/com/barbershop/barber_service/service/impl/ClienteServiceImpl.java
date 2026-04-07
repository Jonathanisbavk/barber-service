package com.barbershop.barber_service.service.impl;

import com.barbershop.barber_service.model.Cliente;
import com.barbershop.barber_service.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ArrayList<Cliente> clientes = new ArrayList<>();
    private Long contador = 1L;

    @Override
    public List<Cliente> listar() {
        return clientes;
    }

    @Override
    public Cliente obtenerPorId(Long id) {
        for (Cliente cliente : clientes) {
            if (cliente.getId().equals(id)) {
                return cliente;
            }
        }
        return null;
    }

    @Override
    public Cliente insertar(Cliente cliente) {
        cliente.setId(contador);
        contador++;
        clientes.add(cliente);
        return cliente;
    }

    @Override
    public Cliente actualizar(Long id, Cliente cliente) {
        Cliente existente = obtenerPorId(id);
        if (existente == null) {
            return null;
        }

        existente.setNombre(cliente.getNombre());
        existente.setTelefono(cliente.getTelefono());
        existente.setEmail(cliente.getEmail());

        return existente;
    }

    @Override
    public void eliminar(Long id) {
        clientes.removeIf(item -> item.getId().equals(id));
    }
}

