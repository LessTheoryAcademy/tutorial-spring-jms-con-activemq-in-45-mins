package com.lta.springjms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lta.springjmscommons.message.OrderMessage;

@SpringBootApplication
public class SpringJmsPublisherApplication {

	public static void main(String[] args) {
		
		ConfigurableApplicationContext context = SpringApplication.run(SpringJmsPublisherApplication.class, args);
		
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		
		System.out.println("Sending message");
		
		OrderMessage order = new OrderMessage();
		order.setCustomerName("my customer name");
		order.setCustomerAddress("my customer address");
		order.setNotes("notes....");
		
		List<Integer> productIds = new ArrayList<Integer>();
		productIds.add(10);
		productIds.add(23);
		productIds.add(32);
		productIds.add(67);
		
		order.setProductsIds(productIds);
		
		String orderString = "";
		
		try {
			
			orderString = new ObjectMapper().writeValueAsString(order);
			
			jmsTemplate.convertAndSend("incoming-orders-queue", orderString);
			
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}
	}

}
