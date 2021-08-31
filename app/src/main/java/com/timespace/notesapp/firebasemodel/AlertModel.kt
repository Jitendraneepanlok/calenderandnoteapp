package com.timespace.notesapp.firebasemodel

class AlertModel {
    private var alertTime: String? = null

    constructor() {}

    constructor(alertTime: String?) {
        this.alertTime = alertTime
    }

    fun getAlertTime(): String? {
        return alertTime
    }

    fun setAlertTime(alertTime: String?) {
        this.alertTime = alertTime
    }


}