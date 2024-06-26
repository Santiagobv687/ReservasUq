package co.edu.uniquindio.reserva.reservauq.config;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitFactory {

    private ConnectionFactory connectionFactory;
    public RabbitFactory() {
        this.connectionFactory = new ConnectionFactory();
        this.connectionFactory.setHost("localHost");
        this.connectionFactory.setPort(5672);
        this.connectionFactory.setUsername("guest");
        this.connectionFactory.setPassword("guest");
    }

    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }
}

