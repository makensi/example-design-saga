package com.eitypic.designsaga.application;

import com.eitypic.designsaga.application.request.CreateDocumentRequest;
import com.eitypic.designsaga.domain.coreapi.CreateDocumentCommand;
import com.eitypic.designsaga.domain.coreapi.DocumentId;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class DocumentController {

    private final CommandGateway commandGateway;

    @PostMapping
    public DocumentId create(@RequestBody CreateDocumentRequest request) {
        return commandGateway.sendAndWait(new CreateDocumentCommand(request.getName()));
    }
}
