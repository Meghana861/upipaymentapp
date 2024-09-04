package com.amzur.test.domain

import grails.gorm.annotation.Entity

@Entity
class TransactionDomain {
        AccountDomain senderAccount
        String receiverMobileNumber
        Double amount
        Date transactionDate
        Date transactionTime

        static belongsTo = [senderAccount: AccountDomain]
        def beforeInsert() {
        transactionDate = new Date()
        transactionTime = new Date()
    }
    }
