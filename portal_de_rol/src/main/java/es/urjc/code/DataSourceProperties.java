package es.urjc.code;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class DataSourceProperties {

	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.first")
	public DataSource firstDataSource() {
	    return DataSourceBuilder.create().type(DataSource.class).build();
	}

	@Bean
	@ConfigurationProperties("spring.datasource.slave")
	public DataSource secondDataSource() {
	    return DataSourceBuilder.create().type(DataSource.class).build();
	}
}

