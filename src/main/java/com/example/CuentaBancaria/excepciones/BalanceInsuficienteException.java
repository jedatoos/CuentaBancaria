package com.example.CuentaBancaria.excepciones;

public class BalanceInsuficienteException extends Exception{

    public BalanceInsuficienteException(String message) {
        super(message);
    }
}
