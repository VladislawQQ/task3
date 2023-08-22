package com.example.task3.ui.fragment.myContacts.adapter.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.task3.data.contacts.model.Contact

class ContactDiffUtil : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact)
        : Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact)
        : Boolean = oldItem == newItem
}