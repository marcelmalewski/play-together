package com.marcel.malewski.playtogetherapi.setup;

import org.springframework.context.annotation.Configuration;

//TODO jak z hibernate6 dodać sessionFactory i wtedy dodać open-in-view: false
@Configuration
public class HibernateConfig {

//	@Autowired
//	DataSource dataSource;

//	@Bean
//	public SessionFactory sessionFactory() {
//		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure().build();
//		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
//		return meta.getSessionFactoryBuilder().build();
//	}

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
}
