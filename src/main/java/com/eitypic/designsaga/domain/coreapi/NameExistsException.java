package com.eitypic.designsaga.domain.coreapi;

public class NameExistsException extends RuntimeException {
    public NameExistsException() {
        super("The name already exists");
    }
}
