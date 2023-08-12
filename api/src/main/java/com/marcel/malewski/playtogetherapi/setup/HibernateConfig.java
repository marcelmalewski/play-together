package com.marcel.malewski.playtogetherapi.setup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

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

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory
			= new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setHibernateProperties(hibernateProperties());

		return sessionFactory;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPackagesToScan("com.marcel.malewski.playtogetherapi.entity");

		HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactory.setJpaProperties(hibernateProperties());

		return entityManagerFactory;
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

//TODO jak z hibernate6 dodać sessionFactory i wtedy dodać open-in-view: false

//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.postgresql.Driver");
//		dataSource.setUrl("jdbc:mysql://localhost:5432/play_together");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("postgres");
//		return dataSource;
//	}

//	@Bean
//	public HibernateTransactionManager transactionManager() {
//		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory());
//		transactionManager.setDataSource(dataSource);
//		return transactionManager;
//	}
