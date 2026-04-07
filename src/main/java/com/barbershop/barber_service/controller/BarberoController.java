package com.barbershop.barber_service.controller;

import com.barbershop.barber_service.model.Barbero;
import com.barbershop.barber_service.service.BarberoService;
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
@RequestMapping("/api/barberos")
@CrossOrigin
public class BarberoController {

    private final BarberoService barberoService;

    public BarberoController(BarberoService barberoService) {
        this.barberoService = barberoService;
    }

    @GetMapping
    public List<Barbero> listar() {
        return barberoService.listar();
    }

    @GetMapping("/{id}")
    public Barbero obtenerPorId(@PathVariable Long id) {
        return barberoService.obtenerPorId(id);
    }

    @PostMapping
    public Barbero insertar(@RequestBody Barbero barbero) {
        return barberoService.insertar(barbero);
    }

    @PutMapping("/{id}")
    public Barbero actualizar(@PathVariable Long id, @RequestBody Barbero barbero) {
        return barberoService.actualizar(id, barbero);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        barberoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

