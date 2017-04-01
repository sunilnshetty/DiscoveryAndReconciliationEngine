package com.lte.discovery.reconciliation.adapter;

import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

@MessageDriven
public class AdapterBean implements MessageListener {

	@Resource
	private ConnectionFactory connectionFactory;

	@Resource(name = "DiscoveryBean")
	private Queue requestQueue;

	@Override
	public void onMessage(Message message) {
		System.out.println("AdapterBean.onMessage()");

		if (message instanceof TextMessage) {
			System.out.println("message is instanceof TextMessage.");

			TextMessage textMessage = (TextMessage) message;
			try {
				String adapterRequest = textMessage.getText();
				System.out.println("adapterRequest: " + adapterRequest);
				
				sendAdpaterResponseToDiscoveryEngine(NMSAdapter.process(adapterRequest));
			} catch (JMSException e) {
				System.err.println(e.getMessage());
			}
		} else {
			System.err.println("message is not handled.");
		}
	}

	private void sendAdpaterResponseToDiscoveryEngine(String adapterResponse) {
		Connection connection = null;
		Session session = null;
		
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			MessageProducer messageProducer = session.createProducer(requestQueue);
			messageProducer.send(session.createTextMessage(adapterResponse));
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
