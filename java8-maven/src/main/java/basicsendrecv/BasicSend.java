package basicsendrecv;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BasicSend {
    private final static String QUEUE_SEND = "Scott_Queue";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(QUEUE_SEND, false, false, false, null);
            String msg = "Scott Say Hello";
            channel.basicPublish("", QUEUE_SEND, null, msg.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + msg + "'");
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }

}
