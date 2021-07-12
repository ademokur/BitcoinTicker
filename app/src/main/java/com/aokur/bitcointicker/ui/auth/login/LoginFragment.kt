package com.aokur.bitcointicker.ui.auth.login

import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aokur.bitcointicker.R
import com.aokur.bitcointicker.databinding.FragmentLoginBinding
import com.aokur.bitcointicker.ui.auth.EntryType
import com.aokur.bitcointicker.ui.base.BaseFragment
import com.aokur.bitcointicker.util.CustomTextWatcher
import com.aokur.bitcointicker.util.EntryState
import com.aokur.bitcointicker.util.verifyEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val TAG: String
        get() = LoginFragment::class.java.simpleName

    private val viewModel: LoginViewModel by viewModels()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_login
    }

    override fun init(savedInstanceState: Bundle?) {

        viewModel.setEntryType(EntryType.LOGIN)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginViewModel = viewModel

        binding.edtEmail.afterTextChanged {
            viewModel.isDataChanged(
                EntryState.EMAIL,
                binding.edtEmail.text.toString()
            )
        }

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        viewModel.entryFormState.observe(viewLifecycleOwner, {
            binding.btnLogin.isEnabled = it.isDataValid

            if (it.passwordError.isNullOrEmpty().not())
                binding.edtPassword.error = it.passwordError
            if (it.emailError.isNullOrEmpty().not())
                binding.edtEmail.error = it.emailError
        })

        viewModel.isVerifiedSent.observe(viewLifecycleOwner) {
            if (it)
                viewModel.sendEmailVerify()
            else
                Toast.makeText(requireContext(), getString(R.string.error_email_address), Toast.LENGTH_LONG).show()
        }

        viewModel.resultEntry.observe(viewLifecycleOwner) {
            var resultMessage = if (it) {
                checkUserVerifiedAndNavigate()
                getString(R.string.login_success)
            } else {
                getString(R.string.login_error)
            }

            Toast.makeText(requireContext(), resultMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun checkUserVerifiedAndNavigate() {
        val isUserVerified = viewModel.checkIfUserVerified()

        if (!isUserVerified)
            requireContext().verifyEmail()

        findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToCoinFragment())
    }

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        addTextChangedListener(object : CustomTextWatcher() {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }
}