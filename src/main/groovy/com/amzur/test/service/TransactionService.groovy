package com.amzur.test.service

import com.amzur.test.domain.AccountDomain
import com.amzur.test.domain.TransactionDomain
import com.amzur.test.domain.UserDomain
import com.amzur.test.model.AccountModel
import com.amzur.test.model.TransactionModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton
import java.text.SimpleDateFormat

@Singleton
class TransactionService {

    @Transactional
    def transferMoney(String senderMobileNumber, String receiverMobileNumber, BigDecimal amount, String upiPin) {
        UserDomain sender = UserDomain.findByMobileNumber(senderMobileNumber)
        UserDomain receiver = UserDomain.findByMobileNumber(receiverMobileNumber)

        if (!sender || !receiver) {
            return "Sender or Receiver Not Found"
        }

        AccountDomain senderAccount = AccountDomain.findByUserAndUpiPin(sender, upiPin)
        if (!senderAccount) {
            return "Invalid UPI Pin"
        }

        AccountDomain receiverAccount = AccountDomain.findByUser(receiver)
        if (!receiverAccount) {
            return "Receiver Not Found"
        }

        if (senderAccount.balance < amount) {
            return "Insufficient Balance"
        }

        def transaction
        try {
            // Update sender's balance
            senderAccount.balance -= amount
            senderAccount.save(flush: true)

            // Update receiver's balance
            receiverAccount.balance += amount
            receiverAccount.save(flush: true)

            // Create a new transaction record
            transaction = new TransactionDomain(
                    senderMobileNumber: senderMobileNumber,
                    receiverMobileNumber: receiverMobileNumber,
                    amount: amount,
                    upiPin: upiPin,
                    transactionDate: new Date(),
                    transactionTime: new Date(),
                    senderAccount: senderAccount
            )
            transaction.save(flush: true)
        } catch (Exception e) {
            if (transaction) {
                transaction.status = 'Failed'
                transaction.save(flush: true)
            }
            throw e
        }

        return new TransactionModel(
                senderMobileNumber: transaction.senderMobileNumber,
                receiverMobileNumber: transaction.receiverMobileNumber,
                amount: transaction.amount,
                transactionDate: transaction.transactionDate,
                transactionTime: transaction.transactionTime
        )
    }


    @Transactional
    def getTransactionHistory(String mobileNumber) {

        def user = UserDomain.findByMobileNumber(mobileNumber)

        if (!user) {
            throw new IllegalArgumentException("User not found")
        }


        def transactionList = TransactionDomain.findAllBySenderMobileNumberOrReceiverMobileNumber(mobileNumber, mobileNumber)

        if (transactionList.isEmpty()) {
            return "No transactions found for this user"
        }


        def transactionHistory = transactionList.collect { transaction ->
            new TransactionModel(
                    senderMobileNumber: transaction.senderMobileNumber,
                    receiverMobileNumber: transaction.receiverMobileNumber,
                    amount: transaction.amount,
                    transactionDate: transaction.transactionDate,
                    transactionTime: transaction.transactionTime
            )
        }

        return transactionHistory
    }
}










