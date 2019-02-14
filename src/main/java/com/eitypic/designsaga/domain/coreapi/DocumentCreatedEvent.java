package com.eitypic.designsaga.domain.coreapi;

import lombok.Value;

@Value
public class DocumentCreatedEvent {
    DocumentId documentId;
    String name;
}
