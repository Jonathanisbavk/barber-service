package com.barbershop.barber_service.controller;

import com.barbershop.barber_service.model.Cita;
import com.barbershop.barber_service.service.CitaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @GetMapping
    public List<Cita> listar() {
        return citaService.listar();
    }

    @GetMapping("/{id}")
    public Cita obtenerPorId(@PathVariable Long id) {
        return citaService.obtenerPorId(id);
    }

    @GetMapping("/barbero/{barberoId}")
    public List<Cita> listarPorBarbero(@PathVariable Long barberoId) {
        return citaService.listarPorBarberoId(barberoId);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Cita> listarPorCliente(@PathVariable Long clienteId) {
        return citaService.listarPorClienteId(clienteId);
    }

    @PostMapping
    public Cita insertar(@RequestBody Cita cita) {
        return citaService.insertar(cita);
    }

    @PutMapping("/{id}")
    public Cita actualizar(@PathVariable Long id, @RequestBody Cita cita) {
        return citaService.actualizar(id, cita);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

