package com.iquote.batch.config;

import java.util.Properties;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

@Configuration
public class BatchConfiguration {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    // @Autowired
    // private JobLauncher jobLauncher;

    @Bean
    public TransactionProxyFactoryBean baseProxy() {
        TransactionProxyFactoryBean transactionProxyFactoryBean = new TransactionProxyFactoryBean();
        Properties transactionAttributes = new Properties();
        transactionAttributes.setProperty("*", "PROPAGATION_REQUIRED"); // 所有操作的事务传播机制都设置为REQUIRED
        transactionAttributes.setProperty("*", "ISOLATION_READ_COMMITTED");
        transactionProxyFactoryBean.setTransactionAttributes(transactionAttributes);
        transactionProxyFactoryBean.setTarget(jobRepository);
        // TransactionProxyFactoryBean创建事务代理时，需要了解当前事务所处的环境，该环境属性通过PlatformTransactionManager实例（其实现类的实例）传入
        transactionProxyFactoryBean.setTransactionManager(transactionManager);
        return transactionProxyFactoryBean;
    }

    // @Bean
    // public JobLauncher simpleJobLauncher() {
    //     SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
    //     jobLauncher.setJobRepository(jobRepository);
    //     return jobLauncher;
    // }

}