package com.eitypic.designsaga.infrastructure;

import com.eitypic.designsaga.domain.coreapi.DocumentCreatedEvent;
import com.eitypic.designsaga.domain.coreapi.NameExistsQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.queryhandling.QueryHandler;

import java.util.Set;

@RequiredArgsConstructor
public class DocumentRepository {

    private Set<String> names;

    @QueryHandler
    public boolean query(NameExistsQuery query) {
        return names.contains(query.getName().toLowerCase());
    }

    @EventSourcingHandler
    public void on(DocumentCreatedEvent event) {
        names.add(event.getName().toLowerCase());
    }

}
