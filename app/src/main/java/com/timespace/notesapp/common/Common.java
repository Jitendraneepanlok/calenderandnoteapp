package com.timespace.notesapp.common;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Common {


    public static String spaceType;
    public static int btn_all=0,btn_sports=1,btn_event=1,btn_music=1,btn_holiday=1;


    // firebase integrate
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

}
