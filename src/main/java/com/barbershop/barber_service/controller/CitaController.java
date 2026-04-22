package com.barbershop.barber_service.controller;

import com.barbershop.barber_service.model.Cita;
import com.barbershop.barber_service.service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el recurso "citas".
 * Además del CRUD estándar, expone endpoints para filtrar citas
 * por barbero o por cliente.
 * Ruta base: /api/citas
 */

// @RestController → controlador que retorna datos JSON en cada método.
@RestController

// @RequestMapping("/api/citas") → prefijo de ruta para todos los endpoints.
@RequestMapping("/api/citas")

// @CrossOrigin → habilita CORS para aceptar peticiones del frontend.
@CrossOrigin
public class CitaController {

    // Inyección de la interfaz CitaService — respeta el principio
    // de inversión de dependencias: depender de abstracciones, no de implementaciones.
    private final CitaService citaService;

    // Constructor usado por Spring para resolver e inyectar la dependencia.
    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    // GET /api/citas → retorna todas las citas en la BD.
    @GetMapping
    public List<Cita> listar() {
        return citaService.listar();
    }

    // GET /api/citas/{id} → retorna una cita específica por su ID.
    @GetMapping("/{id}")
    public Cita obtenerPorId(@PathVariable Long id) {
        return citaService.obtenerPorId(id);
    }

    // GET /api/citas/barbero/{barberoId} →
    //   endpoint de filtrado: retorna solo las citas asignadas a ese barbero.
    //   Útil para construir la agenda de un barbero en el frontend.
    @GetMapping("/barbero/{barberoId}")
    public List<Cita> listarPorBarbero(@PathVariable Long barberoId) {
        return citaService.listarPorBarberoId(barberoId);
    }

    // GET /api/citas/cliente/{clienteId} →
    //   retorna el historial de citas de un cliente específico.
    @GetMapping("/cliente/{clienteId}")
    public List<Cita> listarPorCliente(@PathVariable Long clienteId) {
        return citaService.listarPorClienteId(clienteId);
    }

    // POST /api/citas → crea una nueva cita con los datos del body JSON.
    // @RequestBody deserializa el JSON recibido al objeto Cita.
    @PostMapping
    public Cita insertar(@RequestBody Cita cita) {
        return citaService.insertar(cita);
    }

    // PUT /api/citas/{id} → actualiza una cita existente.
    @PutMapping("/{id}")
    public Cita actualizar(@PathVariable Long id, @RequestBody Cita cita) {
        return citaService.actualizar(id, cita);
    }

    // DELETE /api/citas/{id} → elimina la cita con ese ID.
    // HTTP 204 No Content → respuesta estándar para eliminaciones exitosas.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
