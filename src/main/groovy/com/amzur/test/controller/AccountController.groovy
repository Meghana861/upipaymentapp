package com.amzur.test.controller

import com.amzur.test.model.AccountModel
import com.amzur.test.service.AccountService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post

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
}
