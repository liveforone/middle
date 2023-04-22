package middle.recommendservice;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class RecommendServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommendServiceApplication.class, args);
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}

	@Bean
	public Logger.Level feignLoggerLeve() {
		return Logger.Level.FULL;
	}

}
