package ua.foxminded.task10.uml.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
@EnableJpaRepositories(basePackages = "ua.foxminded.task10.uml.repository")
public class JpaConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
