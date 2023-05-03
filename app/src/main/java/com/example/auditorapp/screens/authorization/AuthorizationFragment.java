package com.example.auditorapp.screens.authorization;

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
import com.example.auditorapp.databinding.FragmentAuthorizationBinding;
import com.example.auditorapp.entity.user.User;
import com.example.auditorapp.utils.TextUtil;

public class AuthorizationFragment extends BaseFragment<FragmentAuthorizationBinding, AuthorizationViewModel> {

    @Override
    protected FragmentAuthorizationBinding createBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentAuthorizationBinding.inflate(inflater, container, false);
    }

    @Override
    protected AuthorizationViewModel createViewModel() {
        return new ViewModelProvider(this).get(AuthorizationViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getSetTextLD().observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                binding.btLogin.setText(R.string.send);
            } else {
                binding.btLogin.setText(R.string.login);
            }
        });

        viewModel.getTokenLD().observe(getViewLifecycleOwner(), s -> {
            NavController navController = NavHostFragment
                    .findNavController(AuthorizationFragment.this);
            NavDirections action = AuthorizationFragmentDirections
                    .actionAuthorizationFragmentToReviewsFragment();
            navController.navigate(action);
        });

        binding.btLogin.setOnClickListener(v -> {
            User user = new User();
            user.setUserName(TextUtil.inputText(binding.etLogin));
            user.setPassword(TextUtil.inputText(binding.etPassword));
            viewModel.getLoggingUser(user);

        });

        viewModel.getNoInternetErrorLD().observe(getViewLifecycleOwner(), throwable ->
                showSnackBar(requireContext(), binding.btLogin, "Sorry, no internet connection"));
        viewModel.getTimeOutErrorLD().observe(getViewLifecycleOwner(), throwable ->
                showSnackBar(requireContext(), binding.btLogin, "Sorry, server is not responding"));

        binding.tvCreateNewAcc.setOnClickListener(v -> {
            NavController navController = NavHostFragment
                    .findNavController(AuthorizationFragment.this);
            NavDirections action = AuthorizationFragmentDirections
                    .actionAuthorizationFragmentToRegistrationFragment();
            navController.navigate(action);
        });
    }
}

