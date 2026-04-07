package com.barbershop.barber_service.service.impl;

import com.barbershop.barber_service.model.Barbero;
import com.barbershop.barber_service.service.BarberoService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BarberoServiceImpl implements BarberoService {

    private final ArrayList<Barbero> barberos = new ArrayList<>();
    private Long contador = 1L;

    @Override
    public List<Barbero> listar() {
        return barberos;
    }

    @Override
    public Barbero obtenerPorId(Long id) {
        for (Barbero barbero : barberos) {
            if (barbero.getId().equals(id)) {
                return barbero;
            }
        }
        return null;
    }

    @Override
    public Barbero insertar(Barbero barbero) {
        barbero.setId(contador);
        contador++;
        barberos.add(barbero);
        return barbero;
    }

    @Override
    public Barbero actualizar(Long id, Barbero barbero) {
        Barbero existente = obtenerPorId(id);
        if (existente == null) {
            return null;
        }

        existente.setNombre(barbero.getNombre());
        existente.setEspecialidad(barbero.getEspecialidad());
        existente.setTelefono(barbero.getTelefono());
        existente.setDisponible(barbero.getDisponible());

        return existente;
    }

    @Override
    public void eliminar(Long id) {
        barberos.removeIf(item -> item.getId().equals(id));
    }
}

