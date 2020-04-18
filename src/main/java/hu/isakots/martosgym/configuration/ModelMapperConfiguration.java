package hu.isakots.martosgym.configuration;

import hu.isakots.martosgym.configuration.converter.UserToManagedUserConverter;
import hu.isakots.martosgym.configuration.converter.UserToUserWithRolesConverter;
import hu.isakots.martosgym.domain.User;
import hu.isakots.martosgym.rest.account.model.AccountModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
        modelMapper.addConverter(new UserToManagedUserConverter());

        modelMapper.addMappings(
                new PropertyMap<AccountModel, User>() {
                    @Override
                    protected void configure() {
                        skip(destination.getId());
                        skip(destination.getEmail());
                    }
                });

        return modelMapper;
    }
}