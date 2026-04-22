package com.barbershop.barber_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Entidad JPA que representa una cita agendada en la barbería.
 * Relaciona un cliente, un barbero y un servicio mediante sus IDs (FK simples).
 * Se persiste en la tabla "citas" de MariaDB.
 */

// @Entity → registra esta clase en el contexto de persistencia de JPA.
@Entity

// @Table(name = "citas") → nombre de la tabla en la BD.
@Table(name = "citas")
public class Cita {

    // Clave primaria con auto-incremento gestionado por la BD.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Se almacena solo el ID del cliente (clave foránea simple).
    // Se eligió este enfoque —en lugar de @ManyToOne— para mantener
    // el mismo contrato JSON que espera el frontend (clienteId: 1).
    // @NotNull → el campo es obligatorio en validaciones de entrada.
    // @Column(name = "cliente_id") → nombre exacto de la columna FK en BD.
    @NotNull
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    // FK al barbero asignado a esta cita.
    @NotNull
    @Column(name = "barbero_id", nullable = false)
    private Long barberoId;

    // FK al servicio que se realizará en la cita.
    @NotNull
    @Column(name = "servicio_id", nullable = false)
    private Long servicioId;

    // Fecha guardada como String (formato "YYYY-MM-DD") para simplificar
    // el intercambio de datos con el frontend sin conversiones adicionales.
    @Column(nullable = false)
    private String fecha;

    // Hora guardada como String (formato "HH:mm") por la misma razón que fecha.
    @Column(nullable = false)
    private String hora;

    // Estado de la cita. Valores posibles: PENDIENTE, CONFIRMADA, COMPLETADA, CANCELADA.
    // El valor por defecto es "PENDIENTE" al crear una nueva cita.
    @Column(nullable = false, length = 20)
    private String estado = "PENDIENTE";

    // Constructor vacío requerido por JPA para instanciar la entidad
    // cuando se recuperan filas desde la base de datos.
    public Cita() {}

    public Cita(Long id, Long clienteId, Long barberoId, Long servicioId,
                String fecha, String hora, String estado) {
        this.id = id;
        this.clienteId = clienteId;
        this.barberoId = barberoId;
        this.servicioId = servicioId;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
    }

    // Getters y setters — JPA y Jackson los requieren para leer/escribir campos.
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public Long getBarberoId() { return barberoId; }
    public void setBarberoId(Long barberoId) { this.barberoId = barberoId; }

    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
