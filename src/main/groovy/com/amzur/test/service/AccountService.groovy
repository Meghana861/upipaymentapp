package com.amzur.test.service

import com.amzur.test.domain.AccountDomain
import com.amzur.test.model.AccountModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton


@Singleton
class AccountService {

    @Transactional
    def createAccount(AccountModel accountModel) {
        //Exceptions Later
        def accountDomain = new AccountDomain(
                bankName: accountModel.bankName,
                accountNumber: accountModel.accountNumber,
                upiPin: accountModel.upiPin,
                balance: accountModel.balance,
                transactionLimit: accountModel.transactionLimit,
                user: accountModel.user
        )
        accountDomain.save()

        return accountDomain
    }

    @Transactional
    def getAllAccountsByUserId(Long id){
        def accounts = AccountDomain.findAllByUserId(id)
        return accounts
    }
}
