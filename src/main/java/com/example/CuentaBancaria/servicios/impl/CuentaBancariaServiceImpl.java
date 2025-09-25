package com.example.CuentaBancaria.servicios.impl;


import com.example.CuentaBancaria.dtos.*;
import com.example.CuentaBancaria.entidades.*;
import com.example.CuentaBancaria.enums.TipoOperacion;
import com.example.CuentaBancaria.excepciones.BalanceInsuficienteException;
import com.example.CuentaBancaria.excepciones.ClienteNotFoundException;
import com.example.CuentaBancaria.excepciones.CuentaBancariaNotFoundException;
import com.example.CuentaBancaria.mappers.CuentaBancariaMapperImpl;
import com.example.CuentaBancaria.repositorios.ClienteRepository;
import com.example.CuentaBancaria.repositorios.CuentaBancariaRepository;
import com.example.CuentaBancaria.repositorios.OperacionCuentaRepository;
import com.example.CuentaBancaria.servicios.CuentaBancariaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class CuentaBancariaServiceImpl implements CuentaBancariaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CuentaBancariaRepository cuentaBancariaRepository;

    @Autowired
    private OperacionCuentaRepository operacionCuentaRepository;

    @Autowired
    private CuentaBancariaMapperImpl cuentaBancariaMapper;

    @Override
    public ClienteDTO saveCliente(ClienteDTO clienteDTO) {
        //log.info("Guardando un nuevo cliente");
        Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO);
        Cliente clienteBBDD = clienteRepository.save(cliente);
        return cuentaBancariaMapper.mapearDeCliente(clienteBBDD);
    }

    @Override
    public ClienteDTO getCliente(Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente no encontrado"));
        return cuentaBancariaMapper.mapearDeCliente(cliente);
    }

    @Override
    public ClienteDTO updateCliente(ClienteDTO clienteDTO) {
      //  log.info("Actualizando cliente");
        Cliente cliente = cuentaBancariaMapper.mapearDeClienteDTO(clienteDTO);
        Cliente clienteBBDD = clienteRepository.save(cliente);
        return cuentaBancariaMapper.mapearDeCliente(clienteBBDD);
    }

    @Override
    public List<ClienteDTO> searchClientes(String keyword) {
        List<Cliente> clientes = clienteRepository.searchClientes(keyword);
        List<ClienteDTO> clientesDTOS = clientes.stream().map(cliente -> cuentaBancariaMapper.mapearDeCliente(cliente)).collect(Collectors.toList());
        return clientesDTOS;
    }

    @Override
    public void deleteCliente(Long clienteId) {
        clienteRepository.deleteById(clienteId);
    }

    @Override
    public CuentaActualDTO saveCuentaBancariaActual(double balanceInicial, double sobregiro, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if(cliente == null){
            throw new ClienteNotFoundException("Cliente no encontrado");
        }

        CuentaActual cuentaActual = new CuentaActual();
        cuentaActual.setId(UUID.randomUUID().toString());
        cuentaActual.setFechaCreacion(new Date());
        cuentaActual.setBalance(balanceInicial);
        cuentaActual.setSobregiro(sobregiro);
        cuentaActual.setCliente(cliente);

        CuentaActual cuentaActualBBDD = cuentaBancariaRepository.save(cuentaActual);
        return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActualBBDD);
    }

    @Override
    public CuentaAhorroDTO saveCuentaBancariaAhorro(double balanceInicial, double tasaInteres, Long clienteId) throws ClienteNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId).orElse(null);

        if(cliente == null){
            throw new ClienteNotFoundException("Cliente no encontrado");
        }

        CuentaAhorro cuentaAhorro = new CuentaAhorro();
        cuentaAhorro.setId(UUID.randomUUID().toString());
        cuentaAhorro.setFechaCreacion(new Date());
        cuentaAhorro.setBalance(balanceInicial);
        cuentaAhorro.setTasaDeInteres(tasaInteres);
        cuentaAhorro.setCliente(cliente);

        CuentaAhorro cuentaAhorroBBDD = cuentaBancariaRepository.save(cuentaAhorro);
        return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorroBBDD);
    }

    @Override
    public List<ClienteDTO> listClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clienteDTOS = clientes.stream()
                .map(cliente -> cuentaBancariaMapper.mapearDeCliente(cliente))
                .collect(Collectors.toList());
        return clienteDTOS;
    }

    @Override
    public CuentaBancariaDTO getCuentaBancaria(String cuentaId) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));

        if(cuentaBancaria instanceof CuentaAhorro){
            CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBancaria;
            return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorro);
        }

        else{
            CuentaActual cuentaActual = (CuentaActual) cuentaBancaria;
            return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActual);
        }
    }

    @Override
    public void debit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));

        if(cuentaBancaria.getBalance() < monto){
            throw new BalanceInsuficienteException("Balance insuficiente");
        }

        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.DEBITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuentaRepository.save(operacionCuenta);
        cuentaBancaria.setBalance(cuentaBancaria.getBalance() - monto);
        cuentaBancariaRepository.save(cuentaBancaria);
    }

    @Override
    public void credit(String cuentaId, double monto, String descripcion) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaBancariaNotFoundException("Cuenta bancaria no encontrada"));

        OperacionCuenta operacionCuenta = new OperacionCuenta();
        operacionCuenta.setTipoOperacion(TipoOperacion.CREDITO);
        operacionCuenta.setMonto(monto);
        operacionCuenta.setDescripcion(descripcion);
        operacionCuenta.setFechaOperacion(new Date());
        operacionCuenta.setCuentaBancaria(cuentaBancaria);
        operacionCuentaRepository.save(operacionCuenta);
        cuentaBancaria.setBalance(cuentaBancaria.getBalance() + monto);
        cuentaBancariaRepository.save(cuentaBancaria);
    }

    @Override
    public void transfer(String cuentaIdPropietario, String cuentaIdDestinatario, double monto) throws CuentaBancariaNotFoundException, BalanceInsuficienteException {
        debit(cuentaIdPropietario,monto,"Transferencia a : " + cuentaIdDestinatario);
        credit(cuentaIdDestinatario,monto,"Transferencia de : " + cuentaIdPropietario);
    }

    @Override
    public List<CuentaBancariaDTO> listCuentasBancarias() {
        List<CuentaBancaria> cuentasBancarias = cuentaBancariaRepository.findAll();
        List<CuentaBancariaDTO> cuentasBancariasDTOS = cuentasBancarias.stream().map(cuentaBancaria -> {
           if(cuentaBancaria instanceof CuentaAhorro){
               CuentaAhorro cuentaAhorro = (CuentaAhorro) cuentaBancaria;
               return cuentaBancariaMapper.mapearDeCuentaAhorro(cuentaAhorro);
           }
           else{
               CuentaActual cuentaActual = (CuentaActual) cuentaBancaria;
               return cuentaBancariaMapper.mapearDeCuentaActual(cuentaActual);
           }
        }).collect(Collectors.toList());
        return cuentasBancariasDTOS;
    }

    @Override
    public List<OperacionCuentaDTO> listHistorialDeLaCuenta(String cuentaId) {
        List<OperacionCuenta> operacionesDeCuenta = operacionCuentaRepository.findByCuentaBancariaId(cuentaId);
        return operacionesDeCuenta.stream().map(operacionCuenta ->
                cuentaBancariaMapper.mapearDeOperacionCuenta(operacionCuenta)
        ).collect(Collectors.toList());
    }

    @Override
    public HistorialCuentaDTO getHistorialCuenta(String cuentaId, int page, int size) throws CuentaBancariaNotFoundException {
        CuentaBancaria cuentaBancaria = cuentaBancariaRepository.findById(cuentaId).orElse(null);

        if(cuentaBancaria == null){
            throw new CuentaBancariaNotFoundException("Cuenta no encontrada");
        }

        Page<OperacionCuenta> operacionesCuenta = operacionCuentaRepository.findByCuentaBancariaIdOrderByFechaOperacionDesc(cuentaId, PageRequest.of(page, size));
        HistorialCuentaDTO historialCuentaDTO = new HistorialCuentaDTO();
        List<OperacionCuentaDTO> operacionesCuentaDTOS = operacionesCuenta.getContent().stream().map(operacionCuenta -> cuentaBancariaMapper.mapearDeOperacionCuenta(operacionCuenta)).collect(Collectors.toList());
        historialCuentaDTO.setOperacionesCuentaDTOS(operacionesCuentaDTOS);
        historialCuentaDTO.setCuentaId(cuentaBancaria.getId());
        historialCuentaDTO.setBalance(cuentaBancaria.getBalance());
        historialCuentaDTO.setCurrentPage(page);
        historialCuentaDTO.setPageSize(size);
        historialCuentaDTO.setTotalPages(operacionesCuenta.getTotalPages());
        return historialCuentaDTO;
    }
}
