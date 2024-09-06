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
        List<UserDomain> userDomains = UserDomain.findAll()

        if (userDomains) {

            return userDomains.collect { userDomain ->
                new UserModel(
                        id: userDomain.id,
                        firstName: userDomain.firstName,
                        lastName: userDomain.lastName,
                        email: userDomain.email,
                        mobileNumber: userDomain.mobileNumber,
                        pin: userDomain.pin
                )
            }
        } else {
            return []
        }
    }




    @Transactional
    def getLoginByMobileNumberAndPin(String mobileNumber, String pin) {
        UserDomain userDomain = UserDomain.findByMobileNumberAndPin(mobileNumber, pin)
        if (userDomain) {
            return new UserModel(
                    id: userDomain.id,
                    firstName: userDomain.firstName,
                    lastName: userDomain.lastName,
                    email: userDomain.email,
                    mobileNumber: userDomain.mobileNumber,
                    pin: userDomain.pin
            )
        } else {
            throw new IllegalArgumentException("Invalid Credentials")
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
        if (userDomain) {
            return new UserModel(
                    id: userDomain.id,
                    firstName: userDomain.firstName,
                    lastName: userDomain.lastName,
                    email: userDomain.email,
                    mobileNumber: userDomain.mobileNumber,
                    pin: userDomain.pin
            )
        }
    }


    @Transactional
    def getUserById(Long id){
        UserDomain userDomain=UserDomain.findById(id)
        if (userDomain) {
            return new UserModel(
                    id: userDomain.id,
                    firstName: userDomain.firstName,
                    lastName: userDomain.lastName,
                    email: userDomain.email,
                    mobileNumber: userDomain.mobileNumber,
                    pin: userDomain.pin
            )
        }
    }


    @Transactional
    def deleteById(Long id){
        UserDomain userDomain=UserDomain.findById(id)
        if(userDomain){
            userDomain.delete()
            return "deleted successfully"
        }
        else{
            return false
        }
    }

}
