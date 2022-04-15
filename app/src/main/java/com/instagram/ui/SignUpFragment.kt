package com.instagram.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.navigation.fragment.findNavController
import com.instagram.LoginActivity
import com.instagram.R
import com.instagram.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {
    private lateinit var signUpBinding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        signUpBinding = FragmentSignUpBinding.inflate(layoutInflater)
        return signUpBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpBinding.buttonLogin.setOnClickListener {
            activity?.finish()
        }

        signUpBinding.buttonSignUp.setOnClickListener {
            findNavController().navigateUp()
            findNavController().navigate(R.id.sign_up_detail_fragment)
        }
    }



}