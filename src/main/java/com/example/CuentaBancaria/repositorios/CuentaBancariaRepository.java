package com.example.CuentaBancaria.repositorios;

import com.example.CuentaBancaria.entidades.CuentaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaBancariaRepository extends JpaRepository<CuentaBancaria,String> {


}
