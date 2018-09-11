package rabbitmq.rabbitmq.SendMessage;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: BUCHU
 * @Date: 2018/9/10 11:24
 * @Description:
 */
public class SendMessage {
    private static final String IP_ADDRESS = "localhost";
    private static final int PORT = 5672;
    private static final String EXCHANGE_NAME = "exchange_demo";
    private static final String ROUTING_KEY = "routingkey_demo";
    private static final String QUEUE_NAME = "routingkey_demo";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        byte[] messagePublish = "Hello huran".getBytes();
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, messagePublish);
        //为了更好地控制发送，可以使用 mandatory 这个参数， 或者可以发送一些特定属性的信息:
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, new AMQP.BasicProperties.Builder()
                .contentType("text/plain")
                .deliveryMode(2).priority(1).userId("hidden").build(), messagePublish);
        //也可以发送一条带有 headers 的消息:
        Map<String, Object> headers = new HashMap<String, Object>(64);
        headers.put("localhost", true);
        headers.put("time", "today");
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, new AMQP.BasicProperties.Builder()
                .headers(headers).build(), messagePublish);
        //还可以发送一条带有过期时间 (expiration) 的消息
        channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, new AMQP.BasicProperties.Builder().expiration("6000")
                .build(), messagePublish);

    }

}
