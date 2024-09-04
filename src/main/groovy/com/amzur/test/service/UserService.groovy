package com.amzur.test.service

import com.amzur.test.domain.UserDomain
import com.amzur.test.model.UserModel
import grails.gorm.transactions.Transactional

import javax.inject.Singleton

@Singleton
class UserService {
    @Transactional
    def createUser(UserModel userModel){
        UserDomain userDomain=new UserDomain()
        userDomain.firstName = userModel.firstName
        userDomain.lastName = userModel.lastName
        userDomain.email = userModel.email
        userDomain.mobileNumber = userModel.mobileNumber
        userDomain.pin = userModel.pin
        userDomain.save()
        return userDomain
    }


    @Transactional
    def getAllUsers() {
        List<UserDomain> userDomain = UserDomain.findAll()
        return userDomain
    }


    @Transactional
    def getLoginByMobileNumberAndPin(String mobileNumber,String pin){
        UserDomain userDomain = UserDomain.findByMobileNumberAndPin(mobileNumber, pin)
        if(userDomain){
            return "Login Successfully"
        }
        else{
            return  "Invalid Credentials"
        }
    }


    @Transactional
    def updateUser(Long id,UserModel updatedUserModel){
        UserDomain userDomain=UserDomain.findById(id)
        if(userDomain) {
            userDomain.firstName = updatedUserModel.firstName
            userDomain.lastName = updatedUserModel.lastName
            userDomain.email = updatedUserModel.email
            userDomain.mobileNumber = updatedUserModel.mobileNumber
            userDomain.pin= updatedUserModel.pin
            userDomain.save()
        }
        return userDomain
    }


    @Transactional
    def getUserById(Long id){
        UserDomain userDomain=UserDomain.findById(id)
        return userDomain
    }


    @Transactional
    def deleteById(Long id){
        UserDomain userDomain=UserDomain.findById(id)
        if(userDomain){
            userDomain.delete()
            return true
        }
        else{
            return false
        }
    }

}
