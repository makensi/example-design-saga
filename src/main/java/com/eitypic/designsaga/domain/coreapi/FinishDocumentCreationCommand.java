package com.eitypic.designsaga.domain.coreapi;

import lombok.Value;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Value
public class FinishDocumentCreationCommand {
    @TargetAggregateIdentifier
    @NotNull
    DocumentId documentId;
    @NotNull
    @Size(min = 1, max = 4)
    List<FileId> fileIds;
}
