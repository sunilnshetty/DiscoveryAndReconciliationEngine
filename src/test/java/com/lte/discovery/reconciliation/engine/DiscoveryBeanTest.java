package com.lte.discovery.reconciliation.engine;

import javax.annotation.Resource;
import javax.ejb.embeddable.EJBContainer;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.junit.Test;

import com.lte.discovery.reconciliation.engine.dto.RequestDetails;
import com.lte.discovery.reconciliation.engine.dto.RequestParameters;

public class DiscoveryBeanTest {

	@Resource
	private ConnectionFactory connectionFactory;

	@Resource(name = "DiscoveryBean")
	private Queue requestQueue;

	@Resource(name = "ResponseQueue")
	private Queue responseQueue;

	@Test
	public void test() throws NamingException, JMSException {

		EJBContainer.createEJBContainer().getContext().bind("inject", this);

		final Connection connection = connectionFactory.createConnection();

		connection.start();

		final Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		final MessageProducer messageProducer = session
				.createProducer(requestQueue);

		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setNmsEmsName("Ericsson OSS-RC");
		requestDetails.setNmsEmsType("NMS");

		RequestParameters requestParameters = new RequestParameters();
		requestParameters.setUserName("suniln");
		requestParameters.setHost("127.0.0.1");
		requestParameters.setPort(22);
		requestParameters.setPassword("1234");
		requestParameters
				.setSourceFolder("C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\SourceDirectory");
		requestParameters
				.setDestinationFolder("C:\\Users\\suniln\\app\\LTEDiscoveryAndReconciliation\\GIT\\DiscoveryAndReconciliationEngine\\src\\test\\resource\\DestinationDirectory");

		requestDetails.setRequestParameters(requestParameters);

		messageProducer.send(session.createObjectMessage(requestDetails));


		final MessageConsumer messageConsumer = session
				.createConsumer(responseQueue);

		TextMessage textMessage = (TextMessage) messageConsumer.receive(1000);

		System.out.println("textMessage.getText(): " + textMessage.getText());
	}
}
