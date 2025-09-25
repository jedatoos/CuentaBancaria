package com.example.CuentaBancaria.servicios;



import com.example.CuentaBancaria.dtos.*;
import com.example.CuentaBancaria.excepciones.BalanceInsuficienteException;
import com.example.CuentaBancaria.excepciones.ClienteNotFoundException;
import com.example.CuentaBancaria.excepciones.CuentaBancariaNotFoundException;

import java.util.List;

public interface CuentaBancariaService {

    ClienteDTO saveCliente(ClienteDTO clienteDTO);

    ClienteDTO getCliente(Long clienteId) throws ClienteNotFoundException;

    ClienteDTO updateCliente(ClienteDTO clienteDTO);

    List<ClienteDTO> searchClientes(String keyword);

    void deleteCliente(Long clienteId);

    List<ClienteDTO> listClientes();

    CuentaActualDTO saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException;

    CuentaAhorroDTO saveCuentaBancariaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException;

    void debit(String cuentaId,double monto,String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException;

    void credit(String cuentaId,double monto,String descripcion) throws CuentaBancariaNotFoundException;

    void transfer(String cuentaIdPropietario,String cuentaIdDestinatario,double monto) throws CuentaBancariaNotFoundException,BalanceInsuficienteException;

    CuentaBancariaDTO getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException;

    List<CuentaBancariaDTO> listCuentasBancarias();

    List<OperacionCuentaDTO> listHistorialDeLaCuenta(String cuentaId);

    HistorialCuentaDTO getHistorialCuenta(String cuentaId,int page,int size) throws CuentaBancariaNotFoundException;
}
