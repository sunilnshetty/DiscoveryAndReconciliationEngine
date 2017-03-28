/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
//START SNIPPET: code
package com.lte.discovery.reconciliation.engine;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;

import com.lte.discovery.reconciliation.engine.dto.RequestDetails;

@MessageDriven
public class ChatBeanTopic implements MessageListener {

    @Resource
    private TopicConnectionFactory connectionFactory;

    @Resource(name = "AnswerTopic")
    private Topic answerQueue;

    public void onMessage(Message message) {
        System.out.println("ChatBeanTopic.onMessage()");
        
        if (message instanceof ObjectMessage) {
	    final ObjectMessage objectMessage = (ObjectMessage) message;

	    try {
		RequestDetails requestDetails = (RequestDetails) objectMessage
			.getObject();
		System.out.println(requestDetails.toString());

//		sendToAdapter(RequestManager.create(requestDetails));

	    } catch (JMSException e) {
		System.err.println(e.getMessage());
	    }
	} else if (message instanceof TextMessage) {
	    final TextMessage textMessage = (TextMessage) message;
	    try {
		System.out.println("textMessage.getText(): " + textMessage.getText());
	    } catch (JMSException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	} else {
	    System.err.println("Message type " + message.getClass().getName()
		    + " is not handled.");
	}
    }

    private void respond(String text) throws JMSException {

        Connection connection = null;
        Session session = null;

        try {
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(answerQueue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a message
            TextMessage message = session.createTextMessage(text);

            // Tell the producer to send the message
            producer.send(message);
        } finally {
            // Clean up
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
//END SNIPPET: code
