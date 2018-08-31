package workqueue;

import com.rabbitmq.client.*;


public class NewWorker {
    private static final String TASK_QUEUE_NAME = "Scott_Task_Queue";

    public static void main(String[] argv) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            boolean durable = true;
            channel.queueDeclare(TASK_QUEUE_NAME, durable, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            int prefetchCount = 1;
            channel.basicQos(prefetchCount);

            final Consumer consumer = new NewWorkerConsumer(channel);
            //Auto ACK is by default false
            boolean autoAck = false;
            channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
