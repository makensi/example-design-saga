package com.eitypic.designsaga.domain;

import com.eitypic.designsaga.domain.coreapi.CreateDocumentCommand;
import com.eitypic.designsaga.domain.coreapi.DocumentCreatedEvent;
import com.eitypic.designsaga.domain.coreapi.DocumentId;
import com.eitypic.designsaga.domain.coreapi.FileId;
import lombok.Data;
import org.axonframework.commandhandling.model.AggregateLifecycle;
import org.axonframework.commandhandling.model.EntityId;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.List;

@Aggregate
@Data
public class Document {

    @EntityId
    private DocumentId documentId;

    private String name;

    private List<FileId> fileIds;

    public Document(CreateDocumentCommand command) {
        AggregateLifecycle.apply(new DocumentCreatedEvent(
                DocumentId.generate(),
                command.getName()
        ));
    }

    @EventSourcingHandler
    public void on(DocumentCreatedEvent event){
        this.documentId = event.getDocumentId();
        this.name = event.getName();
    }
}
