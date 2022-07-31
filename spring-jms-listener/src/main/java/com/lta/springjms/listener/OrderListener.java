package com.lta.springjms.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lta.springjmscommons.message.OrderMessage;

@Component
public class OrderListener {

	@JmsListener(destination = "incoming-orders-queue", containerFactory = "jmsListenerFactory")
	public void receiveMessage(String order) {

		System.out.println("Received : " + order);
		
		OrderMessage orderImpl;
		
		try {
			
			orderImpl = new ObjectMapper().readValue(order, OrderMessage.class);
			
			System.out.println(orderImpl);
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
