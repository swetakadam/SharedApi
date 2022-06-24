package com.example.sharedapi

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}