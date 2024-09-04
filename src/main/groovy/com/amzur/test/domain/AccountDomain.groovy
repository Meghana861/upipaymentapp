package com.amzur.test.domain

import grails.gorm.annotation.Entity
import groovy.transform.ToString


@Entity
@ToString
class AccountDomain {
        String bankName
        String accountNumber
        String upiPin
        Double balance=100000.0
        Double transactionLimit
        UserDomain user
        static belongsTo=[user:UserDomain]
       // static hasMany=[transactions:TransactionDomain]
    }

