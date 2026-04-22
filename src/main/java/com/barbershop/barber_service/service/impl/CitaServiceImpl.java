package com.barbershop.barber_service.service.impl;

import com.barbershop.barber_service.model.Cita;
import com.barbershop.barber_service.repository.CitaRepository;
import com.barbershop.barber_service.service.CitaService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la capa de servicio para la entidad Cita.
 * Gestiona la lógica de negocio relacionada con las citas:
 * creación, edición, eliminación y filtros por barbero o cliente.
 */

// @Service → registra esta clase como bean de lógica de negocio en Spring.
@Service
public class CitaServiceImpl implements CitaService {

    // Repositorio inyectado por constructor — acceso exclusivo a datos de citas.
    private final CitaRepository citaRepository;

    // Inyección por constructor: Spring resuelve automáticamente la dependencia.
    public CitaServiceImpl(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    // @Override → implementa el método definido en la interfaz CitaService.
    @Override
    public List<Cita> listar() {
        // findAll() → SELECT * FROM citas (retorna todas las citas).
        return citaRepository.findAll();
    }

    @Override
    public Cita obtenerPorId(Long id) {
        // Optional.orElse(null) → evita lanzar excepción si no se encuentra la cita.
        return citaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cita> listarPorBarberoId(Long barberoId) {
        // Consulta derivada definida en CitaRepository:
        //   SELECT * FROM citas WHERE barbero_id = ?
        //   Útil para mostrar el calendario de un barbero específico.
        return citaRepository.findByBarberoId(barberoId);
    }

    @Override
    public List<Cita> listarPorClienteId(Long clienteId) {
        // Consulta derivada:
        //   SELECT * FROM citas WHERE cliente_id = ?
        //   Útil para ver el historial de citas de un cliente.
        return citaRepository.findByClienteId(clienteId);
    }

    @Override
    public Cita insertar(Cita cita) {
        // save() con ID null → Hibernate ejecuta INSERT y asigna el ID generado.
        return citaRepository.save(cita);
    }

    @Override
    public Cita actualizar(Long id, Cita cita) {
        Cita existente = citaRepository.findById(id).orElse(null);

        // Verificar existencia antes de actualizar para no crear un registro nuevo.
        if (existente == null) return null;

        // Se actualiza campo por campo sobre el objeto ya gestionado por Hibernate.
        // Esto permite que Hibernate detecte los cambios (dirty checking)
        // y solo genere el UPDATE si algún valor realmente cambió.
        existente.setClienteId(cita.getClienteId());
        existente.setBarberoId(cita.getBarberoId());
        existente.setServicioId(cita.getServicioId());
        existente.setFecha(cita.getFecha());
        existente.setHora(cita.getHora());
        existente.setEstado(cita.getEstado());

        // save() con ID existente → Hibernate ejecuta UPDATE citas SET ... WHERE id = ?
        return citaRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        // DELETE FROM citas WHERE id = ?
        citaRepository.deleteById(id);
    }
}
