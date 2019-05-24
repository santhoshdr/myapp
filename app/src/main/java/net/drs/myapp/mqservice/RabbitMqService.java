package net.drs.myapp.mqservice;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class RabbitMqService implements IRabbitMqService {

	
	private static final String EXCHANGE_NAME = "pub-sub-queue";
	
	
	
	@PostConstruct
	private void postConsrtuct(){

        MqUtils.manageCheckedExceptions(new Callable<Void>() {

            @Override
            public Void call() throws Exception {
            	initializeMq();
            	return null;
            }
        });
	}
	
	
	public void initializeMq(){
		try {
		    receiveSMSMQMessage();
		}catch(Exception e){
			
		}
		
	}

	@Override
	public void publishSMSMessage(String smsmessage, String number) {
		// TODO Auto-generated method stub
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

			for (int i = 0; i < 10; i++) {
				String message = "Helloworld message - " + i;
				channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
				System.out.println(" [x] Sent '" + message + "'");
			}
		} catch (IOException | TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}

	
	
	
	public String receiveSMSMQMessage() {
		
	
		try {
			
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection;
			connection = factory.newConnection();
			Channel channel = connection.createChannel();

			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
		
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			System.out.println(" [x] Received '" + message + "'");
		};
		channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
		});
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
/*	private final class SmsMqConsumer extends DefaultConsumer {

        private SmsMqConsumer(Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties props, byte[] body) throws IOException {

            };
        }
    }
	*/
	
	

	
	public static void main(String args[]){
		
		RabbitMqService  mq = new RabbitMqService();
		mq.publishSMSMessage("hi", "asdasd");
	}
	
	
	
}
