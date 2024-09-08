package com.amzur.test.controller

import com.amzur.test.model.AccountModel
import com.amzur.test.model.TransactionModel
import com.amzur.test.service.AccountService
import com.amzur.test.service.TransactionService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

import javax.inject.Inject

@Controller("/account")
class AccountController {
    @Inject
    AccountService accountService


    @Post
    def createAccount(@Body AccountModel accountModel){
        return accountService.createAccount(accountModel)
    }

    @Get("/{userId}")
    def getAccountsByUserId(@PathVariable Long userId){
        return accountService.getAllAccountsByUserId(userId)
    }

    @Get("/banks")
    def getBanks(){
        return accountService.getAllBankNames()
    }

    @Get("/accountId/{id}")
    def getAccountById(Long id){
        return accountService.getAccountById(id)
    }

    @Put("/{id}")
    def updateAccountById(Long id,AccountModel updatedAccountModel){
        return accountService.updateAccountById(id,updatedAccountModel)
    }

    @Put("/updatePin/{id}")
    def updatePinById(Long id, String oldPin, String newPin){
        return accountService.updatePinById(id,oldPin,newPin)
    }

    @Get
    def getAllAccounts(){
        return accountService.getAllAccounts()
    }

    @Put("/{accountId}/primary/{userId}")
    HttpResponse<AccountModel> setPrimaryAccount(@PathVariable Long accountId, @PathVariable Long userId) {
        try {
            def account = accountService.setPrimaryAccount(accountId, userId)
            return HttpResponse.ok(account)
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest("Error: " + e.message)
        }
    }

    // Get all accounts by userId
    @Get("/user/{userId}")
    HttpResponse<List<AccountModel>> getAccountsByUser(@PathVariable Long userId) {
        try {
            def accounts = accountService.getByUserId(userId)
            return HttpResponse.ok(accounts)
        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest("Error: " + e.message)
        }
    }
}
