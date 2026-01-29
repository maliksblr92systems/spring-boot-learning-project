package com.evergreen.EvergreenAuthServer.jobs.configs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.evergreen.EvergreenAuthServer.models.Customer;
import com.evergreen.EvergreenAuthServer.repositories.CustomerRepository;

// @EnableBatchProcessing (not needed in spring batch 5.0 )
@Configuration
public class CustomerCsvImportBatchJob {

    // private JobBuilder jobBuilder;
    private CustomerRepository customerRepository;
    private PlatformTransactionManager platformTransactionManager;
    private JobRepository jobRepository;

    public CustomerCsvImportBatchJob(CustomerRepository customerRepository, PlatformTransactionManager platformTransactionManager,
            JobRepository jobRepository) {
        this.customerRepository = customerRepository;
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
    }



    @Bean
    public ItemReader<Customer> getCustomerCsvReader() {

        // LineTokenizer start
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "first_name", "last_name", "email", "phone", "city", "country", "age");
        // LineTokenizer end


        // FieldSetMapper start
        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<Customer>();
        fieldSetMapper.setTargetType(Customer.class);
        // FieldSetMapper end

        // LineMapper start
        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        // LineMapper end



        FlatFileItemReader<Customer> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new ClassPathResource("data/csv/customer.csv"));
        flatFileItemReader.setLineMapper(lineMapper);
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setName("customer_csv_reader");
        return flatFileItemReader;

    }


    @Bean
    public ItemProcessor<Customer, Customer> getProcessor() {
        ItemProcessor<Customer, Customer> processor = (Customer item) -> {
            System.out.println("processing item with id " + item.getEmail());
            return item;
        };
        return processor;

    }


    @Bean
    public ItemWriter<Customer> getCsvWriter() {
        RepositoryItemWriter<Customer> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(customerRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;

    }



    @Bean
    public Job getCustomerCsvImportBatchJob() {
        Step firstStep = new StepBuilder("csv-import-step", jobRepository).<Customer, Customer>chunk(10, platformTransactionManager)
                .reader(getCustomerCsvReader()).processor(getProcessor()).writer(getCsvWriter()).build();
        return new JobBuilder("firstJob", jobRepository).start(firstStep).build();
    }



}
