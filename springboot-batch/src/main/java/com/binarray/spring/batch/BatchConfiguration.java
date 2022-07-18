/**
 * 
 */
package com.binarray.spring.batch;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.retry.backoff.FixedBackOffPolicy;

/**
 * @author Ashesh Krishna
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

  @Autowired
  public JobBuilderFactory jobBuilderFactory;

  @Autowired
  public StepBuilderFactory stepBuilderFactory;

  @Bean
  public FlatFileItemReader<Person> reader() {
    return new FlatFileItemReaderBuilder<Person>()
      .name("personItemReader")
      .resource(new ClassPathResource("sample-data.csv"))
      .delimited()
      .names(new String[]{"id", "firstName", "lastName"})
      .fieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
        setTargetType(Person.class);
      }})
      .build();
  }

  @Bean
  public PersonItemProcessor processor() {
    return new PersonItemProcessor();
  }

  @Bean
  public JdbcBatchItemWriter<Person> writer(DataSource dataSource) {
    return new JdbcBatchItemWriterBuilder<Person>()
      .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
      .sql("INSERT INTO PEOPLE (person_id, first_name, last_name) VALUES (:id, :firstName, :lastName)")
      .dataSource(dataSource)
      .build();
  }
  
  @Bean
  public Job importUserJob(JobCompletionNotificationListener listener, Step stepWithRetry) {
    return jobBuilderFactory.get("importUserJob")
      .incrementer(new RunIdIncrementer())
      .listener(listener)
      .flow(stepWithRetry)
      .end()
      .build();
  }

  @Bean
  public Step stepWithRetry(JdbcBatchItemWriter<Person> writer) {
    return stepBuilderFactory.get("stepWithRetry")
      .<Person, Person> chunk(2)
      .reader(reader())
      .processor(processor())
      .writer(writer)
      .faultTolerant()
      .retryLimit(3)
      .retry(DuplicateKeyException.class)
      .skipLimit(2)
      //.skip(DuplicateKeyException.class)	// Skip based on exception class
      .skipPolicy(new CustomSkipPolicy())	// Skip based on custom skip policy
      .backOffPolicy(new FixedBackOffPolicy())
      .listener(new PersonSkipListener())
      .build();
  }
}
