package com.hexagonaldemo.paymentapi.adapters.payment.event;

import com.hexagonaldemo.paymentapi.common.commandhandler.VoidCommandHandler;
import com.hexagonaldemo.paymentapi.payment.command.PaymentRollback;
import com.hexagonaldemo.paymentapi.payment.event.PaymentRollbackEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentRollbackEventKafkaConsumer {

    private final VoidCommandHandler<PaymentRollback> paymentRollbackVoidCommandHandler;

    @StreamListener(PaymentEventKafkaStream.PAYMENT_ROLLBACK_INPUT)
    public void consume(@Payload PaymentRollbackEvent event) {
        log.info("Payment roll back event received: {}", event);
        try {
            paymentRollbackVoidCommandHandler.handle(event.toModel());
        } catch (Exception e) {
            log.warn("Payment {} is not rollbacked", event.getId(), e);
        }
    }
}
