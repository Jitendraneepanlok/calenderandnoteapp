package com.timespace.notesapp.firebasemodel

class Events{
    var administrated_by: String?=null
    var alert: String?=null
    var all_Day_Event: String?=null
    var end_date: String?=null
    var end_time: String?=null
    var location: String?=null
    var repeat: String?=null
    var start_date: String?=null
    var start_time: String?=null
    var subject_title: String?=null
    var tags: String?=null
    var calendarId: String?=null
    var descriptions: DescriptionEvent ?=null

    constructor() {}
    constructor(
        administrated_by: String?,
        alert: String?,
        all_Day_Event: String?,
        end_date: String?,
        end_time: String?,
        location: String?,
        repeat: String?,
        start_date: String?,
        start_time: String?,
        subject_title: String?,
        tags: String?,
        calendarId: String?,
        descriptions: DescriptionEvent?
    ) {
        this.administrated_by = administrated_by
        this.alert = alert
        this.all_Day_Event = all_Day_Event
        this.end_date = end_date
        this.end_time = end_time
        this.location = location
        this.repeat = repeat
        this.start_date = start_date
        this.start_time = start_time
        this.subject_title = subject_title
        this.tags = tags
        this.calendarId = calendarId
        this.descriptions = descriptions
    }


}