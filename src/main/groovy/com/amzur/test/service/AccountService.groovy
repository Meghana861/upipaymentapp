package com.amzur.test.service
import com.amzur.test.domain.UserDomain
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

        def accountModels = new AccountModel(
                    id: accountDomain.id,
                    bankName: accountDomain.bankName,
                    accountNumber: accountDomain.accountNumber,
                    upiPin: accountDomain.upiPin,
                    balance: accountDomain.balance,
                    transactionLimit: accountDomain.transactionLimit,
                    //user: accountDomain.user
            )
        return accountModels
        }




    @Transactional
    def getAllAccountsByUserId(Long userId) {
        def user = UserDomain.get(userId)
        if (user) {
            def userAccounts = AccountDomain.findAllByUser(user)

            // Convert each AccountDomain instance to AccountModel
            def accountModels = userAccounts.collect { accountDomain ->
                new AccountModel(
                        id: accountDomain.id,
                        bankName: accountDomain.bankName,
                        accountNumber: accountDomain.accountNumber,
                        upiPin: accountDomain.upiPin,
                        balance: accountDomain.balance,
                        transactionLimit: accountDomain.transactionLimit,
                        //user: accountDomain.user
                )
            }

            return accountModels
        }
    }
}