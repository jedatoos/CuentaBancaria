package com.example.CuentaBancaria.dtos;

import lombok.Data;

import java.util.List;

@Data
public class HistorialCuentaDTO {

    private String cuentaId;
    private double balance;
    private int currentPage;
    private int totalPages;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCuentaId() {
        return cuentaId;
    }

    public void setCuentaId(String cuentaId) {
        this.cuentaId = cuentaId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<OperacionCuentaDTO> getOperacionesCuentaDTOS() {
        return operacionesCuentaDTOS;
    }

    public void setOperacionesCuentaDTOS(List<OperacionCuentaDTO> operacionesCuentaDTOS) {
        this.operacionesCuentaDTOS = operacionesCuentaDTOS;
    }

    private int pageSize;
    private List<OperacionCuentaDTO> operacionesCuentaDTOS;

}
