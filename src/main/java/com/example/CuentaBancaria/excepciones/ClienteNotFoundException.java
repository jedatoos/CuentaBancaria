package com.example.CuentaBancaria.excepciones;

public class ClienteNotFoundException extends Exception{

    public ClienteNotFoundException(String message) {
        super(message);
    }
}
