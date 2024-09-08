package com.amzur.test.model


import com.amzur.test.domain.UserDomain

class AccountModel {
    Long id
    String bankName
    String accountNumber
    String upiPin
    BigDecimal balance = 100000
    BigDecimal transactionLimit
    UserDomain user
    Boolean isPrimary
}
