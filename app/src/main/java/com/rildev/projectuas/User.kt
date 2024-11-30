package com.rildev.projectuas


data class User (
    var idmember:Int,
    var fname:String,
    var lname:String,
    var username:String,
    var password:String,
    var profile:String="member") //selalu member tdk ada role admin