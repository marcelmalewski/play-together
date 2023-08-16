package com.marcel.malewski.playtogetherapi.config;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
////TODO jak uda się to ogarnąć to wtedy dodać open-in-view: false
//@Configuration
//@EnableTransactionManagement
public class HibernateConfig {
//	//TODO pozbyć field injection
//	@Autowired
//	private DataSource dataSource;
//
//	@Value("${spring.jpa.properties.hibernate.dialect}")
//	private String hibernateDialect;
//
//	@Value("${spring.jpa.properties.hibernate.jdbc.time_zone}")
//	private String hibernateTimeZone;
//
//	@Value("${spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation}")
//	private String hibernateLobCreation;
//
//	@Value("${spring.jpa.properties.hibernate.validator.apply_to_ddl}")
//	private String hibernateApplyToDdl;
//
//	@Value("${spring.jpa.hibernate.ddl-auto}")
//	private String hibernateDdlAuto;
//
//	@Value("${spring.jpa.show-sql}")
//	private String hibernateShowSql;
//
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//		LocalContainerEntityManagerFactoryBean entityManagerFactory
//			= new LocalContainerEntityManagerFactoryBean();
//		entityManagerFactory.setDataSource(dataSource);
//		entityManagerFactory.setPackagesToScan("com.marcel.malewski");
//
//		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
//		entityManagerFactory.setJpaProperties(hibernateProperties());
//
//		return entityManagerFactory;
//	}
//
//	private Properties hibernateProperties() {
//		Properties properties = new Properties();
//		properties.put("hibernate.dialect", hibernateDialect);
//		properties.put("hibernate.show_sql", hibernateShowSql);
//		properties.put("hibernate.jdbc.time_zone", hibernateTimeZone);
//		properties.put("hibernate.jdbc.lob.non_contextual_creation", hibernateLobCreation);
//		properties.put("hibernate.validator.apply_to_ddl", hibernateApplyToDdl);
//		properties.put("hibernate.hbm2ddl.auto", hibernateDdlAuto);
//
//		return properties;
//	}
}
