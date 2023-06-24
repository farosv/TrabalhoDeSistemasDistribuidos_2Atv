package com.sd.filaveiculos.controller;

import com.sd.filaveiculos.veiculos.Veiculo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VeiculoController {

    private static final String URL_BD = "jdbc:h2:c:/Users/nayar/Documentos/Banco de Dados/veiculos";

    @PostMapping("/veiculos")
    public void postVeiculo(@RequestBody Veiculo veiculo) throws SQLException {
        Connection connection = DriverManager.getConnection(URL_BD, "sa", "");
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO veiculo (nomeCliente, marcaModeloVeiculo, anoModelo, valorVenda, dataPublicacao) VALUES (?, ?, ?, ?, ?)");
        statement.setString(1, veiculo.getNomeCliente());
        statement.setString(2, veiculo.getMarcaModeloVeiculo());
        statement.setInt(3, veiculo.getAnoModelo());
        statement.setDouble(4, veiculo.getValorVenda());
        statement.setDate(5, new java.sql.Date(veiculo.getDataPublicacao().getTime()));
        statement.executeUpdate();
        connection.close();
    }

    @GetMapping("/veiculos")
    public List<Veiculo> getVeiculos() throws SQLException {
        List<Veiculo> veiculos = new ArrayList<>();
        Connection connection = DriverManager.getConnection(URL_BD, "sa", "");
        PreparedStatement statement = connection.prepareStatement(
                "SELECT nomeCliente, marcaModeloVeiculo, anoModelo, valorVenda, dataPublicacao FROM VEICULO");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Veiculo veiculo = new Veiculo(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3),
                    resultSet.getDouble(4));
            veiculo.setDataPublicacao(resultSet.getDate(5));
            veiculos.add(veiculo);
        }
        connection.close();
        return veiculos;
    }
}
