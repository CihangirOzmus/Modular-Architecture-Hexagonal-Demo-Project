package com.hexagonaldemo.ticketapi.common.commandhandler;

import com.hexagonaldemo.ticketapi.ticket.command.TicketRetrieve;
import com.hexagonaldemo.ticketapi.ticket.model.Ticket;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "false", matchIfMissing = true)
public class FakeTicketRetrieveCommandHandler implements CommandHandler<List<Ticket>, TicketRetrieve> {

    private static final Long NO_TICKETS_ACCOUNT_ID = 6661L;
    private static final List<Long> FAILING_IDS = List.of(
            NO_TICKETS_ACCOUNT_ID
    );

    @Override
    public List<Ticket> handle(TicketRetrieve ticketRetrieve) {
         if (ticketRetrieve.getAccountId().equals(NO_TICKETS_ACCOUNT_ID)) {
            return List.of();
        }
        if (!FAILING_IDS.contains(ticketRetrieve.getAccountId())) {
            return List.of(Ticket.builder()
                            .id(2L)
                            .count(1)
                            .meetupId(1001L)
                            .reserveDate(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                            .price(BigDecimal.valueOf(100.00))
                            .accountId(ticketRetrieve.getAccountId())
                            .build(),
                    Ticket.builder()
                            .id(2L)
                            .count(1)
                            .meetupId(1002L)
                            .reserveDate(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                            .price(BigDecimal.valueOf(110.00))
                            .accountId(ticketRetrieve.getAccountId())
                            .build(),
                    Ticket.builder()
                            .id(3L)
                            .count(1)
                            .meetupId(1003L)
                            .reserveDate(LocalDateTime.of(2021, 1, 1, 19, 0, 0))
                            .price(BigDecimal.valueOf(120.00))
                            .accountId(ticketRetrieve.getAccountId())
                            .build());
        }
        throw new RuntimeException("uncovered test scenario occurred");
    }
}
