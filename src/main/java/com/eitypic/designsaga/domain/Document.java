package com.eitypic.designsaga.domain;

import com.eitypic.designsaga.domain.coreapi.CreateDocumentCommand;
import com.eitypic.designsaga.domain.coreapi.DocumentCreatedEvent;
import com.eitypic.designsaga.domain.coreapi.DocumentId;
import com.eitypic.designsaga.domain.coreapi.FileId;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

@Slf4j
@Aggregate
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Document {

    @EqualsAndHashCode.Include
    @AggregateIdentifier
    private DocumentId documentId;

    private String name;

    private List<FileId> fileIds;

    public Document(CreateDocumentCommand command) {
        logger.debug(this.getClass().getSimpleName() + " - " + command.getClass().getSimpleName());
        AggregateLifecycle.apply(new DocumentCreatedEvent(
                command.getId(),
                command.getName()
        ));
    }

    @EventSourcingHandler
    public void on(DocumentCreatedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " - " + event.getClass().getSimpleName());
        this.documentId = event.getDocumentId();
        this.name = event.getName();
    }
}
