package com.aokur.bitcointicker.ui.auth.register

import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aokur.bitcointicker.R
import com.aokur.bitcointicker.databinding.FragmentRegisterBinding
import com.aokur.bitcointicker.ui.auth.EntryType
import com.aokur.bitcointicker.ui.base.BaseFragment
import com.aokur.bitcointicker.util.CustomTextWatcher
import com.aokur.bitcointicker.util.EntryState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: BaseFragment<FragmentRegisterBinding>() {

    override val TAG: String
        get() = RegisterFragment::class.java.simpleName

    private val viewModel: RegisterViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_register
    }

    override fun init(savedInstanceState: Bundle?) {
        viewModel.setEntryType(EntryType.REGISTER)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.registerViewModel = viewModel

        binding.edtEmail.afterTextChanged {
            viewModel.isDataChanged(
                EntryState.EMAIL,
                binding.edtEmail.text.toString()
            )
        }

        viewModel.entryFormState.observe(viewLifecycleOwner, {
            binding.btnRegister.isEnabled = it.isDataValid

            if (it.emailError.isNullOrEmpty().not())
                binding.edtEmail.error = it.emailError

            if (!it.passwordError.isNullOrEmpty().not())
                binding.edtPassword.error = it.passwordError

        })

        viewModel.resultEntry.observe(viewLifecycleOwner) {
            val resultMessage = if (it) {
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                getString(R.string.register_success)
            } else {
                getString(R.string.register_error)
            }

            Toast.makeText(requireContext(), resultMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

}