package com.eitypic.designsaga.domain;

import com.eitypic.designsaga.domain.coreapi.*;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Slf4j
@Aggregate
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Document {

    @EqualsAndHashCode.Include
    @AggregateIdentifier
    private DocumentId id;

    private String name;

    private List<FileId> fileIds;

    private boolean deleted = false;

    public Document(CreateDocumentCommand command) {
        logger.debug(this.getClass().getSimpleName() + " > " + command.getClass().getSimpleName());
        apply(new CreateDocumentRequestedEvent(
                command.getId(),
                command.getName(),
                command.getNumberOfFiles()
        ));
    }

    @CommandHandler
    public void handle(FinishDocumentCreationCommand command) {
        logger.debug(this.getClass().getSimpleName() + " > " + command.getClass().getSimpleName());
        apply(new DocumentCreatedEvent(
                id,
                fileIds
        ));
    }

    @CommandHandler
    public void handle(DeleteDocumentCommand command) {
        logger.debug(this.getClass().getSimpleName() + " > " + command.getClass().getSimpleName());
        apply(new DocumentDeletedEvent(id));
    }

    @EventSourcingHandler
    public void on(CreateDocumentRequestedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " > " + event.getClass().getSimpleName());
        id = event.getDocumentId();
        name = event.getName();
    }

    @EventSourcingHandler
    public void on(DocumentCreatedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " > " + event.getClass().getSimpleName());
        fileIds = event.getFileIds();
    }

    @EventSourcingHandler
    public void on(DocumentDeletedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " > " + event.getClass().getSimpleName());
        deleted = true;
    }

}
