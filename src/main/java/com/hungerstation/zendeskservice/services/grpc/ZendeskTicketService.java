package com.hungerstation.zendeskservice.services.grpc;

import com.google.protobuf.util.Timestamps;
import com.hungerstation.zendeskservice.domain.ZendeskTicket;
import com.hungerstation.zendeskservice.services.OperationsService;
import io.grpc.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ZendeskTicketService extends com.hungerstation.zendesk_ticket.grpc.services.ZendeskTicketServiceGrpc.ZendeskTicketServiceImplBase {

    @Autowired
    private OperationsService operationsService;

    private Logger logger = LoggerFactory.getLogger(ZendeskTicketService.class);

    @Override
    public void create(com.hungerstation.zendesk_ticket.grpc.services.ZendeskTicket request,
                       io.grpc.stub.StreamObserver<com.hungerstation.zendesk_ticket.grpc.services.ZendeskTicket> responseObserver) {
        logger.debug("Request " + request);

        if (request.getRelatedToId() <= 0) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription(String.format("related_to_id must be greater than zero"))
                    .asRuntimeException());
            return;
        }

        if (request.getRelatedToType() == null || request.getRelatedToType().isEmpty()) {
            responseObserver.onError(Status.INVALID_ARGUMENT
                    .withDescription(String.format("related_to_type must be specified"))
                    .asRuntimeException());
            return;
        }

        ZendeskTicket zendeskTicket = new ZendeskTicket();
        zendeskTicket.setRelatedToId(request.getRelatedToId());
        zendeskTicket.setRelatedToType(request.getRelatedToType());

        ZendeskTicket savedZendeskTicket;
        savedZendeskTicket = operationsService.create(zendeskTicket);

        if (savedZendeskTicket == null) {
            responseObserver.onError(Status.ABORTED
                    .withDescription(String.format("error while saving the issue in DB"))
                    .asRuntimeException());
            return;
        }

        com.hungerstation.zendesk_ticket.grpc.services.ZendeskTicket zendeskTicketResponse = createDTO(savedZendeskTicket);

        logger.debug("Response " + zendeskTicketResponse);

        responseObserver.onNext(zendeskTicketResponse);
        responseObserver.onCompleted();
    }

    private com.hungerstation.zendesk_ticket.grpc.services.ZendeskTicket createDTO(ZendeskTicket zendeskTicket) {
        try {
            return com.hungerstation.zendesk_ticket.grpc.services.ZendeskTicket.newBuilder()
                    .setZendeskReferenceId(zendeskTicket.getZendeskReferenceId())
                    .setRelatedToType(zendeskTicket.getRelatedToType())
                    .setRelatedToId(zendeskTicket.getRelatedToId())
                    .setCreatedAt(Timestamps.parse(new SimpleDateFormat("yyyy-MM-dd'T'h:m:ssZZZZZ").format(zendeskTicket.getCreatedAt())))
                    .build();
        } catch (ParseException e) {
            return com.hungerstation.zendesk_ticket.grpc.services.ZendeskTicket.newBuilder()
                    .setZendeskReferenceId(zendeskTicket.getZendeskReferenceId())
                    .setRelatedToType(zendeskTicket.getRelatedToType())
                    .setRelatedToId(zendeskTicket.getRelatedToId())
                    .build();
        }
    }
}
