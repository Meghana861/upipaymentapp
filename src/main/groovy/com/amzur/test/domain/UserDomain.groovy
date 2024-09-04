package com.amzur.test.domain

import grails.gorm.annotation.Entity
@Entity
class UserDomain {
    String firstName
    String lastName
    String email
    String mobileNumber
    String pin

    static hasMany = [accounts: AccountDomain] //remove this and try at last

    static constraints = {
        firstName nullable: true
        lastName nullable: true
        email email: true, nullable: true
        mobileNumber unique: true, nullable: true
        pin nullable: true
    }
}

