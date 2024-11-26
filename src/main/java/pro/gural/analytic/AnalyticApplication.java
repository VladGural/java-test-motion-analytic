package pro.gural.analytic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(scanBasePackages = {
        "pro.gural.analytic",
        "pro.gural.common",
        "pro.gural.exception"
})
@EnableKafka
public class AnalyticApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnalyticApplication.class, args);
    }

}
