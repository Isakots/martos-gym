package hu.isakots.martosgym;

import hu.isakots.martosgym.configuration.properties.ImageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableConfigurationProperties({ImageProperties.class})
@PropertySource("file:${appconf.dir}/jwt.properties")
@PropertySource("file:${appconf.dir}/file-upload.properties")
public class MartosGymApplication {

    public static void main(String[] args) {
        SpringApplication.run(MartosGymApplication.class, args);
    }

}
