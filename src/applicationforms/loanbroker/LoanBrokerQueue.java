package applicationforms.loanbroker;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

import javax.jms.*;
import java.io.Serializable;
import java.net.URI;

public class LoanBrokerQueue {

    private BrokerService broker;
    private Queue queue;
    private Session session;
    private Connection connection;

    public LoanBrokerQueue() {
    }

    public void startBroker(boolean startBroker) throws Exception {

        if (startBroker) {
            broker = BrokerFactory.createBroker(new URI("broker:(tcp://localhost:61616)"));
            broker.start();
        }
        connection = null;
        // Producer
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue("customerQueue");
    }

    public void sendMessage(Object object) throws JMSException {
        Message msg = session.createObjectMessage((Serializable) object);
        MessageProducer producer = session.createProducer(queue);
        System.out.println("Sending text '" + object.toString() + "'");
        producer.send(msg);
    }

    public void stopBroker() throws Exception {
        session.close();
        broker.stop();
    }

    public Session getSession() {
        return session;
    }

    public Queue getQueue() {
        return queue;
    }

    public Connection getConnection() {
        return connection;
    }
}
