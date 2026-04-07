package com.barbershop.barber_service.service.impl;

import com.barbershop.barber_service.model.Servicio;
import com.barbershop.barber_service.service.ServicioService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ArrayList<Servicio> servicios = new ArrayList<>();
    private Long contador = 1L;

    @Override
    public List<Servicio> listar() {
        return servicios;
    }

    @Override
    public Servicio obtenerPorId(Long id) {
        for (Servicio servicio : servicios) {
            if (servicio.getId().equals(id)) {
                return servicio;
            }
        }
        return null;
    }

    @Override
    public Servicio insertar(Servicio servicio) {
        servicio.setId(contador);
        contador++;
        servicios.add(servicio);
        return servicio;
    }

    @Override
    public Servicio actualizar(Long id, Servicio servicio) {
        Servicio existente = obtenerPorId(id);
        if (existente == null) {
            return null;
        }

        existente.setNombre(servicio.getNombre());
        existente.setDescripcion(servicio.getDescripcion());
        existente.setPrecio(servicio.getPrecio());
        existente.setDuracionMinutos(servicio.getDuracionMinutos());

        return existente;
    }

    @Override
    public void eliminar(Long id) {
        servicios.removeIf(item -> item.getId().equals(id));
    }
}

