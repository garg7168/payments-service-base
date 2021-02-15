package com.citi.payment.propertyloader;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties(prefix="citi.config")
@Data
@PropertySource(value="file:${CONFIG_DIR}/config.properties",ignoreResourceNotFound = false)
public class ConfigPropertyLoader {
    private String mongoDbUri;
    private String  mongoDbUser;
    private String mongoDbPasword;
    private String mongoDbName;
    private int minMongoPoolSize;
    private int maxMongoPoolSize;
    private long maxWaitTimeMS;
    private long maxConnectionIdleTimeMS;
    private String kafkaBootstrapServers;
    private Integer kafkaConcurrencyLevel;
	public String getMongoDbUri() {
		return mongoDbUri;
	}
	public void setMongoDbUri(String mongoDbUri) {
		this.mongoDbUri = mongoDbUri;
	}
	public String getMongoDbUser() {
		return mongoDbUser;
	}
	public void setMongoDbUser(String mongoDbUser) {
		this.mongoDbUser = mongoDbUser;
	}
	public String getMongoDbPasword() {
		return mongoDbPasword;
	}
	public void setMongoDbPasword(String mongoDbPasword) {
		this.mongoDbPasword = mongoDbPasword;
	}
	public String getMongoDbName() {
		return mongoDbName;
	}
	public void setMongoDbName(String mongoDbName) {
		this.mongoDbName = mongoDbName;
	}
	public int getMinMongoPoolSize() {
		return minMongoPoolSize;
	}
	public void setMinMongoPoolSize(int minMongoPoolSize) {
		this.minMongoPoolSize = minMongoPoolSize;
	}
	public int getMaxMongoPoolSize() {
		return maxMongoPoolSize;
	}
	public void setMaxMongoPoolSize(int maxMongoPoolSize) {
		this.maxMongoPoolSize = maxMongoPoolSize;
	}
	public long getMaxWaitTimeMS() {
		return maxWaitTimeMS;
	}
	public void setMaxWaitTimeMS(long maxWaitTimeMS) {
		this.maxWaitTimeMS = maxWaitTimeMS;
	}
	public long getMaxConnectionIdleTimeMS() {
		return maxConnectionIdleTimeMS;
	}
	public void setMaxConnectionIdleTimeMS(long maxConnectionIdleTimeMS) {
		this.maxConnectionIdleTimeMS = maxConnectionIdleTimeMS;
	}
	public String getKafkaBootstrapServers() {
		return kafkaBootstrapServers;
	}
	public void setKafkaBootstrapServers(String kafkaBootstrapServers) {
		this.kafkaBootstrapServers = kafkaBootstrapServers;
	}
	public Integer getKafkaConcurrencyLevel() {
		return kafkaConcurrencyLevel;
	}
	public void setKafkaConcurrencyLevel(Integer kafkaConcurrencyLevel) {
		this.kafkaConcurrencyLevel = kafkaConcurrencyLevel;
	}
        
}
