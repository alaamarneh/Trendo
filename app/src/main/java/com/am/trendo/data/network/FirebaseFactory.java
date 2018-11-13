package com.am.trendo.data.network;

public class FirebaseFactory {
    public static FirebaseDB getFirebaseDB(){
        return new AppFirebaseDB();
    }
}
