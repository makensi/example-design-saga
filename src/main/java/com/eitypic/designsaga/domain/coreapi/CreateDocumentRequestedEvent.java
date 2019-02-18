package com.eitypic.designsaga.domain.coreapi;

import lombok.Value;

@Value
public class CreateDocumentRequestedEvent {
    DocumentId documentId;
    String name;
    Integer numberOfFiles;
}
