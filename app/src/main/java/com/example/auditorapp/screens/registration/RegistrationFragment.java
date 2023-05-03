package com.example.auditorapp.screens.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.auditorapp.R;
import com.example.auditorapp.core.BaseFragment;
import com.example.auditorapp.databinding.FragmentRegistrationBinding;
import com.example.auditorapp.entity.user.User;
import com.example.auditorapp.utils.TextUtil;

import java.util.Objects;

public class RegistrationFragment extends BaseFragment<FragmentRegistrationBinding, RegistrationViewModel> {

    @Override
    protected FragmentRegistrationBinding createBinding(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return FragmentRegistrationBinding.inflate(inflater, container, false);
    }

    @Override
    protected RegistrationViewModel createViewModel() {
        return new ViewModelProvider(this).get(RegistrationViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getSetTextLD().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                binding.btLogin.setText(R.string.send);
            } else {
                binding.btLogin.setText(R.string.create);
            }
        });

        viewModel.getTokenLD().observe(getViewLifecycleOwner(), s -> {
            NavController navController = NavHostFragment
                    .findNavController(RegistrationFragment.this);
            NavDirections action =
                    RegistrationFragmentDirections.actionRegistrationFragmentToReviewsFragment();
            navController.navigate(action);
        });
        viewModel.getNoInternetErrorLD().observe(getViewLifecycleOwner(), throwable ->
                showSnackBar(
                        requireContext(),
                        binding.btLogin,
                        "Sorry, no internet connection"));
        viewModel.getTimeOutErrorLD().observe(getViewLifecycleOwner(), throwable ->
                showSnackBar(
                        requireContext(),
                        binding.btLogin,
                        "Sorry, server is not responding"));

        binding.btLogin.setOnClickListener(v -> {
            User user = new User();
            user.setEmail(TextUtil.inputText(binding.etLogin));
            user.setUserName(TextUtil.inputText(binding.etName));
            user.setPassword(TextUtil.inputText(binding.etPassword));
            boolean isValidFields = validateFields();
            if (isValidFields) {
                viewModel.addNewUser(user);
            }
        });

        binding.ivArrowBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment
                    .findNavController(RegistrationFragment.this);
            navController.popBackStack();
        });
    }

    private boolean validateFields() {

        boolean isValidFields = true;

        if (!((Objects.requireNonNull(binding.etPassword.getText()).toString())
                .equals(Objects.requireNonNull(binding.etPasswordRepeat.getText()).toString()))) {
            binding.etPasswordRepeat.setError("Something wrong with password");
            isValidFields = false;
        }
        return isValidFields;
    }
}



