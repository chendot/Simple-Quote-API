package com.chendot.quote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class CFQuoteApplication {

	// private static final Logger log = LoggerFactory.getLogger(CFQuoteApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CFQuoteApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) { 
		// 使用RestTemplateBuilder注入
		return builder.build();
	}

	// @Bean
	// public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
	// 	return args -> {
	// 		Quote quote = restTemplate.getForObject("https://quoters.apps.pcfone.io/api/random", Quote.class);
	// 		log.info(quote.toString());
	// 	};
	// }

}
