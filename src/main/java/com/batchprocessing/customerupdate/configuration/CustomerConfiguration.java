package com.batchprocessing.customerupdate.configuration;

import com.batchprocessing.customerupdate.models.Customer;
import com.batchprocessing.customerupdate.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
@EnableTask
@EnableBatchProcessing
public class CustomerConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("${usage.file.name:classpath:input.json}")
    private Resource usageResource;

    @Bean
    public Job job1(ItemReader<User> reader,
                    ItemProcessor<User, Customer> itemProcessor, ItemWriter<Customer> writer) {
        Step step = stepBuilderFactory.get("CustomerProcessing")
                .<User, Customer>chunk(1)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();

        return jobBuilderFactory.get("CustomerJob")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public JsonItemReader<User> jsonItemReader() {

        ObjectMapper objectMapper = new ObjectMapper();
        JacksonJsonObjectReader<User> jsonObjectReader =
                new JacksonJsonObjectReader<>(User.class);
        jsonObjectReader.setMapper(objectMapper);

        return new JsonItemReaderBuilder<User>()
                .jsonObjectReader(jsonObjectReader)
                .resource(usageResource)
                .name("UsageJsonItemReader")
                .build();
    }

    @Bean
    public ItemWriter<Customer> jdbcBillWriter(DataSource dataSource) {
        JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriterBuilder<Customer>()
                .beanMapped()
                .dataSource(dataSource)
                .sql("INSERT INTO CUSTOMER (id, first_name, " +
                        "last_name, minutes, data_usage,bill_amount) VALUES " +
                        "(:id, :firstName, :lastName, :minutes, :dataUsage, " +
                        ":billAmount)")
                .build();
        return writer;
    }

    @Bean
    ItemProcessor<User, Customer> customerProcessor() {
        return new CustomerProcessor();
    }

}