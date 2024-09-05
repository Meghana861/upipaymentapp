package com.amzur.test.model

import com.amzur.test.domain.AccountDomain

class TransactionModel {
    String senderMobileNumber
    BigDecimal amount
    String receiverMobileNumber
    String upiPin
    Date transactionDate
    Date transactionTime

}
