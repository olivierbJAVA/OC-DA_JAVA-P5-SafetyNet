package com.safetynet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class in charge of launching the program.
 */
@SpringBootApplication
@EnableSwagger2
public class SafetyNetApplication {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetApplication.class);

	/**
	 * Launch the program.
	 * 
	 * @param args Command line arguments None arguments are needed
	 */
	public static void main(String[] args) {
		logger.info("Launch SafetyNet Application");

		SpringApplication.run(SafetyNetApplication.class, args);
	}

	@Bean
	public HttpTraceRepository httpTraceRepository() {
		return new InMemoryHttpTraceRepository();
	}

}
