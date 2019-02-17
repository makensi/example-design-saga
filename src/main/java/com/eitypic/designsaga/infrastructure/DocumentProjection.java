package com.eitypic.designsaga.infrastructure;

import com.eitypic.designsaga.domain.coreapi.DocumentCreatedEvent;
import com.eitypic.designsaga.domain.coreapi.DocumentId;
import com.eitypic.designsaga.domain.coreapi.ListDocumentsQuery;
import com.eitypic.designsaga.domain.coreapi.NameExistsQuery;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@NoArgsConstructor
public class DocumentProjection {

    private Map<DocumentId, String> documents = new HashMap<>();

    @QueryHandler
    public boolean query(NameExistsQuery query) {
        return documents.values().contains(query.getName().toLowerCase());
    }

    @QueryHandler
    public Set<DocumentId> query(ListDocumentsQuery query) {
        return documents.keySet();
    }

    @EventSourcingHandler
    public void on(DocumentCreatedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " - " + event.getClass().getSimpleName());
        documents.put(event.getDocumentId(), event.getName().toLowerCase());
    }

}
