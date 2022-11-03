package com.contactsmanagerapp.modules

import android.content.ContentProviderOperation
import android.content.Intent
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
    /*
     * Prepares the batch operation for inserting a new raw contact and its data. Even if
     * the Contacts Provider does not have any data for this person, you can't add a Contact,
     * only a raw contact. The Contacts Provider will then add a Contact automatically.
     */

        // Creates a new array of ContentProviderOperation objects.
        val ops = arrayListOf<ContentProviderOperation>()

        /*
         * Creates a new raw contact with its account type (server type) and account name
         * (user's account). Remember that the display name is not stored in this row, but in a
         * StructuredName data row. No other data is required.
         */
        var op: ContentProviderOperation.Builder =
            ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
               .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)

        // Builds the operation and adds it to the array of operations
        ops.add(op.build())

        // Creates the display name for the new raw contact, as a StructuredName data row.
        /*
     * withValueBackReference sets the value of the first argument to the value of
     * the ContentProviderResult indexed by the second argument. In this particular
     * call, the raw contact ID column of the StructuredName data row is set to the
     * value of the result returned by the first operation, which is the one that
     * actually adds the raw contact row.
     */

        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)

            // Sets the data row's MIME type to StructuredName
            .withValue(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)

            // Sets the data row's display name to the name in the UI.
            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)

        // Builds the operation and adds it to the array of operations
        ops.add(op.build())

        // Inserts the specified phone number and type as a Phone data row
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
            /*
             * Sets the value of the raw contact id column to the new raw contact ID returned
             * by the first operation in the batch.
             */
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)

            // Sets the data row's MIME type to Phone
            .withValue(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)

            // Sets the phone number and type
            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber)
            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, TYPE_MOBILE)

        // Builds the operation and adds it to the array of operations
        ops.add(op.build())

        try {
            reactApplicationContext.contentResolver.applyBatch(ContactsContract.AUTHORITY, ops)
        } catch (e: Exception) {
            // Display a warning

            // Log exception
            Log.e("ERROR", "Exception encountered while inserting contact: $e")
        }
    }
}


