package com.amzur.test.controller

import com.amzur.test.model.TransactionModel
import com.amzur.test.service.KafkaProducerClient
import com.amzur.test.service.TransactionService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.HttpResponse
import javax.inject.Inject


@Controller("/transfer")
class TransactionController {

    @Inject
    TransactionService transactionService

    @Inject
    KafkaProducerClient kafkaProducerClient

    @Post
    def transferMoney(@Body TransferRequest transferRequest) {

        String senderMobileNumber = transferRequest.senderMobileNumber
        String receiverMobileNumber = transferRequest.receiverMobileNumber
        BigDecimal amount = transferRequest.amount
        String upiPin = transferRequest.upiPin


        def transaction=transactionService.transferMoney(senderMobileNumber, receiverMobileNumber, amount, upiPin)
        String message = """{
                senderMobileNumber: "${transaction.senderMobileNumber}",
                receiverMobileNumber:" ${transaction.receiverMobileNumber}",
                amount: "${transaction.amount}",
                transactionDate:"${transaction.transactionDate}",
                transactionTime:"${transaction.transactionTime}"
        }"""
        kafkaProducerClient.sendMessage("payment-topic", message)
        return HttpResponse.ok([message: "Transferred Money Successfully"])
    }
    @Get("/history/{mobileNumber}")
    def getTransactionHistory(@PathVariable String mobileNumber) {
        return transactionService.getTransactionHistory(mobileNumber)
    }
}



class TransferRequest {
    String senderMobileNumber
    String receiverMobileNumber
    BigDecimal amount
    String upiPin
}

