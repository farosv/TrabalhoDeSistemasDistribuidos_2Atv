package com.sd.filaveiculos.veiculos;

import java.io.Serializable;
import java.util.Date;

public class Veiculo implements Serializable {

    private String nomeCliente;
    private String marcaModeloVeiculo;
    private int anoModelo;
    private double valorVenda;
    private Date dataPublicacao;

    public Veiculo(String nomeCliente, String marcaModeloVeiculo, int anoModelo, double valorVenda) {
        this.nomeCliente = nomeCliente;
        this.marcaModeloVeiculo = marcaModeloVeiculo;
        this.anoModelo = anoModelo;
        this.valorVenda = valorVenda;
        this.dataPublicacao = new Date();
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getMarcaModeloVeiculo() {
        return marcaModeloVeiculo;
    }

    public void setMarcaModeloVeiculo(String marcaModeloVeiculo) {
        this.marcaModeloVeiculo = marcaModeloVeiculo;
    }

    public int getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(int anoModelo) {
        this.anoModelo = anoModelo;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
}
