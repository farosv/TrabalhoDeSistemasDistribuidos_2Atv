package com.sd.filaveiculos.veiculos;

import org.h2.jdbcx.JdbcConnectionPool;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import org.apache.activemq.ActiveMQConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Consumidor {

    private static final String URL_BROKER = "tcp://localhost:61616";
    private static final String NOME_FILA = "FILA_VEICULOS";
    private static final String URL_BD = "jdbc:h2:c:/Users/nayar/Documentos/Banco de Dados/veiculos";

    public static void main(String[] args) throws JMSException, SQLException {
        // Criação do pool de conexões JDBC do H2
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create(URL_BD, "sa", "");

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL_BROKER);
        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) connectionFactory;
        QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
        QueueSession queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        Queue queue = queueSession.createQueue(NOME_FILA);
        QueueReceiver receiver = queueSession.createReceiver(queue);

        receiver.setMessageListener(message -> {
            try {
                ObjectMessage objectMessage = (ObjectMessage) message;
                Veiculo veiculo = (Veiculo) objectMessage.getObject();
                System.out.println("Recebendo mensagem: " + veiculo.toString());

                try (Connection connection = connectionPool.getConnection()) {
                    PreparedStatement statement = connection.prepareStatement(
                            "INSERT INTO veiculo (nomeCliente, marcaModeloVeiculo, anoModelo, valorVenda, dataPublicacao) VALUES (?, ?, ?, ?, ?)");
                    statement.setString(1, veiculo.getNomeCliente());
                    statement.setString(2, veiculo.getMarcaModeloVeiculo());
                    statement.setInt(3, veiculo.getAnoModelo());
                    statement.setDouble(4, veiculo.getValorVenda());
                    statement.setDate(5, new java.sql.Date(veiculo.getDataPublicacao().getTime()));
                    int rowsAffected = statement.executeUpdate();
                    System.out.println("Registros afetados: " + rowsAffected);
                }
            } catch (SQLException | JMSException e) {
                e.printStackTrace();
            }
        });

        queueConnection.start();

        System.out.println("Consumer iniciado...");
        
        
        while (true) {

        }
    }
}
