package com.example.task3.ui.fragment.contactProfile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.example.task3.R
import com.example.task3.data.contacts.model.Contact
import com.example.task3.databinding.FragmentContactProfileBinding
import com.example.task3.ui.utils.Constants.TRANSITION_NAME_CAREER
import com.example.task3.ui.utils.Constants.TRANSITION_NAME_CONTACT_NAME
import com.example.task3.ui.utils.Constants.TRANSITION_NAME_IMAGE
import com.example.task3.ui.utils.ext.setContactPhoto

class ContactProfileFragment : Fragment(R.layout.fragment_contact_profile){

    private val binding : FragmentContactProfileBinding by lazy {
        FragmentContactProfileBinding.inflate(layoutInflater)
    }
    private val args : ContactProfileFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contact : Contact = args.contact

        setAnimation()
        bindContactInfo(contact)
        setListeners()
    }

    private fun setAnimation() {
        with(binding) {
            fragmentMyProfileImageViewProfilePhoto.transitionName = TRANSITION_NAME_IMAGE
            fragmentMyProfileTextViewProfileName.transitionName = TRANSITION_NAME_CONTACT_NAME
            fragmentMyProfileTextViewCareer.transitionName = TRANSITION_NAME_CAREER
            // TODO: add anim to home address
        }

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            R.transition.transition_move
        )

        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    private fun setListeners() {
        binding.fragmentMyProfileTextViewBack.setOnClickListener { findNavController().popBackStack() } // TODO: from popBackStack() to navigateUp()
    }

    private fun bindContactInfo(contact: Contact) {
        with(binding) {
            fragmentMyProfileTextViewProfileName.text = contact.name
            fragmentMyProfileTextViewCareer.text = contact.career
            fragmentMyProfileImageViewProfilePhoto.setContactPhoto(contact.photo)
        }
    }
}