package com.lte.discovery.reconciliation.engine;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.lte.discovery.reconciliation.engine.dto.RequestDetails;

@MessageDriven()
public class DiscoveryBean implements MessageListener {

	@Resource
	private ConnectionFactory connectionFactory;

	@Resource(name = "AdapterBean")
	private Queue adapterRequestQueue;

	@Resource(name = "ResponseQueue")
	private Queue responseQueue;

	@Override
	public void onMessage(Message message) {
		System.out.println("DiscoveryBean.onMessage()");

		if (message instanceof ObjectMessage) {
			System.out.println("message is instanceof ObjectMessage.");

			ObjectMessage objectMessage = (ObjectMessage) message;
			RequestDetails requestDetails = null;
			try {
				requestDetails = (RequestDetails) objectMessage.getObject();
			} catch (JMSException e) {
				System.err.println(e.getMessage());
			}

			String adapterRequest = RequestManager.marshal(requestDetails);

			sendAdapterRequest(adapterRequest);

		} else if (message instanceof TextMessage) {
			System.out.println("message is instanceof TextMessage.");

			TextMessage textMessage = (TextMessage) message;

			String adapterResponse = null;
			try {
				adapterResponse = textMessage.getText();
				System.out.println("******** textMessage.getText(): "
						+ adapterResponse);
			} catch (JMSException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			sendAdapterResponse(adapterResponse);

		} else {
			System.err.println("message is not handled.");
		}

	}

	private void sendAdapterResponse(String adapterResponse) {
		Connection connection = null;
		Session session = null;

		try {
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			final MessageProducer messageProducer = session
					.createProducer(responseQueue);
			messageProducer.send(session.createTextMessage(adapterResponse));

		} catch (JMSException e) {
			System.err.println(e.getMessage());
		}

	}

	private void sendAdapterRequest(String adapterRequest) {
		Connection connection = null;
		Session session = null;

		try {
			connection = connectionFactory.createConnection();
			connection.start();

			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			final MessageProducer messageProducer = session
					.createProducer(adapterRequestQueue);
			messageProducer.send(session.createTextMessage(adapterRequest));

		} catch (JMSException e) {
			System.err.println(e.getMessage());
		}

	}
}
