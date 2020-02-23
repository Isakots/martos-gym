package hu.isakots.martosgym.configuration;

import hu.isakots.martosgym.configuration.properties.MultiPartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.nio.charset.StandardCharsets;

@Configuration
@EnableConfigurationProperties(MultiPartProperties.class)
@PropertySource("file:${appconf.dir}/file-upload.properties")
public class MultiPartResolverConfiguration {

    private final MultiPartProperties multiPartProperties;

    public MultiPartResolverConfiguration(MultiPartProperties multiPartProperties) {
        this.multiPartProperties = multiPartProperties;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding(StandardCharsets.UTF_8.name());
        resolver.setMaxUploadSize(multiPartProperties.getMaxRequestSize());
        resolver.setMaxUploadSizePerFile(multiPartProperties.getMaxFileSize());
        return resolver;
    }
}