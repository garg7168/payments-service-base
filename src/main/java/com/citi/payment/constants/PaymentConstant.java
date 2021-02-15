package com.citi.payment.constants;

import org.springframework.stereotype.Component;

@Component
public class PaymentConstant {
	public static final String INTERNAL_SERVER_ERROR = "CITI1001";
	public static final String INTERNAL_SERVER_ERROR_MSG = "Internal Server Error";

	public static String error_message(String error_code){
		if(error_code.equalsIgnoreCase("EC01"))
				return "Account not exist in database.";
		return INTERNAL_SERVER_ERROR_MSG;
	}
	
	//Kafka CorrelationId
	public static final String KAFKA_CORRELATIONID = "kafka_correlationId";
	
	//Kafka Request/Response map path
	public static final String KAFKA_REQRES_TOPIC_JSON_PATH = "/json_kafka_topics/kafkatopics.json";
}
