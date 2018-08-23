package com.hungerstation.zendeskservice.repository;

import com.hungerstation.zendeskservice.domain.ZendeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IZendeskTicketRepository extends JpaRepository<ZendeskTicket, Long> {
}
