package com.amzur.test.domain

import grails.gorm.annotation.Entity

@Entity
class TransactionDomain {
        Long id
        BigDecimal amount
        String receiver
        Date transactionDate = new Date()
        Date transactionTime =new Date()

        static belongsTo = [account: AccountDomain]
    }

