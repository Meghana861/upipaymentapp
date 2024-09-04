package com.amzur.test.domain

import grails.gorm.annotation.Entity
import groovy.transform.ToString


@Entity
@ToString
class AccountDomain {
        Long id
        String bankName
        String accountNumber
        String upiPin
        Double balance = 100000
        Double transactionLimit

        static belongsTo = [user: UserDomain]

        static constraints = {
                bankName blank: false, nullable: false
                accountNumber blank: false, nullable: false, unique: true
                upiPin size: 4..4, nullable: false
                transactionLimit nullable: false
                balance nullable: false
        }
    }

