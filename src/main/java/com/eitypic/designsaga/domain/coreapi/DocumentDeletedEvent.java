package com.eitypic.designsaga.domain.coreapi;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

@Value
public class DocumentDeletedEvent {
    @TargetAggregateIdentifier
    DocumentId documentId;
}
