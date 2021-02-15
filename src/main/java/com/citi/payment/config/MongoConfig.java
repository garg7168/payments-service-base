package com.citi.payment.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
public class MongoConfig {

	@Value("${citi.config.mongodb.mongoURI}")
	private String mongoURI;
	@Value("${citi.config.mongodb.mongoDBName}")
	private String mongoDbName;

	public String getDatabaseName() {

		return mongoDbName;
	}

	@Bean
	public MongoDbFactory mongoDbFactory() {

		return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
	}

	@Bean
	public MongoTemplate mongoTemplate() {

		DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
		MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(dbRefResolver,
				new MongoMappingContext());
		// Don't save _class to mongo
		mappingMongoConverter.setTypeMapper(new DefaultMongoTypeMapper(null));
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory(), mappingMongoConverter);
		return mongoTemplate;
	}

	@Bean(name = "mongoClient")
	@ConditionalOnProperty(value = "citi.config.mongodb.mongoURI")
	public MongoClient mongoClient() {

		//"mongodb://localhost:27030/kafka?replicaSet=mySet"
		MongoClientURI client = new MongoClientURI(mongoURI);
		return new MongoClient(client);
	}

	@Bean
	@ConditionalOnBean(name = "mongoClient")
	@Primary
	MongoTransactionManager transactionManager(MongoClient mongoClient) {

		return new MongoTransactionManager(mongoDbFactory());
	}
}