package com.timespace.notesapp.firebasemodel

class DailyEvents {
    var time: String? = null
    var title: String? = null

    constructor() {}
    constructor(time: String?, title: String?) {
        this.time = time
        this.title = title
    }
}