package com.example.task3.data.contacts

import com.example.task3.data.contacts.model.Contact
import com.example.task3.ui.utils.ext.log
import kotlinx.coroutines.flow.MutableStateFlow

class ContactService {

    var contacts = MutableStateFlow<List<Contact>>(emptyList())
    private val contactProvider = ContactGenerator()
    private var lastDeleteContact: Contact? = null
    private var lastDeleteIndex: Int? = null
    init {
        if (contacts.value.isEmpty()) { // TODO: always true?
            val contactsPhone = MutableStateFlow<List<Contact>>(emptyList())
            try {
                contactsPhone.value = contactProvider.getPhoneContacts()
            } catch (e: Exception) {
                log("Catch! ${e.message}")
            }

            contacts =
                if (contactsPhone.value.isNotEmpty())
                    contactsPhone
                else
                    contactProvider.generateContacts()
        }
    }

    fun deleteContact(index: Int) {
        lastDeleteIndex = index
        lastDeleteContact = contacts.value[index]

        contacts.value = contacts.value.toMutableList().apply {
            remove(lastDeleteContact)
        }
    }
    fun restoreContact() {
        contacts.value = contacts.value.toMutableList().apply {
            if(lastDeleteIndex != null && lastDeleteContact != null) {
                add(lastDeleteIndex!!, lastDeleteContact!!)
                lastDeleteIndex = null
                lastDeleteContact = null
            }
        }
    }

//    fun addContact(contact: Contact) {   // TODO: if have this contact, if(true) delete
//        contacts.value = contacts.value.toMutableList().apply {
//            add(contact)
//        }
//    }

    fun addContact(contact: Contact, index: Int = contacts.value.size) {
        contacts.value = contacts.value.toMutableList().apply {
            add(index, contact)
        }
    }
    // TODO: remove
    // fun getContactIndex(contact: Contact): Int = contacts.value.indexOf(contact)

    fun getContactByIndex(index: Int): Contact {    // TODO: remove
        return contacts.value[index]
    }
}