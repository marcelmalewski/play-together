package com.marcel.malewski.playtogetherapi.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

//TODO jak to będzie dzialac i bede mial sessionFactory to wtedy dodać open-in-view: false

@Configuration
public class HibernateConfig {
	@Autowired
	private DataSource dataSource;

	@Value("${spring.jpa.properties.hibernate.dialect}")
	private String hibernateDialect;

	@Value("${spring.jpa.properties.hibernate.jdbc.time_zone}")
	private String hibernateTimeZone;

	@Value("${spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation}")
	private String hibernateLobCreation;

	@Value("${spring.jpa.properties.hibernate.validator.apply_to_ddl}")
	private String hibernateApplyToDdl;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String hibernateDdlAuto;

	@Value("${spring.jpa.show-sql}")
	private String hibernateShowSql;

	@Bean(name="entityManagerFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory
			= new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	private Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", hibernateDialect);
		properties.put("hibernate.show_sql", hibernateShowSql);
		properties.put("hibernate.jdbc.time_zone", hibernateTimeZone);
		properties.put("hibernate.jdbc.lob.non_contextual_creation", hibernateLobCreation);
		properties.put("hibernate.validator.apply_to_ddl", hibernateApplyToDdl);
		properties.put("hibernate.hbm2ddl.auto", hibernateDdlAuto);

		return properties;
	}
}
