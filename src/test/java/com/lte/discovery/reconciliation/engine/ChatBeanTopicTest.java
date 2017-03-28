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

import junit.framework.TestCase;

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
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

public class ChatBeanTopicTest extends TestCase {

    @Resource
    private TopicConnectionFactory connectionFactory;

    @Resource(name = "ChatBeanTopic")
    private Topic questionQueue;

    @Resource(name = "AnswerTopic")
    private Topic answerQueue;

    public void test() throws Exception {
        EJBContainer.createEJBContainer().getContext().bind("inject", this);

        final TopicConnection connection = connectionFactory.createTopicConnection();

        connection.start();

        final TopicSession session = connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);

//        final MessageProducer questions = session.createProducer(questionQueue);
//
//        final MessageConsumer answers = session.createConsumer(answerQueue);
        
        final TopicPublisher questions = session.createPublisher(questionQueue);

        sendText("Hello World!", questions, session);

//        assertEquals("Hello, Test Case!", receiveText(answers));
        
//        System.out.println("receiveText(answers): " + receiveText(answers));
//
//        sendText("How are you?", questions, session);
//
//        assertEquals("I'm doing well.", receiveText(answers));
//
//        sendText("Still spinning?", questions, session);
//
//        assertEquals("Once every day, as usual.", receiveText(answers));

    }

    private void sendText(String text, TopicPublisher questions, TopicSession session) throws JMSException {

        questions.send(session.createTextMessage(text));
        
//        questions.publish(session.createTextMessage(text));

    }

    private String receiveText(MessageConsumer answers) throws JMSException {

        return ((TextMessage) answers.receive(1000)).getText();

    }

}
//END SNIPPET: code
