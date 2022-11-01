package com.contactsmanagerapp.modules

import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class ContactsModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName() = "ContactsModule"

    @ReactMethod
    fun createContact(name: String, number: String) {
        Log.d("ContactModule", "Create contact with name: $name and number: $number")

    }


}


