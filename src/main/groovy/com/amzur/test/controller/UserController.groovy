package com.amzur.test.controller

import com.amzur.test.model.UserModel
import com.amzur.test.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.Status

import javax.inject.Inject

@Controller("/register")
class UserController {
    @Inject
    UserService userService

    @Post
    def saveUsers(@Body UserModel userModel){
           def user= userService.createUser(userModel)
            if(user){
                return HttpResponse.created(user)
            }
            else{
                return HttpResponse.badRequest("Failed to add user")
            }
    }

    @Get
    def getUsers(){
        def userModelList= userService.getAllUsers()
        if(userModelList){
            return HttpResponse.ok(userModelList)
        }
        else{
            return HttpResponse.notFound("Users Not Found")
        }
    }

    @Post("/login")
    def getLogin(@Body UserModel userModel){
        return userService.getLoginByMobileNumberAndPin(userModel.mobileNumber,userModel.pin)
    }

    @Put("/{id}")
    def updateUser(@PathVariable Long id, @Body UserModel userModel){
        def updatedUser=userService.updateUser(id,userModel)
        if(updatedUser){
            return HttpResponse.ok(updatedUser)
        }
        else{
            return HttpResponse.notFound("User with ${id} Not Found to Update ")
        }
    }

    @Get("/{id}")
    def getById(@PathVariable Long id){
        return userService.getUserById(id)
    }

    @Delete("/{id}")
    def deleteById(@PathVariable Long id){
        def deleteUser= userService.deleteById(id)
        if(deleteUser){
            return HttpResponse.noContent()
        }
        else{
            return HttpResponse.notFound("User Not Found with id ${id}")
        }
    }

}
