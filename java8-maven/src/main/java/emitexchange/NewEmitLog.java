package emitexchange;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewEmitLog {
    private static final String SCOTT_EXCHANGE_LOG = "Scott_exchange_log";

    public static void main(String[] argv) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(SCOTT_EXCHANGE_LOG, BuiltinExchangeType.FANOUT);
            String message = getMessage(argv);
            channel.basicPublish(SCOTT_EXCHANGE_LOG, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1)
            return "info: Hello World!";
        return String.join(" ", strings);
    }
}
