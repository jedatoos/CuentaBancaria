package com.example.CuentaBancaria.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@Entity
@DiscriminatorValue("SA")
@NoArgsConstructor
@AllArgsConstructor
public class CuentaAhorro extends CuentaBancaria{

    private double tasaDeInteres;

    public double getTasaDeInteres() {
        return tasaDeInteres;
    }

    public void setTasaDeInteres(double tasaDeInteres) {
        this.tasaDeInteres = tasaDeInteres;
    }
}
