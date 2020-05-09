package hu.isakots.martosgym.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource("file:${appconf.dir}/scheduler.properties")
public class SchedulerConfiguration {

}
