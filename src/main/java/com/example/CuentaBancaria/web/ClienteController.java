package com.example.CuentaBancaria.web;


import com.example.CuentaBancaria.dtos.ClienteDTO;
import com.example.CuentaBancaria.excepciones.ClienteNotFoundException;
import com.example.CuentaBancaria.servicios.CuentaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @Autowired
    private CuentaBancariaService cuentaBancariaService;

    @PostMapping("/clientes")
    public ClienteDTO guardarCliente(@RequestBody ClienteDTO clienteDTO){
        return cuentaBancariaService.saveCliente(clienteDTO);
    }

    @GetMapping("/clientes/{id}")
    public ClienteDTO listarDatosDelCliente(@PathVariable(name = "id") Long clienteId) throws ClienteNotFoundException {
        return cuentaBancariaService.getCliente(clienteId);
    }

    @PutMapping("/clientes/{clienteId}")
    public ClienteDTO actualizarCliente(@PathVariable Long clienteId,@RequestBody ClienteDTO clienteDTO){
        clienteDTO.setId(clienteId);
        return cuentaBancariaService.updateCliente(clienteDTO);
    }

    @GetMapping("/clientes/search")
    public List<ClienteDTO> buscarClientes(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return cuentaBancariaService.searchClientes("%"+keyword+"%");
    }

    @GetMapping("/clientes")
    public List<ClienteDTO> listarClientes(){
        return cuentaBancariaService.listClientes();
    }

    @DeleteMapping("/clientes/{id}")
    public void eliminarCliente(@PathVariable Long id)
    {
        cuentaBancariaService.deleteCliente(id);
    }


}
