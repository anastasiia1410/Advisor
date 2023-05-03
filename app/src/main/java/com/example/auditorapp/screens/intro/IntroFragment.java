package com.example.auditorapp.screens.intro;

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
import androidx.viewpager2.widget.ViewPager2;

import com.example.auditorapp.core.BaseFragment;
import com.example.auditorapp.databinding.FragmentIntroBinding;

public class IntroFragment extends BaseFragment<FragmentIntroBinding, IntroViewModel> {
    private ViewPager2.OnPageChangeCallback callback;
    private IntroAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new IntroAdapter();
    }

    @Override
    protected IntroViewModel createViewModel() {
        return new ViewModelProvider(this).get(IntroViewModel.class);
    }

    @Override
    protected FragmentIntroBinding createBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentIntroBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getIntroData();
        viewModel.getIntroDataLD().observe(getViewLifecycleOwner(), introData -> adapter.updateItems(introData));
        binding.vpPager.setAdapter(adapter);
        callback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                visibilityBt(position);
            }
        };

        binding.btGo.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(IntroFragment.this);
            NavDirections action = IntroFragmentDirections.actionIntroFragmentToAuthorizationFragment();
            navController.navigate(action);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.vpPager.registerOnPageChangeCallback(callback);
    }

    private void visibilityBt(int position) {
        if (position == adapter.getItemCount() - 1) {
            binding.btGo.setVisibility(View.VISIBLE);
        } else {
            binding.btGo.setVisibility(View.GONE);
        }
    }
}

