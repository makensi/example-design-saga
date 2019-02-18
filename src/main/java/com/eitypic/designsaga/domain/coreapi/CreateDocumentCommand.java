package com.eitypic.designsaga.domain.coreapi;

import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class CreateDocumentCommand {
    @NonNull
    DocumentId id;
    @NotBlank
    String name;
    @NotNull
    Integer numberOfFiles;
}
