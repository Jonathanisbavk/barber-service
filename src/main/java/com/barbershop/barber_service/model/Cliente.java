package com.barbershop.barber_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidad JPA que representa a un cliente de la barbería.
 * Hibernate usa esta clase para crear/gestionar la tabla "clientes" en MariaDB.
 */

// @Entity → le dice a JPA que esta clase es una entidad persistente,
//            es decir, que tendrá su propia tabla en la base de datos.
@Entity

// @Table(name = "clientes") → especifica el nombre exacto de la tabla en la BD.
//   Si se omite, JPA usaría el nombre de la clase ("Cliente") por defecto.
@Table(name = "clientes")
public class Cliente {

    // @Id → marca este campo como la clave primaria de la tabla.
    @Id

    // @GeneratedValue(strategy = GenerationType.IDENTITY) →
    //   indica que el valor del ID lo genera automáticamente la BD
    //   usando AUTO_INCREMENT (equivalente a SERIAL en PostgreSQL).
    //   No debemos asignarlo manualmente al crear un objeto.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank → validación de Bean Validation (Jakarta):
    //   rechaza el campo si es null, vacío "" o solo espacios en blanco.
    //   Se activa cuando el controlador usa @Valid en el parámetro.
    @NotBlank

    // @Column(nullable = false, length = 100) →
    //   nullable = false: genera la columna con NOT NULL en SQL.
    //   length = 100:     limita el VARCHAR a 100 caracteres en la BD.
    @Column(nullable = false, length = 100)
    private String nombre;

    // @Column(length = 20) → columna opcional (permite null),
    //   pero limita la longitud máxima a 20 caracteres (ej: "+51987654321").
    @Column(length = 20)
    private String telefono;

    // Campo opcional sin restricciones de longitud especiales (usa VARCHAR por defecto).
    @Column(length = 100)
    private String email;

    // Constructor vacío requerido por JPA para instanciar la entidad
    // al leer filas desde la base de datos mediante reflexión.
    public Cliente() {}

    // Constructor de conveniencia para crear objetos desde código de prueba.
    public Cliente(Long id, String nombre, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y setters — JPA y Jackson los usan para leer/escribir
    // los valores del objeto tanto al persistir como al serializar a JSON.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
