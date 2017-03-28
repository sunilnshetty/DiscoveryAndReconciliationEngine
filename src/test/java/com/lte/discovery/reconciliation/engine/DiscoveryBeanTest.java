package com.lte.discovery.reconciliation.engine;

import static org.junit.Assert.*;

import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.embeddable.EJBContainer;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Test;

import com.lte.discovery.reconciliation.engine.dto.RequestDetails;

public class DiscoveryBeanTest {

    @Resource
    private static TopicConnectionFactory connectionFactory;

    @Resource(name = "DiscoveryBean")
    private Topic engineRequestTopic;

    @Test
    public void test() {
	try {
	    EJBContainer.createEJBContainer().getContext().bind("inject", this);
	} catch (NamingException e) {
	    System.err.println(e.getMessage());
	}

	try {
	    final TopicConnection connection = connectionFactory
		    .createTopicConnection();
	    connection.start();

	    final TopicSession session = connection.createTopicSession(false,
		    TopicSession.AUTO_ACKNOWLEDGE);

	    final TopicPublisher engineRequestProducer = session
		    .createPublisher(engineRequestTopic);

	    RequestDetails requestDetails = new RequestDetails();
	    requestDetails.setNmsEmsType("EMS");
	    requestDetails.setNmsEmsName("Ericsson OSS-RC");

	    engineRequestProducer.publish(session
		    .createObjectMessage(requestDetails));

	} catch (JMSException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

}
