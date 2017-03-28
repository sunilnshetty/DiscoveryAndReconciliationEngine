package com.lte.discovery.reconciliation.engine;

import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.embeddable.EJBContainer;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicMDBClient {

//    @Resource
//    private static TopicConnectionFactory topicConnectionFactory;
//
//    @Resource(name = "topic/weatherTopic")
//    private static Topic topic;

    public static void main(String[] args) {
	Properties env = new Properties();
	env.put(Context.INITIAL_CONTEXT_FACTORY,
		"org.apache.openejb.client.LocalInitialContextFactory");
	env.put(Context.PROVIDER_URL, "localhost");

	try {
	    InitialContext ctx = new InitialContext(env);
	    TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) ctx
		    .lookup("Default JMS Connection Factory");

	    final Connection connection = topicConnectionFactory
		    .createConnection();

	    TopicConnection topicConnection = topicConnectionFactory
		    .createTopicConnection();
	    TopicSession session = topicConnection.createTopicSession(false,
		    Session.CLIENT_ACKNOWLEDGE);

//	    TopicPublisher publisher = session.createPublisher(topic);
//
//	    TextMessage msg = session.createTextMessage("Hello, ");
//	    publisher.send(msg);
	} catch (NamingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
