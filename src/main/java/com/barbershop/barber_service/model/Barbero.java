package com.barbershop.barber_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa a un barbero de la barbería.
 * Un barbero puede tener múltiples especialidades (One-to-Many con Especialidad).
 */

// @Entity → clase persistente gestionada por Hibernate/JPA.
@Entity

// @Table(name = "barberos") → nombre de la tabla en la BD.
@Table(name = "barberos")
public class Barbero {

    // Clave primaria con auto-incremento delegado a MariaDB.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank → el nombre no puede ser null, "" ni solo espacios.
    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    // @OneToMany → un barbero tiene muchas especialidades.
    //   mappedBy = "barbero" → le indica a JPA que el campo dueño de la relación
    //     (el que tiene la FK en la tabla) está en Especialidad.barbero.
    //     Esto evita que JPA cree una tabla intermedia innecesaria.
    //   cascade = ALL → todas las operaciones (INSERT, UPDATE, DELETE) sobre Barbero
    //     se propagan automáticamente a sus especialidades.
    //   orphanRemoval = true → si una Especialidad se elimina de esta lista,
    //     Hibernate ejecuta DELETE en la BD (evita huérfanos en la tabla).
    //   fetch = EAGER → carga las especialidades junto con el barbero en la misma
    //     consulta. Apropiado porque la lista es pequeña y siempre se necesita.
    @OneToMany(mappedBy = "barbero",
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.EAGER)
    private List<Especialidad> especialidades = new ArrayList<>();

    @Column(length = 20)
    private String telefono;

    // Boolean que indica si el barbero está disponible para tomar citas.
    // nullable = false → NOT NULL en BD; valor por defecto: true.
    @Column(nullable = false)
    private Boolean disponible = true;

    // Constructor vacío requerido por JPA.
    public Barbero() {}

    public Barbero(Long id, String nombre, List<Especialidad> especialidades,
                   String telefono, Boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.especialidades = especialidades;
        this.telefono = telefono;
        this.disponible = disponible;
    }

    // Getters y setters — requeridos por JPA y Jackson.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Especialidad> getEspecialidades() { return especialidades; }
    public void setEspecialidades(List<Especialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
}
