package com.amzur.test.domain

import grails.gorm.annotation.Entity

@Entity
class TransactionDomain {
        String senderMobileNumber
        BigDecimal amount
        String receiverMobileNumber
//        String upiPin
        Date transactionDate = new Date()
        Date transactionTime =new Date()

       static belongsTo = [senderAccount: AccountDomain]
    }

