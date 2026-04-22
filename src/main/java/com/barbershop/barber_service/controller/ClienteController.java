package com.barbershop.barber_service.controller;

import com.barbershop.barber_service.model.Cliente;
import com.barbershop.barber_service.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para el recurso "clientes".
 * Expone los endpoints HTTP que el frontend consume vía Axios.
 * Ruta base: /api/clientes
 */

// @RestController → combina @Controller + @ResponseBody.
//   - @Controller: registra la clase como componente web de Spring MVC.
//   - @ResponseBody: todos los métodos retornan objetos serializados a JSON
//     automáticamente por Jackson (no se buscan vistas HTML).
@RestController

// @RequestMapping("/api/clientes") → prefijo de ruta para todos los endpoints
//   de este controlador. Sigue la convención REST de versionar con "/api/".
@RequestMapping("/api/clientes")

// @CrossOrigin → habilita CORS (Cross-Origin Resource Sharing) para este controlador.
//   Sin esto, el navegador bloquea las peticiones del frontend (localhost:3000 o
//   archivo local) al backend (localhost:8080) por política de seguridad Same-Origin.
//   En producción conviene restringirlo a dominios específicos.
@CrossOrigin
public class ClienteController {

    // Dependencia al servicio — se trabaja contra la interfaz (no la implementación)
    // para respetar el principio de inversión de dependencias (DIP/SOLID).
    private final ClienteService clienteService;

    // Inyección por constructor: Spring provee automáticamente la implementación
    // registrada con @Service (ClienteServiceImpl).
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // @GetMapping → mapea HTTP GET /api/clientes
    //   Retorna la lista completa de clientes serializada como JSON array.
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listar();
    }

    // @GetMapping("/{id}") → mapea HTTP GET /api/clientes/5
    // @PathVariable Long id → extrae el valor {id} de la URL y lo convierte a Long.
    @GetMapping("/{id}")
    public Cliente obtenerPorId(@PathVariable Long id) {
        return clienteService.obtenerPorId(id);
    }

    // @PostMapping → mapea HTTP POST /api/clientes
    // @RequestBody Cliente cliente → Spring deserializa el JSON del cuerpo
    //   de la petición al objeto Cliente usando Jackson.
    @PostMapping
    public Cliente insertar(@RequestBody Cliente cliente) {
        return clienteService.insertar(cliente);
    }

    // @PutMapping("/{id}") → mapea HTTP PUT /api/clientes/5
    //   Recibe el ID en la URL y los nuevos datos en el cuerpo JSON.
    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.actualizar(id, cliente);
    }

    // @DeleteMapping("/{id}") → mapea HTTP DELETE /api/clientes/5
    // ResponseEntity<Void> → permite devolver un código HTTP explícito
    //   sin cuerpo en la respuesta.
    // ResponseEntity.noContent().build() → retorna HTTP 204 No Content,
    //   el estándar REST para confirmación de eliminación exitosa.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
