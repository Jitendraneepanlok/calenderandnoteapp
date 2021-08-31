package com.timespace.notesapp.firebasemodel

class MySpace {
    private var title: String? = null
    private  var start_date:String? = null
    private  var end_date:String? = null
    private  var start_time:String? = null
    private  var end_time:String? = null
    private  var repeat:String? = null
    private  var details:String? = null
    private var all_day: Boolean? = null
    private var alerts: List<AlertModel>? = null
    private var tags: List<AlertModel>? = null

    constructor() {}

    constructor(
        title: String?,
        start_date: String?,
        end_date: String?,
        start_time: String,
        end_time: String,
        repeat: String,
        details: String,
        all_day: Boolean?,
        alerts: List<AlertModel>?,
        tags: List<AlertModel>?
    ) {
        this.title = title
        this.start_date = start_date
        this.end_date = end_date
        this.start_time = start_time
        this.end_time = end_time
        this.repeat = repeat
        this.details = details
        this.all_day = all_day
        this.alerts = alerts
        this.tags = tags
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getStart_date(): String? {
        return start_date
    }

    fun setStart_date(start_date: String) {
        this.start_date = start_date
    }

    fun getEnd_date(): String? {
        return end_date
    }

    fun setEnd_date(end_date: String) {
        this.end_date = end_date
    }

    fun getStart_time(): String? {
        return start_time
    }

    fun setStart_time(start_time: String) {
        this.start_time = start_time
    }

    fun getEnd_time(): String? {
        return end_time
    }

    fun setEnd_time(end_time: String) {
        this.end_time = end_time
    }

    fun getRepeat(): String? {
        return repeat
    }

    fun setRepeat(repeat: String) {
        this.repeat = repeat
    }

    fun getDetails(): String? {
        return details
    }

    fun setDetails(details: String) {
        this.details = details
    }

    fun getAll_day(): Boolean? {
        return all_day
    }

    fun setAll_day(all_day: Boolean?) {
        this.all_day = all_day
    }

    fun getAlerts(): List<AlertModel>? {
        return alerts
    }

    fun setAlerts(alerts: List<AlertModel>?) {
        this.alerts = alerts
    }

    fun getTags(): List<AlertModel>? {
        return tags
    }

    fun setTags(tags: List<AlertModel>?) {
        this.tags = tags
    }
}