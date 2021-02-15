/*package com.citi.payment.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.citi.payment.Exception.KafkaErrorHandler;
import com.citi.payment.constants.PaymentConstant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class KafkaReplyResponseConfig {

	@Value("${citi.config.acks_config:all}")
	private String kafkaackconfig;
	@Value("${citi.config.kafka.bootstrap-server}")
	private String kafkaBootstrapServers;
	@Value("${citi.config.kafkaRequestTimeoutMs:60000}")// Why you set this limit
	private Integer kafkaRequestTimeoutMs;
	@Value("${citi.config.kafkaRetryBackofMs:200}")
	private Integer kafkaRetryBackofMs;
	@Value("${citi.config.kafkaDeliveryTimeoutMs:120000}")
	private Integer kafkaDeliveryTimeoutMs;

	@Value("${citi.config.kafkaRetryCount:1}")
	private Integer kafkaRetryCount;
	
	//static block for map loading
	
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "grp");
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
		return props;
	}

	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaRequestTimeoutMs);
		props.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, kafkaDeliveryTimeoutMs);
		props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, kafkaRetryBackofMs);
		props.put(ProducerConfig.ACKS_CONFIG, kafkaackconfig);
		return props;
	}

	@Bean
	public KafkaTemplate<Object, Object> replyTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	@ConditionalOnProperty(value = "citi.config.kafka.bootstrap-server")// 
	public ConsumerFactory<Object, Object> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigs());
	}

	@Bean
	public ProducerFactory<Object, Object> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Object>> requestListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setReplyTemplate(replyTemplate());
		factory.setErrorHandler(new SeekToCurrentErrorHandler(
				new KafkaErrorHandler(replyTemplate(), objectMapper(), kafkaReqResTopicsMap()), 1));
		return factory;
	}

	@Bean
	public KafkaMessageListenerContainer<Object, Object> replyListenerContainer() {
		ContainerProperties containerProperties = new ContainerProperties("account-get-response",
				"account-put-response", "account-post-response", "account-delete-response");
		return new KafkaMessageListenerContainer<>(consumerFactory(), containerProperties);
	}

	@Bean
	public ReplyingKafkaTemplate<Object, Object, Object> replyKafkaTemplate(ProducerFactory<Object, Object> pf,
			KafkaMessageListenerContainer<Object, Object> lc) {
		ReplyingKafkaTemplate<Object, Object, Object> replyKafkaTemplate = new ReplyingKafkaTemplate<>(pf, lc);
		replyKafkaTemplate.setReplyTimeout(300000);
		replyKafkaTemplate.setSharedReplyTopic(true);
		return replyKafkaTemplate;
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public Map<String, String> kafkaReqResTopicsMap() {
		Map<String, String> kafkaReqResTopics = new HashMap<>();
		InputStream is = KafkaReplyResponseConfig.class.getResourceAsStream(PaymentConstant.KAFKA_REQRES_TOPIC_JSON_PATH);
		try {
			kafkaReqResTopics = objectMapper().readValue(is, new TypeReference<Map<String, String>>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return kafkaReqResTopics;
	}

}*/
