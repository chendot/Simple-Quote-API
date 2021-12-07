package com.iquote.batch.config.job;

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
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
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

@Configuration
public class JobSample {
    private static final Logger log = LoggerFactory.getLogger(JobSample.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private PersonItemProcessor personItemProcessor;

    @Autowired
    @Qualifier(value = "killUserTasklet")
    private Tasklet killUserTasklet;

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

    /**
     * incrementer(new RunIdIncrementer()
     * @param listener
     * @param importStep
     * @return
     */
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step importStep) {
        return jobBuilderFactory.get("importUserJob").incrementer(new RunIdIncrementer()).listener(listener)
                .flow(importStep)
                .end().build();
    }

    /**
     * allowStartIfComplete(true) 如果输入参数没有变化或没有传入任何参数，则可以通过设置该参数允许重跑任务
     * @param writer
     * @return
     */ 
    @Qualifier(value = "step1")
    @Bean
    public Step importStep(JdbcBatchItemWriter<Person> writer) {
        return stepBuilderFactory.get("importStep").<Person, Person>chunk(100).reader(reader())
                .processor(personItemProcessor)
                .writer(writer).allowStartIfComplete(true).build();
    }

    // @Qualifier(value = "step1")
    // @Bean
    // public Step processStep() {
    // log.info("testStep!!!!");
    // return
    // stepBuilderFactory.get("processStep").chunk(10).reader(reader()).writer(writer(dataSource())).build();
    // }

    @Bean
    public Job killUserJob() throws Exception {
        return jobBuilderFactory.get("killUserJob").start(killUserStep()).build();
    }

    @Qualifier(value = "killUserStep")
    @Bean
    public Step killUserStep() throws Exception {
        return stepBuilderFactory.get("killUserStep").tasklet(killUserTasklet).build();
    }
}
