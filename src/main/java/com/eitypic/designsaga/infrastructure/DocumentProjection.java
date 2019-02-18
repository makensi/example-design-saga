package com.eitypic.designsaga.infrastructure;

import com.eitypic.designsaga.domain.coreapi.*;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@NoArgsConstructor
public class DocumentProjection {

    private List<DocumentView> documents = new ArrayList<>();

    @QueryHandler
    public boolean query(NameExistsQuery query) {
        return documents.stream().anyMatch(documentView -> documentView.getName().equals(query.getName()));
    }

    @QueryHandler
    public List<DocumentView> query(ListDocumentsQuery query) {
        return documents;
    }

    @EventSourcingHandler
    public void on(CreateDocumentRequestedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " - " + event.getClass().getSimpleName());
        documents.add(new DocumentView(
                event.getDocumentId(),
                event.getName()
        ));
    }

    @EventSourcingHandler
    public void on(FileCreatedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " - " + event.getClass().getSimpleName());
        documents.stream()
                .filter(documentView -> documentView.getDocumentId().equals(event.getDocumentId()))
                .findFirst()
                .ifPresent(documentView -> documentView.addFile(event.getFileId()));
    }

    @EventSourcingHandler
    public void on(DocumentDeletedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " - " + event.getClass().getSimpleName());
        documents.removeIf(documentView -> documentView.getDocumentId().equals(event.getDocumentId()));
    }

}
