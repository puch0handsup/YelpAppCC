package com.example.yelpappcc.utils

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.yelpappcc.presentation.viewmodel.YelpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    protected val yelpViewModel by lazy {
        ViewModelProvider(requireActivity())[YelpViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun showError(message: String, action: () -> Unit) {
        AlertDialog.Builder(requireActivity())
            .setTitle("Error Occurred")
            .setMessage(message)
            .setNegativeButton("DISMISS") {dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

}