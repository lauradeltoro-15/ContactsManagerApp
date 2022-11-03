package com.contactsmanagerapp.modules

import android.content.ContentProviderOperation
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
import android.util.Log
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class ContactsModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName() = "ContactsModule"

    @ReactMethod
    fun createContact(name: String, phoneNumber: String) {
        val contentProviderOperations = arrayListOf<ContentProviderOperation>()

        contentProviderOperations.add(getCreateRawContentOperation().build())
        contentProviderOperations.add(getDisplayNameOperation(name).build())
        contentProviderOperations.add(getPhoneNumberOperation(phoneNumber).build())

        commitContact(contentProviderOperations)
    }

    // Inserts the specified phone number and type as a Phone data row
    private fun getPhoneNumberOperation(phoneNumber: String) =
        ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, TYPE_MOBILE)

    // Creates the display name for the new raw contact, as a StructuredName data row.
    private fun getDisplayNameOperation(name: String): ContentProviderOperation.Builder {
        return ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
    }

    // Creates a new raw contact with its account type and account name
    private fun getCreateRawContentOperation(): ContentProviderOperation.Builder {
        return ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
    }

    private fun commitContact(contentProviderOperations: ArrayList<ContentProviderOperation>) {
        try {
            reactApplicationContext.contentResolver.applyBatch(
                ContactsContract.AUTHORITY,
                contentProviderOperations
            )
        } catch (e: Exception) {
            Log.e("ERROR", "Exception encountered while inserting contact: $e")
        }
    }
}


