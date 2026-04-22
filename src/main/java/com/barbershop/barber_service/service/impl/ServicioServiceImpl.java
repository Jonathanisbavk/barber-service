package com.barbershop.barber_service.service.impl;

import com.barbershop.barber_service.model.Servicio;
import com.barbershop.barber_service.repository.ServicioRepository;
import com.barbershop.barber_service.service.ServicioService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioServiceImpl(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    @Override
    public List<Servicio> listar() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio obtenerPorId(Long id) {
        return servicioRepository.findById(id).orElse(null);
    }

    @Override
    public Servicio insertar(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio actualizar(Long id, Servicio servicio) {
        Servicio existente = servicioRepository.findById(id).orElse(null);
        if (existente == null) return null;

        existente.setNombre(servicio.getNombre());
        existente.setDescripcion(servicio.getDescripcion());
        existente.setPrecio(servicio.getPrecio());
        existente.setDuracionMinutos(servicio.getDuracionMinutos());

        return servicioRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        servicioRepository.deleteById(id);
    }
}
