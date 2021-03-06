package pl.wpulik.cassandra.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@EnableCassandraRepositories
public class CassandraConfig {
	
	  @Value("${spring.data.cassandra.contact-points}")
	  private String hostList;

	  @Value("${spring.data.cassandra.datacenter}")
	  private String datacenter;

	  @Value("${spring.data.cassandra.keyspace-name}")
	  private String keyspaceName;

	  @Value("${spring.data.cassandra.port}")
	  private Integer port;

	  @Bean
	  public CqlSessionFactoryBean getCqlSession() {
	    CqlSessionFactoryBean factory = new CqlSessionFactoryBean();
	    factory.setPort(port);
	    factory.setKeyspaceName(keyspaceName);
	    factory.setContactPoints(hostList);
	    factory.setLocalDatacenter(datacenter);
	    return factory;
	  }

}
