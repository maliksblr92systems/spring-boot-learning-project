package com.evergreen.EvergreenAuthServer.controllers;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private Job customerCsvImportJob;
    private JobLauncher jobLauncher;


    public JobController(@Qualifier("getCustomerCsvImportBatchJob") Job customerCsvImportJob, JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
        this.customerCsvImportJob = customerCsvImportJob;
    }



    @PostMapping("/csv/customer")
    public String startJob() {
        JobParameters jobParameters = new JobParametersBuilder().addLong("startAt", System.currentTimeMillis()).toJobParameters();
        try {
            jobLauncher.run(customerCsvImportJob, jobParameters);
            return "success";
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
            return "error";
        } catch (JobRestartException e) {
            e.printStackTrace();
            return "error";
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
            return "error";
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
            return "error";
        }

    }
}
