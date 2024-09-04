package com.amzur.test.model


import com.amzur.test.domain.UserDomain

class AccountModel {
    String bankName
    String accountNumber
    String upiPin
    Double balance=100000.0
    Double transactionLimit
    UserDomain user
}
