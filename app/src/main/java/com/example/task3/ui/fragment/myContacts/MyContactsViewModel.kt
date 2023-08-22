package com.example.task3.ui.fragment.myContacts

import androidx.lifecycle.ViewModel
import com.example.task3.data.contacts.ContactService
import com.example.task3.data.contacts.model.Contact
import kotlinx.coroutines.flow.StateFlow

class MyContactsViewModel : ViewModel() {

    private val contactService = ContactService()
    
    val contacts : StateFlow<List<Contact>> = contactService.contacts

    // fun getContact(index: Int): Contact = contacts.value[index]// TODO: remove

    // fun getContactIndex(contact: Contact) : Int = contactService.getContactIndex(contact)  // TODO: remove

    fun deleteContact(index: Int) = contactService.deleteContact(index)
    fun deleteContact(contact: Contact) = contactService.deleteContact(contacts.value.indexOf(contact))
    fun restoreContact() = contactService.restoreContact()

    fun addContact(index: Int, contact: Contact) { // TODO: change
        if(index == 0) {
            contactService.addContact(contact)
        } else {
            contactService.addContact(contact, index)
        }
    }

//    fun addContact(contact: Contact) {
//        contactService.addContact(contact)
//    }
}