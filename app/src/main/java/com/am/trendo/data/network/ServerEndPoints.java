package com.am.trendo.data.network;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ServerEndPoints {
    public static DatabaseReference salesReference = FirebaseDatabase.getInstance().getReference().child("sales");
}
