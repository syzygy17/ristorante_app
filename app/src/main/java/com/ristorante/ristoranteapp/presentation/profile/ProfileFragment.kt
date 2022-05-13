package com.ristorante.ristoranteapp.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ristorante.ristoranteapp.R
import com.ristorante.ristoranteapp.databinding.FragmentProfileBinding
import com.ristorante.ristoranteapp.databinding.FragmentRegistrationBinding
import org.koin.android.ext.android.inject


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val firebaseAuth: FirebaseAuth by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setListeners()
    }

    private fun initView() = with(binding) {
        userLogin.text = "Ваша почта - ${firebaseAuth.currentUser?.email}"
    }

    private fun setListeners() = with(binding) {
        signOutTextView.setOnClickListener {
            Firebase.auth.signOut()
            findNavController().navigate(R.id.action_profileFragment_to_authFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}