package com.iquote.batch.config;

import java.util.Properties;

import javax.sql.DataSource;

import com.iquote.batch.listener.JobCompletionNotificationListener;
import com.iquote.batch.model.Person;
import com.iquote.batch.processor.PersonItemProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

@Configuration
public class BatchConfiguration {
    private static final Logger log = LoggerFactory.getLogger(BatchConfiguration.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private PersonItemProcessor personItemProcessor;

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

    @Bean
    public JobLauncher simpleJobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        return jobLauncher;
    }

    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>().name("personItemReader")
                .resource(new ClassPathResource("sample-data.csv")).delimited()
                .names(new String[] { "firstName", "lastName" })
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {
                    {
                        setTargetType(Person.class);
                    }
                }).build();
    }

    @Bean
    public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Person>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)").dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step importStep) {
        return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener).flow(importStep)
                .end().build();
    }

    @Qualifier(value = "step1")
    @Bean
    public Step importStep(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("importStep").<Person, Person>chunk(100).reader(reader()).processor(personItemProcessor)
                .writer(writer).build();
    }

    // @Qualifier(value = "step1")
    // @Bean
    // public Step processStep() {
    //     log.info("testStep!!!!");
    //     return stepBuilderFactory.get("processStep").chunk(10).reader(reader()).writer(writer(dataSource())).build();
    // }

}