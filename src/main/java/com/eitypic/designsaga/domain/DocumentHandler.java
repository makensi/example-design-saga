package com.eitypic.designsaga.domain;

import com.eitypic.designsaga.domain.coreapi.CreateDocumentCommand;
import com.eitypic.designsaga.domain.coreapi.NameExistsException;
import com.eitypic.designsaga.domain.coreapi.NameExistsQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentHandler {

    private final QueryGateway queryGateway;
    private final Repository<Document> documentAggregateRepository;

    @CommandHandler
    public void handle(CreateDocumentCommand command) throws Exception {
        logger.debug(this.getClass().getSimpleName() + " - " + command.getClass().getSimpleName());
        if (documentExists(command)) {
            throw new NameExistsException();
        }
        documentAggregateRepository.newInstance(() -> new Document(command));
    }

    private boolean documentExists(CreateDocumentCommand command) {
        return queryGateway.query(new NameExistsQuery(command.getName()), Boolean.TYPE).join();
    }
}
