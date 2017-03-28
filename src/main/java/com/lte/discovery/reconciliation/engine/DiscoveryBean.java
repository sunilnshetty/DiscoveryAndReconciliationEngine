package com.lte.discovery.reconciliation.engine;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.resource.spi.Activation;

import com.lte.discovery.reconciliation.engine.dto.RequestDetails;

@MessageDriven(name = "DiscoveryBean", activationConfig = { @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
	@ActivationConfigProperty(propertyName="subscriptionDurability",propertyValue="Durable")
	/*@ActivationConfigProperty(propertyName="destinationJndiName",propertyValue="DiscoveryBeanJndi")*/})
public class DiscoveryBean implements MessageListener {

    @Resource
    private TopicConnectionFactory topicConnectionFactory;

    @Resource(name = "AdapterRequestTopic")
    private Topic requestTopic;

    @Resource(name = "ResponseTopic")
    private Topic responseTopic;

    @Override
    public void onMessage(Message message) {
	System.out.println("DiscoveryBean.onMessage()");

	if (message instanceof ObjectMessage) {
	    final ObjectMessage objectMessage = (ObjectMessage) message;

	    try {
		RequestDetails requestDetails = (RequestDetails) objectMessage
			.getObject();
		System.out.println(requestDetails.toString());

		sendToAdapter(RequestManager.create(requestDetails));

	    } catch (JMSException e) {
		System.err.println(e.getMessage());
	    }
	} else if (message instanceof TextMessage) {
	    final TextMessage textMessage = (TextMessage) message;

	} else {
	    System.err.println("Message type " + message.getClass().getName()
		    + " is not handled.");
	}

    }

    private void sendToAdapter(String requestString) {

	TopicConnection connection = null;
	TopicSession session = null;

	try {
	    connection = topicConnectionFactory.createTopicConnection();
	    connection.start();

	    session = connection.createTopicSession(false,
		    TopicSession.AUTO_ACKNOWLEDGE);

	    TopicPublisher topicPublisher = session
		    .createPublisher(requestTopic);
	    topicPublisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

	    TextMessage message = session.createTextMessage(requestString);

	    topicPublisher.publish(message);
	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {

	    try {
		if (session != null)
		    session.close();
		if (connection != null)
		    connection.close();
	    } catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

    }

}
