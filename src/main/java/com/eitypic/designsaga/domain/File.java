package com.eitypic.designsaga.domain;

import com.eitypic.designsaga.domain.coreapi.DocumentId;
import com.eitypic.designsaga.domain.coreapi.FileId;
import lombok.Data;
import org.axonframework.commandhandling.model.EntityId;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Data
public class File {

    @EntityId
    private FileId id;

    private DocumentId documentId;

    private String name;
}
