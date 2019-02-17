package com.eitypic.designsaga.domain.coreapi;

import lombok.NonNull;
import lombok.Value;

import javax.validation.constraints.NotBlank;

@Value
public class CreateDocumentCommand {
    @NonNull
    DocumentId id;
    @NotBlank
    String name;
}
