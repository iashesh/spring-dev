package com.binarray.spring.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Implement SpringBoot Batch Service with Retry and Skip Example.
 * 
 * https://spring.io/guides/gs/batch-processing/#scratch
 * 
 * @author Ashesh Krishna
 *
 */
@SpringBootApplication
public class SpringbootBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBatchApplication.class, args);
	}

}
