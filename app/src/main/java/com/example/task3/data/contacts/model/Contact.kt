package com.example.task3.data.contacts.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Contact (
    var id: Long = UUID.randomUUID().mostSignificantBits, // TODO: form Long to UUID
    val photo: String = "",
    val name: String,
    val career: String // TODO: add address
) : Parcelable {
    override fun toString(): String { // TODO: delete?
        return "Contact: id: $id, \nFull name: $name, \nCareer: $career, \nImage: $photo"
    }

}