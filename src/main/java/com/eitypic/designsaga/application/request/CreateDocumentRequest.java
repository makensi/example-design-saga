package com.eitypic.designsaga.application.request;

import lombok.Value;

@Value
public class CreateDocumentRequest {
    String name;
    Integer numberOfFiles;
}
