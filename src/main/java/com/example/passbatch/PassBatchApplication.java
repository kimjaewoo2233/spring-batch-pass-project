package com.example.passbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//원래 잡 파일을 나눠서 한다 근데 테스트용이므로 여기에 대략 구현한 번해봄
@EnableBatchProcessing
@SpringBootApplication
public class PassBatchApplication {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    public PassBatchApplication(JobBuilderFactory jobBuilderFactory,StepBuilderFactory stepBuilderFactory){
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;

    }

    @Bean
    public Step passStep(){
            return this.stepBuilderFactory
                    .get("passStep")
                    .tasklet((contribution, chunkContext) -> {
                        System.out.println("Execute PassStep");
                        return RepeatStatus.FINISHED;
                    }).build();

    }

    @Bean
    public Job passJob(){
        return this.jobBuilderFactory
                .get("passJob")
                .start(passStep())
                .build();

    }


    public static void main(String[] args) {
        SpringApplication.run(PassBatchApplication.class, args);
    }

}
