package com.amzur.test.service
import com.amzur.test.domain.UserDomain
import com.amzur.test.domain.AccountDomain
import com.amzur.test.model.AccountModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton


@Singleton
class AccountService {

    @Transactional
    AccountDomain findPrimaryAccount(UserDomain user) {
        return AccountDomain.findByUserAndIsPrimary(user, true)
    }

    @Transactional
    def createAccount(AccountModel accountModel) {
        // Find the user by ID
        def user = UserDomain.get(accountModel.user.id)
        if (!user) {
            throw new IllegalArgumentException("User not found")
        }

        // Create AccountDomain object from AccountModel
        def accountDomain = new AccountDomain(
                bankName: accountModel.bankName,
                accountNumber: accountModel.accountNumber,
                upiPin: accountModel.upiPin,
                balance: accountModel.balance,
                transactionLimit: accountModel.transactionLimit,
                user: user
        )



        def primaryAccount = findPrimaryAccount(user)
        if (!primaryAccount) {
            // Set this account as primary if none exists
            accountDomain.isPrimary = true
        } else {
            // Set as primary based on input, default to false
            accountDomain.isPrimary = accountModel.isPrimary ?: false
        }

        // Save the account to the database
        accountDomain.save(flush: true)

        // If the newly created account is set as primary, unset other primary accounts
        if (accountDomain.isPrimary) {
            unsetOtherPrimaryAccounts(accountDomain)
        }

        // Return the created AccountModel with the saved details
        def accountModels = new AccountModel(
                id: accountDomain.id,
                bankName: accountDomain.bankName,
                accountNumber: accountDomain.accountNumber,
                upiPin: accountDomain.upiPin,
                balance: accountDomain.balance,
                transactionLimit: accountDomain.transactionLimit,
                isPrimary: accountDomain.isPrimary
        )

        return accountModels
    }

// Helper method to unset other primary accounts for a user
    private void unsetOtherPrimaryAccounts(AccountDomain newPrimaryAccount) {
        UserDomain user = newPrimaryAccount.user
        findByUser(user).findAll { it.isPrimary }.each { account ->
            if (account.id != newPrimaryAccount.id) {
                account.isPrimary = false
                account.save(flush: true)
            }
        }
        }

    @Transactional
    List<AccountDomain> findByUser(UserDomain user) {
        return AccountDomain.findAllByUser(user)
    }



//    @Transactional
//    def createAccount(AccountModel accountModel) {
//        //Put  the Exceptions Later
//        def accountDomain = new AccountDomain(
//                bankName: accountModel.bankName,
//                accountNumber: accountModel.accountNumber,
//                upiPin: accountModel.upiPin,
//                balance: accountModel.balance,
//                transactionLimit: accountModel.transactionLimit,
//                user: accountModel.user
//        )
//        accountDomain.save()
//
//        def accountModels = new AccountModel(
//                    id: accountDomain.id,
//                    bankName: accountDomain.bankName,
//                    accountNumber: accountDomain.accountNumber,
//                    upiPin: accountDomain.upiPin,
//                    balance: accountDomain.balance,
//                    transactionLimit: accountDomain.transactionLimit,
//
//            )
//        return accountModels
//        }


    @Transactional
    def getAllBankNames() {
        def bankNames = AccountDomain.executeQuery('SELECT DISTINCT a.bankName FROM AccountDomain a')
        return bankNames
    }

    @Transactional
    def getAllAccountsByUserId(Long userId) {
        def user = UserDomain.get(userId)
        if (user) {
            def userAccounts = AccountDomain.findAllByUser(user)

            def accountModels = userAccounts.collect { accountDomain ->
                new AccountModel(
                        id: accountDomain.id,
                        bankName: accountDomain.bankName,
                        accountNumber: accountDomain.accountNumber,
                        upiPin: accountDomain.upiPin,
                        balance: accountDomain.balance,
                        transactionLimit: accountDomain.transactionLimit,

                )
            }

            return accountModels
        }
    }

    @Transactional
    def getAccountById(Long id) {
        def accountDomain = AccountDomain.findById(id)
        if (accountDomain) {
            return new AccountModel(
                    id: accountDomain.id,
                    bankName: accountDomain.bankName,
                    accountNumber: accountDomain.accountNumber,
                    upiPin: accountDomain.upiPin,
                    balance: accountDomain.balance,
                    transactionLimit: accountDomain.transactionLimit

            )
        }
    }

    @Transactional
    def updateAccountById(Long id, AccountModel updatedAccountModel) {
        def accountDomain = AccountDomain.findById(id)
        if (accountDomain) {

            accountDomain.bankName = updatedAccountModel.bankName
            accountDomain.accountNumber = updatedAccountModel.accountNumber
            accountDomain.upiPin = updatedAccountModel.upiPin
            accountDomain.balance = updatedAccountModel.balance
            accountDomain.transactionLimit = updatedAccountModel.transactionLimit


            accountDomain.save(flush: true)


            return new AccountModel(
                    id: accountDomain.id,
                    bankName: accountDomain.bankName,
                    accountNumber: accountDomain.accountNumber,
                    upiPin: accountDomain.upiPin,
                    balance: accountDomain.balance,
                    transactionLimit: accountDomain.transactionLimit
            )
        }
    }

    @Transactional
    def updatePinById(Long id, String oldPin, String newPin) {
        def accountDomain = AccountDomain.findById(id)
        if (accountDomain) {
            if (accountDomain.upiPin == oldPin) {

                accountDomain.upiPin = newPin
                accountDomain.save(flush: true)


                return "UPI PIN updated successfully."
            }
        }

}
    @Transactional
    def getAllAccounts(){
        def accountDomains = AccountDomain.findAll()
        if (accountDomains) {
            return accountDomains.collect { accountDomain ->
                new AccountModel(
                        id: accountDomain.id,
                        bankName: accountDomain.bankName,
                        accountNumber: accountDomain.accountNumber,
                        upiPin: accountDomain.upiPin,
                        balance: accountDomain.balance,
                        transactionLimit: accountDomain.transactionLimit
                )
            }
        }

    }
    }
