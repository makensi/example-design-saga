package com.eitypic.designsaga.domain.coreapi;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.List;

@Value
public class DocumentCreatedEvent {
    @TargetAggregateIdentifier
    DocumentId documentId;
    List<FileId> fileIds;
}
