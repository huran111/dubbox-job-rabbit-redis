package rabbitmq.rabbitmq.RabbitProducer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/9 18:00
 * @Description:
 */
public class RabbitConsumer {
    private static final String QUEUE_NAME = "routingkey_demo";
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Address[] addresses = new Address[]{new Address(IP_ADDRESS, PORT)};
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection(addresses);
        final Channel channel = connection.createChannel();
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("收到的消息:" + new String(body));
                try {
                    TimeUnit.SECONDS.sleep(1);

                }catch (Exception e){

                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(QUEUE_NAME,consumer);
        //TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.createChannel();
    }
}
