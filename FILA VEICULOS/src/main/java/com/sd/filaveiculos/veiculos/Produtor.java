package com.sd.filaveiculos.veiculos;

import java.util.Date;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Produtor {

    private static final String URL_BROKER = "tcp://localhost:61616";
    private static final String NOME_FILA = "FILA_VEICULOS";

    public static void main(String[] args) throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL_BROKER);
        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) connectionFactory;
        QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();
        QueueSession queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
        Queue queue = queueSession.createQueue(NOME_FILA);
        javax.jms.QueueSender sender = queueSession.createSender(queue);
        Veiculo veiculo = new Veiculo("Vitor", "Porsche 411", 2020, 350000.0);
        ObjectMessage message = queueSession.createObjectMessage();
        message.setObject(veiculo);
        message.setJMSExpiration(new Date().getTime() + 1000);
        System.out.println("Enviando mensagem: " + message.getObject().toString());
        sender.send(message);
        queueSession.close();
        queueConnection.close();
    }
}
