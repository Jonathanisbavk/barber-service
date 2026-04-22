package com.barbershop.barber_service.service.impl;

import com.barbershop.barber_service.model.Barbero;
import com.barbershop.barber_service.repository.BarberoRepository;
import com.barbershop.barber_service.service.BarberoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación de la capa de servicio para Barbero.
 * Gestiona la lógica de negocio, incluyendo el manejo
 * de la lista de especialidades (One-to-Many).
 */

// @Service → bean de lógica de negocio registrado en el contexto de Spring.
@Service
public class BarberoServiceImpl implements BarberoService {

    private final BarberoRepository barberoRepository;

    // Inyección por constructor: Spring provee BarberoRepository automáticamente.
    public BarberoServiceImpl(BarberoRepository barberoRepository) {
        this.barberoRepository = barberoRepository;
    }

    @Override
    public List<Barbero> listar() {
        // SELECT * FROM barberos + SELECT * FROM especialidades WHERE barbero_id IN (...)
        // Hibernate hace las dos consultas automáticamente gracias a FetchType.EAGER.
        return barberoRepository.findAll();
    }

    @Override
    public Barbero obtenerPorId(Long id) {
        return barberoRepository.findById(id).orElse(null);
    }

    @Override
    // @Transactional → todas las operaciones de este método ocurren en una sola
    //   transacción de BD. Si algo falla en medio, se hace rollback automático.
    //   Es necesario porque manipulamos dos tablas (barberos + especialidades).
    @Transactional
    public Barbero insertar(Barbero barbero) {
        // Antes de guardar, se establece la referencia inversa en cada especialidad.
        // Sin esto, Hibernate no sabría qué valor poner en la columna "barbero_id".
        // cascade = ALL se encarga de hacer el INSERT en especialidades automáticamente.
        barbero.getEspecialidades().forEach(e -> e.setBarbero(barbero));
        return barberoRepository.save(barbero);
    }

    @Override
    @Transactional
    public Barbero actualizar(Long id, Barbero barbero) {
        Barbero existente = barberoRepository.findById(id).orElse(null);
        if (existente == null) return null;

        // Actualizar campos simples del barbero.
        existente.setNombre(barbero.getNombre());
        existente.setTelefono(barbero.getTelefono());
        existente.setDisponible(barbero.getDisponible());

        // Reemplazar la lista de especialidades:
        // clear() → vacía la lista y, gracias a orphanRemoval = true,
        //   Hibernate ejecutará DELETE en BD para cada especialidad eliminada.
        existente.getEspecialidades().clear();

        // Agregar las nuevas especialidades vinculadas al barbero existente.
        // Se asigna la referencia back (setBarbero) para que Hibernate
        // sepa qué barbero_id usar en el INSERT de cada especialidad.
        barbero.getEspecialidades().forEach(e -> {
            e.setBarbero(existente);
            existente.getEspecialidades().add(e);
        });

        // save() con ID existente → Hibernate ejecuta UPDATE barberos SET ... WHERE id = ?
        // y gestiona los INSERT/DELETE de especialidades por cascade + orphanRemoval.
        return barberoRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        // Al eliminar el barbero, cascade = ALL + ON DELETE CASCADE en la FK
        // garantizan que sus especialidades también se eliminen automáticamente.
        barberoRepository.deleteById(id);
    }
}
