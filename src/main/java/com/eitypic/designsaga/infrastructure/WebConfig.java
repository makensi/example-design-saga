package com.eitypic.designsaga.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.SpringHandlerInstantiator;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.MapperFeature.SORT_PROPERTIES_ALPHABETICALLY;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
@EnableWebMvc
// Respect the methods names be cause in most of the cases those are the beans names (instead to use @Bean(name="beanName"))
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private HandlerInstantiator handlerInstantiator;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .featuresToEnable(
                        WRITE_DATES_AS_TIMESTAMPS, // respect the ISO8601 format
                        SORT_PROPERTIES_ALPHABETICALLY // already in decision
                )
                .serializationInclusion(
                        NON_NULL // remove null values
                )
                // injection in validators
                .handlerInstantiator(handlerInstantiator)
                // a part of the default ones will include others like @JsonComponent
                .findModulesViaServiceLoader(true)
                .build();
    }

    // enable the default static resources handling in webapp
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // management of the output
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.enableContentNegotiation(new MappingJackson2JsonView(objectMapper));
    }

    // management of the input
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
    }

    @Bean
    public HandlerInstantiator handlerInstantiator(ApplicationContext applicationContext) {
        return new SpringHandlerInstantiator(applicationContext.getAutowireCapableBeanFactory());
    }

}