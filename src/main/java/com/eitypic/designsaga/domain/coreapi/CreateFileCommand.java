package com.eitypic.designsaga.domain.coreapi;

import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
public class CreateFileCommand {
    @NotNull
    FileId fileId;
    @NotNull
    DocumentId documentId;
}
