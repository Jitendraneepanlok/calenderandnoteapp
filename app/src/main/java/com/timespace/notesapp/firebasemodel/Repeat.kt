package com.timespace.notesapp.firebasemodel

class Repeat {
    var name: String? = null

    constructor() {}

    constructor(name: String?) {
        this.name = name
    }

    fun getPickerViewText(): String? {
        return name
    }

}