package hu.isakots.martosgym.configuration;

import hu.isakots.martosgym.configuration.filter.CharsetFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    @Bean
    public FilterRegistrationBean<CharsetFilter> getCharsetFilter() {
        FilterRegistrationBean<CharsetFilter> charsetFilterBean = new FilterRegistrationBean<>();
        charsetFilterBean.setFilter(new CharsetFilter());
        charsetFilterBean.addUrlPatterns("/*");
        return charsetFilterBean;
    }

}