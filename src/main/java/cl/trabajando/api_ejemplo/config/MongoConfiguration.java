package cl.trabajando.api_ejemplo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.geo.GeoJsonModule;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fasterxml.jackson.databind.Module;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
/**
 * 
 * @author Cristian Gonzalez
 * 13 ago. 2019 
 *
 *Esta clase inicializa y prepara la conexion a MongoDB
 */
@Configuration
@EnableAutoConfiguration(exclude = { XADataSourceAutoConfiguration.class, MongoAutoConfiguration.class })
@EnableMongoRepositories(basePackages = "cl.trabajando.api_ejemplo.repo.mongo", mongoTemplateRef = "primaryMongoTemplate")
@EnableCaching
@EnableScheduling
public class MongoConfiguration {
    @Autowired
    private Environment env;

    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("mongoDbFactory") MongoDbFactory mongoFactory) {
	return new MongoTemplate(mongoFactory, getDefaultMongoConverter(mongoFactory));
    }

    @Bean
    MappingMongoConverter getDefaultMongoConverter(@Qualifier("mongoDbFactory") MongoDbFactory mongoFactory) {
	MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoFactory),
		new MongoMappingContext());
	converter.setTypeMapper(new DefaultMongoTypeMapper(null));
	return converter;
    }

    @Primary
    @Bean("mongoDbFactory")
    public MongoDbFactory mongoDbFactory(MongoProperties mongoProperties) {
	return new SimpleMongoDbFactory(this.mongoClient(mongoProperties), mongoProperties.getDatabase());
    }

    /**
     * Permite configurar un pool de conexiones a mongo DB
     * @return
     */
    private MongoClientOptions buildMongoClient() {
	return MongoClientOptions.builder()
		.connectionsPerHost(
			env.getRequiredProperty("cl.trabajando.data.mongodb.mongo-pool.max-connections", int.class))
		.minConnectionsPerHost(
			env.getRequiredProperty("cl.trabajando.data.mongodb.mongo-pool.min-connections", int.class))
		.maxConnectionIdleTime(env.getRequiredProperty(
			"cl.trabajando.data.mongodb.mongo-pool.max-idle-milliseconds", int.class))
		.threadsAllowedToBlockForConnectionMultiplier(env.getRequiredProperty(
			"cl.trabajando.data.mongodb.mongo-pool.threads-allowed-to-block-for-connection-multiplier",
			int.class))
		.build();
    }

    @Primary
    @Bean(name = "mongoProperties")
    @ConfigurationProperties(prefix = "cl.trabajando.data.mongodb")
    public MongoProperties properties() {
	return new MongoProperties();
    }

    @Bean(name = "mongoClient")
    public MongoClient mongoClient(@Qualifier("mongoProperties") MongoProperties properties) {
	MongoClientOptions build = buildMongoClient();
	return new MongoClientFactory(properties, env).createMongoClient(build);
    }

    @Bean
    public Module registerGeoJsonModule() {
	return new GeoJsonModule();
    }
}
