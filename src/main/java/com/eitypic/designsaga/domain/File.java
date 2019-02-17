package com.eitypic.designsaga.domain;

import com.eitypic.designsaga.domain.coreapi.DocumentId;
import com.eitypic.designsaga.domain.coreapi.FileId;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Slf4j
@Aggregate
@NoArgsConstructor
public class File {

    @AggregateIdentifier
    private FileId id;

    private DocumentId documentId;

    private String name;
}
