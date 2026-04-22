package com.barbershop.barber_service.controller;

import com.barbershop.barber_service.model.Barbero;
import com.barbershop.barber_service.service.BarberoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el recurso "barberos".
 * Expone los endpoints CRUD que el frontend consume vía Axios.
 * Ruta base: /api/barberos
 */

// @RestController → registra la clase como controlador REST.
//   Cada método retorna datos (JSON) en lugar de vistas HTML.
@RestController

// @RequestMapping("/api/barberos") → prefijo compartido por todos los endpoints.
@RequestMapping("/api/barberos")

// @CrossOrigin → permite peticiones desde cualquier origen (CORS).
//   Necesario para que el frontend en un archivo o puerto distinto
//   pueda comunicarse con este backend sin ser bloqueado por el navegador.
@CrossOrigin
public class BarberoController {

    // Se inyecta la interfaz BarberoService, no la implementación concreta.
    // Esto permite cambiar la implementación sin modificar el controlador.
    private final BarberoService barberoService;

    // Inyección de dependencias por constructor — práctica recomendada en Spring.
    public BarberoController(BarberoService barberoService) {
        this.barberoService = barberoService;
    }

    // GET /api/barberos → lista todos los barberos.
    @GetMapping
    public List<Barbero> listar() {
        return barberoService.listar();
    }

    // GET /api/barberos/{id} → obtiene un barbero por su ID.
    // @PathVariable extrae el segmento {id} de la URL y lo convierte a Long.
    @GetMapping("/{id}")
    public Barbero obtenerPorId(@PathVariable Long id) {
        return barberoService.obtenerPorId(id);
    }

    // POST /api/barberos → crea un nuevo barbero.
    // @RequestBody deserializa el JSON del body al objeto Barbero.
    @PostMapping
    public Barbero insertar(@RequestBody Barbero barbero) {
        return barberoService.insertar(barbero);
    }

    // PUT /api/barberos/{id} → actualiza los datos de un barbero existente.
    //   Recibe el ID en la URL y los nuevos valores en el body JSON.
    @PutMapping("/{id}")
    public Barbero actualizar(@PathVariable Long id, @RequestBody Barbero barbero) {
        return barberoService.actualizar(id, barbero);
    }

    // DELETE /api/barberos/{id} → elimina un barbero por ID.
    // Retorna HTTP 204 No Content: indica éxito sin enviar cuerpo en la respuesta.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        barberoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
