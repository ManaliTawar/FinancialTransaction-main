package dev.codescreen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinancialTransactionApplication {

	public static void main(String[] args) {
		
		final Logger logger = LoggerFactory.getLogger(FinancialTransactionApplication.class);
		
		logger.info("\n----Begin logging FinancialTransactionApplication----");
		SpringApplication.run(FinancialTransactionApplication.class, args);
		logger.info("\n----Begin logging FinancialTransactionApplication----");
	}

}
