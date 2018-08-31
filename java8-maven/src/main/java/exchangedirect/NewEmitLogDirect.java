package exchangedirect;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewEmitLogDirect {
    private static final String EXCHANGE_NAME_DIRECT = "Direct_Exchange_Scott_New";
    private static final String EXCHANGE_NAME_DIRECT_QUEUE = "Direct_Exchange_Scott_Queue_New";

    public static void main(String[] argv) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.queueDeclare(EXCHANGE_NAME_DIRECT_QUEUE, false, false, false, null);
        channel.exchangeDeclare(EXCHANGE_NAME_DIRECT, BuiltinExchangeType.DIRECT);
        for (int i = 1; i < 3; i ++) {
            String msg = "The message is from routing key: kye_" + i;
            channel.basicPublish(EXCHANGE_NAME_DIRECT, "key_" + i, null, msg.getBytes("UTF-8"));
        }
        System.out.println("Message emit from producer");
        channel.close();
        connection.close();
    }
}
