package com.barbershop.barber_service.service.impl;

import com.barbershop.barber_service.model.Cita;
import com.barbershop.barber_service.service.CitaService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaServiceImpl implements CitaService {

    private final ArrayList<Cita> citas = new ArrayList<>();
    private Long contador = 1L;

    @Override
    public List<Cita> listar() {
        return citas;
    }

    @Override
    public Cita obtenerPorId(Long id) {
        for (Cita cita : citas) {
            if (cita.getId().equals(id)) {
                return cita;
            }
        }
        return null;
    }

    @Override
    public List<Cita> listarPorBarberoId(Long barberoId) {
        return citas.stream()
                .filter(cita -> cita.getBarberoId().equals(barberoId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Cita> listarPorClienteId(Long clienteId) {
        return citas.stream()
                .filter(cita -> cita.getClienteId().equals(clienteId))
                .collect(Collectors.toList());
    }

    @Override
    public Cita insertar(Cita cita) {
        cita.setId(contador);
        contador++;
        citas.add(cita);
        return cita;
    }

    @Override
    public Cita actualizar(Long id, Cita cita) {
        Cita existente = obtenerPorId(id);
        if (existente == null) {
            return null;
        }

        existente.setClienteId(cita.getClienteId());
        existente.setBarberoId(cita.getBarberoId());
        existente.setServicioId(cita.getServicioId());
        existente.setFecha(cita.getFecha());
        existente.setHora(cita.getHora());
        existente.setEstado(cita.getEstado());

        return existente;
    }

    @Override
    public void eliminar(Long id) {
        citas.removeIf(item -> item.getId().equals(id));
    }
}

