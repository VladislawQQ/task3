package com.example.task3.ui.fragment.myContacts

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task3.R
import com.example.task3.data.contacts.model.Contact
import com.example.task3.databinding.FragmentMyContactsBinding
import com.example.task3.ui.fragment.addContact.AddContactDialogFragment
import com.example.task3.ui.fragment.addContact.ConfirmationListener
import com.example.task3.ui.fragment.myContacts.adapter.ContactActionListener
import com.example.task3.ui.fragment.myContacts.adapter.ContactAdapter
import com.example.task3.ui.utils.Constants.TAG
import com.example.task3.ui.utils.ext.swipeToDelete
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MyContactsFragment : Fragment(R.layout.fragment_my_contacts),
    ConfirmationListener {

    private lateinit var binding: FragmentMyContactsBinding
    private val adapter = ContactAdapter(contactActionListener = object : ContactActionListener {
        override fun onContactDelete(contact: Contact) {
            viewModel.deleteContact(contact)
            showDeleteMessage()
        }

        override fun onContactClick(
            contact: Contact,
            transitionNames: Array<Pair<View, String>>
        ) {
            val extras = FragmentNavigatorExtras(*transitionNames)

            val direction: NavDirections = MyContactsFragmentDirections
                .actionMyContactsFragmentToContactProfileFragment(contact)

            findNavController().navigate(direction, extras)
        }
    })

    private val viewModel: MyContactsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyContactsBinding.bind(view)

        bindRecycleView()
        observeViewModel()
        addSwipeToDelete()
        setListeners()
    }

    private fun setListeners() {
        binding.fragmentMyContactTextViewAddContact.setOnClickListener { startDialogAddContact() }
        binding.fragmentMyContactsImageViewBack.setOnClickListener { imageViewBackListener() }
        binding.fragmentMyContactImageViewSearch.setOnClickListener { } // TODO: search in contacts
    }

    private fun imageViewBackListener() {
        findNavController().popBackStack()
    }

    private fun startDialogAddContact() {
        val addContactDialogFragment = AddContactDialogFragment()
        addContactDialogFragment.show(childFragmentManager, TAG)
    }

    private fun bindRecycleView() { // TODO: change from lateinit

        val recyclerLayoutManager = LinearLayoutManager(activity)
        with(binding) {
            fragmentMyContactRecyclerViewContacts.layoutManager = recyclerLayoutManager
            fragmentMyContactRecyclerViewContacts.adapter = adapter
        }
    }

    private fun observeViewModel() {
        postponeEnterTransition()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contacts.collect {
                    adapter.submitList(it)
                    startPostponedEnterTransition() // TODO: remove
                }
            }
        }
    }

    private fun addSwipeToDelete() {
        binding.fragmentMyContactRecyclerViewContacts.swipeToDelete { index ->
            viewModel.deleteContact(index)
            showDeleteMessage()
        }

    }

    private fun showDeleteMessage() {
        Snackbar.make(binding.root, R.string.message_delete, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.snackbar_action).uppercase()) {
                viewModel.restoreContact()
            }.setActionTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.contacts_activity_class_snackbar_action_color
                )
            )
            .setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.contacts_activity_class_snackbar_text_color
                )
            )
            .show()
    }

    override fun onSaveButtonClicked(contact: Contact) {
        viewModel.addContact(0, contact)
        Snackbar.make(binding.root, R.string.message_add_contact, Snackbar.LENGTH_SHORT)
            .setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.contacts_activity_class_snackbar_text_color
                )
            ).show()
    }
}