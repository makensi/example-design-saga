package com.eitypic.designsaga.domain;

import com.eitypic.designsaga.domain.coreapi.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Slf4j
@Aggregate
@NoArgsConstructor
public class File {

    @AggregateIdentifier
    private FileId id;

    private DocumentId documentId;

    private String name;

    private boolean deleted = false;

    @CommandHandler
    public File(CreateFileCommand command) {
        logger.debug(this.getClass().getSimpleName() + " > " + command.getClass().getSimpleName());
        apply(new FileCreatedEvent(
                command.getFileId(),
                command.getDocumentId()
        ));
    }

    @CommandHandler
    public void on(DeleteFileCommand event) {
        logger.debug(this.getClass().getSimpleName() + " > " + event.getClass().getSimpleName());
        apply(new FileDeletedEvent(
                id,
                event.getDocumentId()
        ));
    }

    @EventSourcingHandler
    public void on(FileCreatedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " > " + event.getClass().getSimpleName());
        id = event.getFileId();
        documentId = event.getDocumentId();
    }

    @EventSourcingHandler
    public void on(FileDeletedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " > " + event.getClass().getSimpleName());
        deleted = true;
    }

}
