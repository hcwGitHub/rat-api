package mk.anyplex.com.hmvodapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class HmvodApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmvodApiApplication.class, args);
    }

}
