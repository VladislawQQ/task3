package com.example.task3.ui.fragment.addContact

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.task3.data.contacts.ContactGenerator
import com.example.task3.data.contacts.model.Contact
import com.example.task3.databinding.DialogAddContactBinding
import com.example.task3.ui.utils.ext.setContactPhoto

class AddContactDialogFragment : DialogFragment() {

    private val listener: ConfirmationListener by lazy {
        try {
            requireParentFragment() as ConfirmationListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context or ${requireParentFragment()} must implement ConfirmationListener")
        }
    }
    private var _binding: DialogAddContactBinding? = null
    private val binding get() = _binding!!
    private val _contactGenerator = ContactGenerator() // TODO: remove

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        _binding = DialogAddContactBinding.inflate(layoutInflater)
        setListeners()

        binding.dialogAddContactImageViewProfilePhoto.setContactPhoto()
        builder.setView(binding.root)
        return builder.create()
    }

    private fun setListeners() {
        binding.dialogAddContactImageViewBack.setOnClickListener { imageViewBackListener() }
        binding.dialogAddContactButtonSave.setOnClickListener { saveButtonListener() }
    }


    private fun saveButtonListener() {
        listener.onSaveButtonClicked(
            with(binding) {
                Contact(
                    name = dialogAddContactEditTextUsername.text.toString(),
                    career = dialogAddContactEditTextCareer.text.toString()
                )
            }
        )
        dismiss()
    }

    private fun imageViewBackListener() {
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        _binding = null
    }
}