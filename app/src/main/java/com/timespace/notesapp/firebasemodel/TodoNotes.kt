package com.timespace.notesapp.firebasemodel

class TodoNotes {

    var time: String = ""
    var title: String = ""
    var notesData:String=""
    var notesBackground:String=""
    var notesTextColor:String=""
    var remainderTime:String=""
    var userId:String=""
    var taskId:String=""

    constructor() {}


    constructor(time:String,title: String,notesData:String,notesBackground:String,notesTextColor:String,
                remainderTime:String,userId:String,taskId:String){
        this.time=time
        this.title=title
        this.notesData=notesData
        this.notesBackground=notesBackground
        this.notesTextColor=notesTextColor
        this.remainderTime=remainderTime
        this.userId=userId
        this.taskId=taskId
    }

    

}