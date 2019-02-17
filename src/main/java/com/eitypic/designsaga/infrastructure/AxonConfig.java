package com.eitypic.designsaga.infrastructure;

import com.eitypic.designsaga.domain.Document;
import com.eitypic.designsaga.domain.File;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.messaging.interceptors.BeanValidationInterceptor;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {


    @Qualifier("eventSerializer")
    @Bean
    public Serializer eventSerializer() {
        return new JacksonSerializer();
    }

    @Bean
    public EventStorageEngine eventStorageEngine() {
        return new InMemoryEventStorageEngine();
    }

    @Bean
    public CommandBus commandBus() {
        SimpleCommandBus commandBus = new SimpleCommandBus();
        commandBus.registerDispatchInterceptor(new BeanValidationInterceptor<>());
        return commandBus;
    }

    @Bean
    public Repository<Document> documentAggregateRepository(EventStore eventStore) {
        return new EventSourcingRepository<>(Document.class, eventStore);
    }

    @Bean
    public Repository<File> fileAggregateRepository(EventStore eventStore) {
        return new EventSourcingRepository<>(File.class, eventStore);
    }

}
