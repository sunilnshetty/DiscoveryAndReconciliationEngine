package com.lte.discovery.reconciliation.engine;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.jms.TopicConnectionFactory;

@MessageDriven(
activationConfig={@ActivationConfigProperty(propertyName="acknowledgeMode",propertyValue="Auto-acknowledge"),
	@ActivationConfigProperty(propertyName="destinationType",propertyValue="javax.jms.Topic"),
	@ActivationConfigProperty(propertyName="clientId",propertyValue="TopicEJB"),
	@ActivationConfigProperty(propertyName="destination",propertyValue="topic/weatherTopic"),
	@ActivationConfigProperty(propertyName="subscriptionName",propertyValue="TopicEJB")
	})
public class TopicEJB implements MessageListener{
    
    @Resource
    private TopicConnectionFactory topicConnectionFactory;

    @Override
    public void onMessage(Message msg) {
	try {
	    System.out.println(msg.getJMSDestination());
	    
	    if(msg instanceof TextMessage) {
		TextMessage message = (TextMessage) msg;
		System.out.println(message.getText());
	    } else if(msg instanceof MapMessage) {
		MapMessage message = (MapMessage) msg;
		System.out.println(message.getString("hello"));
	    } else if(msg instanceof ObjectMessage) {
		ObjectMessage objectMessage = (ObjectMessage) msg;
		System.out.println(objectMessage.getObject());
	    }
	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
    }

}
