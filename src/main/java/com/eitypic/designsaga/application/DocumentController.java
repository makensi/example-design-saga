package com.eitypic.designsaga.application;

import com.eitypic.designsaga.application.request.CreateDocumentRequest;
import com.eitypic.designsaga.domain.coreapi.CreateDocumentCommand;
import com.eitypic.designsaga.domain.coreapi.DocumentId;
import com.eitypic.designsaga.domain.coreapi.ListDocumentsQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static org.axonframework.queryhandling.responsetypes.ResponseTypes.multipleInstancesOf;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(
        value = "/documents",
        consumes = APPLICATION_JSON_UTF8_VALUE,
        produces = APPLICATION_JSON_UTF8_VALUE)
public class DocumentController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @PostMapping
    @ResponseStatus(CREATED)
    public String create(@NotNull @RequestBody CreateDocumentRequest request) {
        logger.debug(this.getClass().getSimpleName() + " - " + request.getClass().getSimpleName());

        DocumentId documentId = DocumentId.generate();
        commandGateway.sendAndWait(new CreateDocumentCommand(
                documentId,
                request.getName()));

        return documentId.toString();
    }

    @GetMapping
    public List<DocumentId> list() {
        List<DocumentId> documentIds = queryGateway.query(
                new ListDocumentsQuery(1000, 1),
                multipleInstancesOf(DocumentId.class)
        ).join();
        return documentIds;
    }
}
