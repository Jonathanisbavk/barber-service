package com.barbershop.barber_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Entidad JPA que representa un servicio ofrecido por la barbería
 * (ej: corte de cabello, afeitado, tinte).
 * Se persiste en la tabla "servicios" de MariaDB.
 */

// @Entity → JPA detecta esta clase y la enlaza a una tabla de la BD.
@Entity

// @Table(name = "servicios") → nombre personalizado de la tabla.
@Table(name = "servicios")
public class Servicio {

    // Clave primaria con auto-incremento delegado a MariaDB (AUTO_INCREMENT).
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @NotBlank → validación de entrada: el nombre no puede estar vacío.
    // @Column(nullable = false, length = 100) → restricción también en la BD.
    @NotBlank
    @Column(nullable = false, length = 100)
    private String nombre;

    // @Column(columnDefinition = "TEXT") →
    //   fuerza el tipo TEXT en SQL, que permite textos largos
    //   sin límite fijo de caracteres (a diferencia de VARCHAR).
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    // @NotNull → no acepta null (pero sí permite 0.0; usa @Positive para eso).
    // @Positive → valida que el precio sea estrictamente mayor a 0.
    // @Column(nullable = false) → refuerza la restricción NOT NULL en la BD.
    // Nota: Double usa DOUBLE en SQL; no se puede usar precision/scale con este tipo.
    @NotNull
    @Positive
    @Column(nullable = false)
    private Double precio;

    // @Column(name = "duracion_minutos") →
    //   el atributo Java se llama "duracionMinutos" (camelCase),
    //   pero en la BD la columna se llama "duracion_minutos" (snake_case).
    //   Sin este @Column, Hibernate usaría "duracionminutos" como nombre.
    @Column(name = "duracion_minutos")
    private Integer duracionMinutos;

    // Constructor vacío requerido por JPA para hidratar objetos desde la BD.
    public Servicio() {}

    public Servicio(Long id, String nombre, String descripcion, Double precio, Integer duracionMinutos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.duracionMinutos = duracionMinutos;
    }

    // Getters y setters — necesarios para serialización JSON (Jackson)
    // y para que Hibernate pueda leer/escribir los valores del objeto.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }
}
