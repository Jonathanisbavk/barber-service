package com.barbershop.barber_service.service.impl;

import com.barbershop.barber_service.model.Cliente;
import com.barbershop.barber_service.repository.ClienteRepository;
import com.barbershop.barber_service.service.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la capa de servicio para la entidad Cliente.
 * Contiene la lógica de negocio y delega el acceso a datos al repositorio.
 */

// @Service → le indica a Spring que esta clase es un componente de lógica
//   de negocio (capa de servicio). Spring la registra como bean y permite
//   que otros componentes la inyecten mediante @Autowired o constructor.
//   Es equivalente a @Component pero con semántica más descriptiva.
@Service
public class ClienteServiceImpl implements ClienteService {

    // Dependencia al repositorio — se declara final para garantizar que
    // no se reasigne después de la construcción (inmutabilidad del servicio).
    private final ClienteRepository clienteRepository;

    // Inyección de dependencias por constructor (práctica recomendada):
    //   Spring detecta que hay un solo constructor y lo usa para inyectar
    //   la instancia de ClienteRepository automáticamente.
    //   Ventaja sobre @Autowired en campo: facilita las pruebas unitarias
    //   porque el repositorio se puede pasar manualmente (sin Spring).
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // @Override → indica que este método implementa el contrato
    //   definido en la interfaz ClienteService.
    //   El compilador verifica que la firma coincida exactamente.
    @Override
    public List<Cliente> listar() {
        // findAll() → genera SELECT * FROM clientes y retorna todos los registros.
        return clienteRepository.findAll();
    }

    @Override
    public Cliente obtenerPorId(Long id) {
        // findById(id) → retorna un Optional<Cliente>.
        // orElse(null) → si no existe ningún cliente con ese ID, retorna null
        //   en lugar de lanzar una excepción, para que el controlador lo maneje.
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public Cliente insertar(Cliente cliente) {
        // save(cliente) → como el objeto no tiene ID todavía (es null),
        //   Hibernate genera un INSERT INTO clientes (...) VALUES (...).
        //   Retorna el objeto ya con el ID asignado por la BD.
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente actualizar(Long id, Cliente cliente) {
        // Primero verificamos que el cliente exista en la BD.
        Cliente existente = clienteRepository.findById(id).orElse(null);

        // Si no existe, se retorna null; el controlador responderá con 404 o similar.
        if (existente == null) return null;

        // Se actualizan solo los campos permitidos sobre el objeto existente.
        // Esto evita sobreescribir el ID u otros campos que no deben cambiar.
        existente.setNombre(cliente.getNombre());
        existente.setTelefono(cliente.getTelefono());
        existente.setEmail(cliente.getEmail());

        // save(existente) → como el objeto ya tiene ID, Hibernate genera
        //   un UPDATE clientes SET ... WHERE id = ?
        return clienteRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        // deleteById(id) → genera DELETE FROM clientes WHERE id = ?
        //   Lanza EmptyResultDataAccessException si el ID no existe.
        clienteRepository.deleteById(id);
    }
}
