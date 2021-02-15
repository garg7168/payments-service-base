package com.citi.payment.Exception;

import java.util.Map;
import java.util.function.BiConsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;

import com.citi.payment.constants.PaymentConstant;
import com.citi.payment.model.Error;
import com.citi.payment.model.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * KafkaHandler class which will be automatically called when exception occured
 * in consumer side
 *
 */
public class KafkaErrorHandler implements BiConsumer<ConsumerRecord<?, ?>, Exception> {

	public static final Logger LOGGER = LoggerFactory.getLogger(KafkaErrorHandler.class);
	private KafkaTemplate kafkaTemplate;
	private ObjectMapper objectMapper;
	private Map<String, String> kafkaReqResTopicsMap;
	String error_code="1111";

	public KafkaErrorHandler(KafkaTemplate<Object, Object> kafkaTemplate, ObjectMapper objectMapper,
			Map<String, String> kafkaReqResTopicsMap) {
		super();
		this.kafkaTemplate = kafkaTemplate;
		this.objectMapper = objectMapper;
		this.kafkaReqResTopicsMap = kafkaReqResTopicsMap;
	}

	/**
	 * this method will be called automatically when Exception occured in Kafka
	 * Consumer
	 *
	 */
	@Override
	public void accept(ConsumerRecord<?, ?> consumerRecord, Exception ex) {

		LOGGER.error("Error for Record ={}, error={} " + ex.getMessage());
		//error_code=ex.getCause().getMessage();
		//System.out.println("Exception cause:---"+ex.getCause()+ "\n----"+ex.getCause().getMessage());
		//System.out.println("msg code-----------"+ex.getMessage().substring(ex.getMessage().length()-4));
		System.out.println("Error for Record ={}, error={} " + ex.getMessage());
		byte[] kafka_correlationId = getCorrelationId(consumerRecord.headers());
		ProducerRecord<Object, Object> producerRecord = new ProducerRecord<>(
				kafkaReqResTopicsMap.get(consumerRecord.topic()), objectMapper.valueToTree(mapErrorToResponse()));
		producerRecord.headers().add(KafkaHeaders.CORRELATION_ID, kafka_correlationId);
		kafkaTemplate.send(producerRecord);
	}

	private Response mapErrorToResponse() {
		Response res = new Response();
		Error error = new Error();
		error.setErrorCode(PaymentConstant.INTERNAL_SERVER_ERROR);
		error.setErrorMessage(PaymentConstant.INTERNAL_SERVER_ERROR_MSG);
		//error.setErrorCode(error_code);
		//error.setErrorMessage(PaymentConstant.error_message(error_code));
		//System.out.println("error code: "+error_code+" error msg: "+PaymentConstant.error_message(error_code));
		res.setError(error);
		return res;
	}

	private byte[] getCorrelationId(Headers headers) {
		byte[] kafka_correlationId = null;
		for (final Header header : headers) {
			final String attributeName = header.key();
			if (attributeName.equalsIgnoreCase(PaymentConstant.KAFKA_CORRELATIONID)) {
				kafka_correlationId = header.value();
			}
		}
		return kafka_correlationId;
	}

}
