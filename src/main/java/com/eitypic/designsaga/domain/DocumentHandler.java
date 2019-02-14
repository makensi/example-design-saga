package com.eitypic.designsaga.domain;

import com.eitypic.designsaga.domain.coreapi.CreateDocumentCommand;
import com.eitypic.designsaga.domain.coreapi.NameExistsException;
import com.eitypic.designsaga.domain.coreapi.NameExistsQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.queryhandling.QueryGateway;

@RequiredArgsConstructor
public class DocumentHandler {

    private final QueryGateway queryGateway;
    private final Repository<Document> documentAggregateRepository;

    @CommandHandler
    public void handle(CreateDocumentCommand command) throws Exception {
        if (queryGateway.query(new NameExistsQuery(command.getName()), Boolean.TYPE).join()) {
            throw new NameExistsException();
        }
        documentAggregateRepository.newInstance(() -> new Document(command));
    }
}
