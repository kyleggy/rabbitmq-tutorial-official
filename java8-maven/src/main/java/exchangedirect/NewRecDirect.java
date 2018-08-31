package exchangedirect;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewRecDirect {
    private static final String EXCHANGE_NAME_DIRECT = "Direct_Exchange_Scott_New";

    public static void main(String[] argv) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME_DIRECT, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();
        for (int i = 1; i < 3; i ++) {
            channel.queueBind(queueName, EXCHANGE_NAME_DIRECT, "key_" + 1);
        }

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };

        channel.basicConsume(queueName, true, consumer);
    }
}
