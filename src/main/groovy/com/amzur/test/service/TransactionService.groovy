package com.amzur.test.service

import com.amzur.test.domain.AccountDomain
import com.amzur.test.domain.TransactionDomain
import com.amzur.test.model.AccountModel
import com.amzur.test.model.TransactionModel
import grails.gorm.transactions.Transactional

class TransactionService {
    @Transactional
    def transferMoney(AccountModel fromAccountModel, String recipient, BigDecimal amount, String upiPin) {
        // Convert AccountModel to AccountDomain
        def fromAccountDomain = AccountDomain.get(fromAccountModel.id)

        if (fromAccountDomain && fromAccountDomain.upiPin == upiPin && fromAccountDomain.balance >= amount) {
            fromAccountDomain.balance -= amount
            fromAccountDomain.save(flush: true)

            // Create and save the transaction domain object
            def transactionDomain = new TransactionDomain(
                    account: fromAccountDomain,
                    amount: amount,
                    recipient: recipient
            )
            transactionDomain.save(flush: true)

            // Convert TransactionDomain to TransactionModel
            def transactionModel = new TransactionModel(
                    id: transactionDomain.id,
                    accountId: transactionDomain.account.id,
                    amount: transactionDomain.amount,
                    recipient: transactionDomain.recipient,
                    transactionDate: transactionDomain.transactionDate // Assuming this field exists
            )

            return transactionModel
        }
    }
}