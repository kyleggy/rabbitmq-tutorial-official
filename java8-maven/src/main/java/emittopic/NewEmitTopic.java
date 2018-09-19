package emittopic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewEmitTopic {
    private static final String EXCHANGE_NAME = "EXCHANGE_TOPIC";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String msg = "Hello, this is a topic exchange type message";
        channel.basicPublish(EXCHANGE_NAME, "name.key.subkey", null, msg.getBytes("UTF-8"));

        channel.close();
        connection.close();
    }
}
