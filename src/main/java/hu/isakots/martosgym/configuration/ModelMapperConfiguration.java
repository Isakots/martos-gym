package hu.isakots.martosgym.configuration;

import hu.isakots.martosgym.configuration.converter.UserToUserWithRolesConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfiguration {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.addConverter(new UserToUserWithRolesConverter());

        return modelMapper;
    }
}