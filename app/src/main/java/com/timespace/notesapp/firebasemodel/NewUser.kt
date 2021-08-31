package com.timespace.notesapp.firebasemodel

class NewUser {
    var number: String? = null
    var name: String? = null
    var district: String? = null
    var town: String? = null
    var status: String? = null

    constructor() {}
    constructor(number: String?, name: String?, district: String?, town: String?, status: String?) {
        this.number = number
        this.name = name
        this.district = district
        this.town = town
        this.status = status
    }
}