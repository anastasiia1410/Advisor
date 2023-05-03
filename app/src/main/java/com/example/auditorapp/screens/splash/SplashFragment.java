package com.example.auditorapp.screens.splash;

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
import com.example.auditorapp.core.BaseFragment;
import com.example.auditorapp.databinding.FragmentSplashScreenBinding;

public class SplashFragment extends BaseFragment<FragmentSplashScreenBinding, SplashViewModel> {

    @Override
    protected SplashViewModel createViewModel() {
        return viewModel = new ViewModelProvider(this).get(SplashViewModel.class);
    }

    @Override
    protected FragmentSplashScreenBinding createBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentSplashScreenBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getScreensLD().observe(getViewLifecycleOwner(), screensNavigate -> {
            NavController navController = NavHostFragment.findNavController(SplashFragment.this);
            NavDirections action;
            switch (screensNavigate) {
                case IntroScreen:
                    action = SplashFragmentDirections.actionSplashFragmentToIntroFragment();
                    navController.navigate(action);
                    break;
                case HomeScreen:
                    action = SplashFragmentDirections.actionSplashFragmentToReviewsFragment();
                    navController.navigate(action);
                    break;
                case AuthorizationScreen:
                    action = SplashFragmentDirections.actionSplashFragmentToAuthorizationFragment();
                    navController.navigate(action);
                    break;
            }
        });

        viewModel.startTick();

    }
}


