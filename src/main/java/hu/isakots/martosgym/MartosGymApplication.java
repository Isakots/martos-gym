package hu.isakots.martosgym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties
@PropertySource("file:${global.appconf.dir}/jwt.properties")
public class MartosGymApplication {

    public static void main(String[] args) {
        SpringApplication.run(MartosGymApplication.class, args);
    }

}
