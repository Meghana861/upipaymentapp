package com.amzur.test.domain

class BankDomain {
    Long id
    String name

    static hasMany = [accounts: AccountDomain]

    static constraints = {
        name blank: false, nullable: false, unique: true
    }
}
