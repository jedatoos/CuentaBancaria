package com.example.CuentaBancaria.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity

@ToString
@DiscriminatorValue("CA")
@NoArgsConstructor
@AllArgsConstructor
public class CuentaActual extends CuentaBancaria{

    private double sobregiro;

    public double getSobregiro() {
        return sobregiro;
    }

    public void setSobregiro(double sobregiro) {
        this.sobregiro = sobregiro;
    }
}
