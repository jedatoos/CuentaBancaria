package com.example.CuentaBancaria.entidades;
import com.example.CuentaBancaria.enums.EstadoCuenta;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TIPO",length = 4)
@Getter // Reemplaza la parte de getter de @Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class CuentaBancaria {

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Id
    private String id;

    private double balance;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    private Date fechaCreacion;

    public EstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }

    public List<OperacionCuenta> getOperacionesCuenta() {
        return operacionesCuenta;
    }

    public void setOperacionesCuenta(List<OperacionCuenta> operacionesCuenta) {
        this.operacionesCuenta = operacionesCuenta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "cuentaBancaria",fetch = FetchType.LAZY)
    private List<OperacionCuenta> operacionesCuenta;
}
