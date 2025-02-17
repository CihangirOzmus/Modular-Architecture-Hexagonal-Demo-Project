package com.hexagonaldemo.paymentapi.common.commandhandler;

import com.hexagonaldemo.paymentapi.balance.command.BalanceRetrieve;
import com.hexagonaldemo.paymentapi.balance.model.Balance;
import com.hexagonaldemo.paymentapi.common.exception.PaymentApiBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@ConditionalOnProperty(name = "commandhandler.enabled", havingValue = "false", matchIfMissing = true)
public class FakeBalanceRetrieveCommandHandler implements CommandHandler<Balance, BalanceRetrieve> {

    private static final Long BALANCE_NOT_FOUND_ACCOUNT_ID = 6661L;
    private static final List<Long> FAILING_IDS = List.of(
            BALANCE_NOT_FOUND_ACCOUNT_ID
    );

    @Override
    public Balance handle(BalanceRetrieve balanceRetrieve) {
        failedBalanceRetrieveScenario(balanceRetrieve);
        return succeededBalanceRetrieveScenario(balanceRetrieve);
    }

    private void failedBalanceRetrieveScenario(BalanceRetrieve balanceRetrieve) {
        if (balanceRetrieve.getAccountId().equals(BALANCE_NOT_FOUND_ACCOUNT_ID)) {
            throw new PaymentApiBusinessException("paymentapi.balance.notFound");
        }
    }

    private Balance succeededBalanceRetrieveScenario(BalanceRetrieve balanceRetrieve) {
        if (!FAILING_IDS.contains(balanceRetrieve.getAccountId())) {
            return Balance.builder()
                    .id(1L)
                    .accountId(balanceRetrieve.getAccountId())
                    .amount(BigDecimal.valueOf(10.0))
                    .build();
        }
        throw new RuntimeException("uncovered test scenario occurred");
    }

}