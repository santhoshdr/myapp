package net.drs.myapp.mqservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;


@Component
@Repository("rabbitMqService")
@Transactional
public class RabbitMqService implements IRabbitMqService {

    private static final Logger LOG = LoggerFactory.getLogger(RabbitMqService.class);

    private static final String EXCHANGE_NAME = "pub-sub-queue";

    private static final String MQ_HOST = "localhost";
    
    
    @PostConstruct
    private void postConsrtuct() {

        MqUtils.manageCheckedExceptions(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                initializeMq();
                return null;
            }
        });
    }
    
    public void initializeMq() {
        try {
            receiveSMSMQMessage();
        } catch (Exception e) {
            LOG.error("Error Occurred during initialization of channel");
        }
    }

    @Override
    public void publishSMSMessage(String smsmessage, String number) {
        
        LOG.info("Publishing message to MQ: SMS message {} , number {} ",smsmessage, number);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(MQ_HOST);
        Map<String, String> message = new HashMap<String,String>();
        message.put(number,smsmessage);
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
                channel.basicPublish(EXCHANGE_NAME, "", null, message.toString().getBytes());
        } catch (IOException | TimeoutException e) {
            LOG.error("Exception Occurred while creating MQ connection");
            e.printStackTrace();
        }
    }

    public String receiveSMSMQMessage() {

        try {

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(MQ_HOST);
            Connection connection;
            connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "");

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                delivery.getBody();
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * private final class SmsMqConsumer extends DefaultConsumer {
     * 
     * private SmsMqConsumer(Channel channel) { super(channel); }
     * 
     * @Override public void handleDelivery(String consumerTag, Envelope
     * envelope, AMQP.BasicProperties props, byte[] body) throws IOException {
     * 
     * }; } }
     */

    public static void main(String args[]) {

        RabbitMqService mq = new RabbitMqService();
        mq.publishSMSMessage("hi", "asdasd");
    }

}
