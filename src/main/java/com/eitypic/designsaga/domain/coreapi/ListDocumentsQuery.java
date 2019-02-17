package com.eitypic.designsaga.domain.coreapi;

import lombok.Value;

@Value
public class ListDocumentsQuery {
    Integer size;
    Integer page;
}
