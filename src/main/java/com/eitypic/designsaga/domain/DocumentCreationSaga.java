package com.eitypic.designsaga.domain;

import com.eitypic.designsaga.domain.coreapi.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;
import org.axonframework.messaging.interceptors.JSR303ViolationException;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.IntStream.range;
import static org.axonframework.eventhandling.saga.SagaLifecycle.associateWith;
import static org.axonframework.eventhandling.saga.SagaLifecycle.end;

// REMEMBER SAGA == PROCESS
//          SAGA == BUSINESS TRANSACTION == BASE
@Slf4j
@Data
@Saga // saga declaration
public class DocumentCreationSaga {

    @Autowired
    // also {{org.springframework.data.annotation.Transient}} must be annotated
    // transient ensure the injectable resources will not serialized
    private transient CommandGateway commandGateway;

    // the properties will be serialized with the SAGA
    // this provide a way to understand on what part of the process is the saga
    private List<FileId> pendingFileIds = new ArrayList<>();
    private List<FileId> createdFileIds = new ArrayList<>();
    private DocumentId documentId;

    // with this annotation the event handler will be one of the starting points for this SAGA
    @StartSaga
    // the event handler is always assoicated to a property of the event which makes the SAGA target
    @SagaEventHandler(associationProperty = "documentId")
    public void on(CreateDocumentRequestedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " > " + event.getClass().getSimpleName());

        // start the process setting the state values
        setDocumentId(event.getDocumentId());

        range(0, event.getNumberOfFiles())
                .forEach(value -> {
                    final FileId fileId = FileId.generate();
                    pendingFileIds.add(fileId); // add a file id to keep track of the process state
                    associateWith("fileId", fileId.toString());
                    commandGateway.send(new CreateFileCommand(
                            fileId,
                            event.getDocumentId()
                    ));
                });
    }

    // the event handler is assoicated to a different property of the event which makes the SAGA target
    // it was set with associateWith
    @SagaEventHandler(associationProperty = "fileId")
    public void on(FileCreatedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " > " + event.getClass().getSimpleName());

        // it is interesting in order to ensure that the fileId with this id is not going to be listen again
        // but in this case is unnecessary
        // SagaLifecycle.removeAssociationWith("fileId", event.getFileId().toString());

        // we remove the file form the SAGA state
        createdFileIds.add(event.getFileId());
        pendingFileIds.remove(event.getFileId());

        if (pendingFileIds.isEmpty()) {
            try {
                // everything goes well
                commandGateway.sendAndWait(new FinishDocumentCreationCommand(
                        documentId,
                        createdFileIds
                ));
            } catch (JSR303ViolationException e) {
                // otherwise compensate the action
                commandGateway.sendAndWait(new DeleteDocumentCommand(
                        documentId
                ));
            }
        }
    }

    // this annotation finish the saga in this event handler
    @EndSaga
    @SagaEventHandler(associationProperty = "documentId")
    public void on(DocumentCreatedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " > " + event.getClass().getSimpleName());
    }

    // this annotation finish the saga in this event handler
    @SagaEventHandler(associationProperty = "documentId")
    public void on(DocumentDeletedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " > " + event.getClass().getSimpleName());
        createdFileIds.forEach(fileId -> commandGateway.send(new DeleteFileCommand(
                fileId,
                documentId
        )));
    }

    // this annotation finish the saga in this event handler
    @SagaEventHandler(associationProperty = "fileId")
    public void on(FileDeletedEvent event) {
        logger.debug(this.getClass().getSimpleName() + " > " + event.getClass().getSimpleName());

        pendingFileIds.add(event.getFileId());
        createdFileIds.remove(event.getFileId());

        if (createdFileIds.isEmpty()) {
            // also SAGA could be finished in code with:
            // SagaLifecycle.end
            end();
        }
    }

}

