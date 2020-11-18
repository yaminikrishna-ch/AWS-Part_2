package com.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DynamoDB.DynamoDB_Data;

@RestController
@RequestMapping("/sqs")
public class SQSController {

	private static final Logger LOG = LoggerFactory.getLogger(SQSController.class);

	String message;
	@Value("${cloud.aws.end-point.uri}")
	private String sqsEndPoint;

	//@GetMapping
	//public void sendMessage() {//
	//	queueMessagingTemplate.send(sqsEndPoint, MessageBuilder.withPayload("hello from Spring Boot").build());
	//}
	
	@SqsListener("FirstQueue")
	public void getMessage(String message) {
		this.message = message;
		LOG.info("Message from SQS Queue - "+message);
		DynamoDB_Data db = new DynamoDB_Data();
		db.StringToJson(message);
	}
	@RequestMapping("/")
	public String returnValue() {
		return message;
	}
	
}
