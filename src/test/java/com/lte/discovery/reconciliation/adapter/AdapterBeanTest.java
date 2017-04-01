package com.lte.discovery.reconciliation.adapter;

import javax.annotation.Resource;
import javax.ejb.embeddable.EJBContainer;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.NamingException;

import org.junit.Test;

public class AdapterBeanTest {

	@Resource
	private ConnectionFactory connectionFactory;

	@Resource(name = "AdapterBean")
	private Queue adapterRequestQueue;

	@Resource(name = "AdapterResponseQueue")
	private Queue adapterResponseQueue;

	@Test
	public void test() throws NamingException, JMSException {

		EJBContainer.createEJBContainer().getContext().bind("inject", this);

		final Connection connection = connectionFactory.createConnection();

		connection.start();

		final Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		final MessageProducer messageProducer = session
				.createProducer(adapterRequestQueue);

		messageProducer.send(session.createTextMessage("Hello World!!!"));

		final MessageConsumer messageConsumer = session
				.createConsumer(adapterResponseQueue);

		messageConsumer.receive(1000);
	}
}
