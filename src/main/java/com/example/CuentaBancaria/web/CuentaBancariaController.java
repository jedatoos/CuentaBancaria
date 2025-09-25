package com.example.CuentaBancaria.web;

import com.example.CuentaBancaria.dtos.*;
import com.example.CuentaBancaria.excepciones.BalanceInsuficienteException;
import com.example.CuentaBancaria.excepciones.CuentaBancariaNotFoundException;
import com.example.CuentaBancaria.servicios.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CuentaBancariaController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @GetMapping("/cuentas/{cuentaId}")
    public CuentaBancariaDTO listarDatosDeUnaCuentaBancaria(@PathVariable String cuentaId) throws CuentaBancariaNotFoundException {
        return cuentaBancariaService.getCuentaBancaria(cuentaId);
    }

    @GetMapping("/cuentas")
    public List<CuentaBancariaDTO> listarCuentasBancarias(){
        return cuentaBancariaService.listCuentasBancarias();
    }

    @GetMapping("/cuentas/{cuentaId}/operaciones")
    public List<OperacionCuentaDTO> listHistorialDeLaCuenta(@PathVariable String cuentaId){
        return cuentaBancariaService.listHistorialDeLaCuenta(cuentaId);
    }

    @GetMapping("/cuentas/{cuentaId}/pageOperaciones")
    public HistorialCuentaDTO listHistorialDeLaCuentaPaginado(@PathVariable String cuentaId, @RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "5") int size) throws CuentaBancariaNotFoundException {
        return cuentaBancariaService.getHistorialCuenta(cuentaId,page,size);
    }

    @PostMapping("/cuentas/debito")
    public DebitoDTO realizarDebito(@RequestBody DebitoDTO debitoDTO) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        cuentaBancariaService.debit(debitoDTO.getCuentaId(),debitoDTO.getMonto(),debitoDTO.getDescripcion());
        return debitoDTO;
    }

    @PostMapping("/cuentas/credito")
    public CreditoDTO realizarCredito(@RequestBody CreditoDTO creditoDTO) throws CuentaBancariaNotFoundException {
        cuentaBancariaService.credit(creditoDTO.getCuentaId(),creditoDTO.getMonto(),creditoDTO.getDescripcion());
        return creditoDTO;
    }

    @PostMapping("/cuentas/transferencia")
    public void realizarTransferencia(@RequestBody TransferenciaRequestDTO transferenciaRequestDTO) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        cuentaBancariaService.transfer(transferenciaRequestDTO.getCuentaPropietario(),transferenciaRequestDTO.getCuentaDestinatario(),transferenciaRequestDTO.getMonto());
    }
}
