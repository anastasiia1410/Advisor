package com.example.auditorapp.screens.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.auditorapp.core.BaseFragment;
import com.example.auditorapp.databinding.FragmentSettingsBinding;


public class SettingFragment extends BaseFragment<FragmentSettingsBinding, SettingViewModel> {

    @Override
    protected SettingViewModel createViewModel() {
        return null;
    }

    @Override
    protected FragmentSettingsBinding createBinding(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return FragmentSettingsBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createInsets();
        super.onViewCreated(view, savedInstanceState);
        NavController navController =
                NavHostFragment.findNavController(SettingFragment.this);

        binding.tvSetting.setOnClickListener(v -> navController.popBackStack());

        binding.tvExit.setOnClickListener(v -> {
            NavDirections action =
                    SettingFragmentDirections.actionSettingFragmentToAuthorizationFragment();
            navController.navigate(action);
        });

    }


}
