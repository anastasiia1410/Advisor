package com.example.auditorapp.screens.reviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.auditorapp.core.BaseFragment;
import com.example.auditorapp.databinding.FragmentDrawerBinding;

public class DrawerFragment extends BaseFragment<FragmentDrawerBinding, DrawerViewModel> {
    private DrawerActionViewModel actionViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionViewModel = new ViewModelProvider(requireActivity()).get(DrawerActionViewModel.class);
    }

    @Override
    protected DrawerViewModel createViewModel() {
        return new ViewModelProvider(this).get(DrawerViewModel.class);
    }

    @Override
    protected FragmentDrawerBinding createBinding(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return FragmentDrawerBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getUserLD().observe(getViewLifecycleOwner(),
                user -> binding.tvUserEmail.setText(user.getUserName()));

        binding.cvReviews.setOnClickListener(v -> actionViewModel.onReviewsClick());

        binding.cvDrafts.setOnClickListener(v -> actionViewModel.onDraftsClick());

        binding.cvSettings.setOnClickListener(v -> actionViewModel.onSettingClick());


    }
}
