package com.hungerstation.zendeskservice.services;

import com.hungerstation.zendeskservice.domain.ZendeskTicket;
import com.hungerstation.zendeskservice.repository.IZendeskTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class OperationsService {

    @Autowired
    private IZendeskTicketRepository iZendeskTicketRepository;


    public ZendeskTicket create(ZendeskTicket zendeskTicket) {
        ZendeskTicket savedZendeskTicket;
        savedZendeskTicket = iZendeskTicketRepository.save(zendeskTicket);

        if (savedZendeskTicket != null) {

            //TODO: call ticket creator worker.

        }

        return savedZendeskTicket;
    }

    public ZendeskTicket update(ZendeskTicket newZendeskTicket) {
        ZendeskTicket zendeskTicket = null;
        if (fetch(newZendeskTicket.getId()).isPresent()) {
            zendeskTicket = iZendeskTicketRepository.save(newZendeskTicket);
        }
        return zendeskTicket;
    }

    public Optional<ZendeskTicket> fetch(long id) {
        return iZendeskTicketRepository.findById(id);
    }


}
