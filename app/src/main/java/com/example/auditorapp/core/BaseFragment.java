package com.example.auditorapp.core;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.snackbar.Snackbar;

public abstract class BaseFragment<VB extends ViewBinding, VM extends AndroidViewModel> extends Fragment {
    protected VB binding;
    protected VM viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = createViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = createBinding(inflater, container, savedInstanceState);

        return binding.getRoot();
    }

    protected abstract VB createBinding(LayoutInflater inflater,
                                        ViewGroup container,
                                        Bundle savedInstanceState);

    protected abstract VM createViewModel();

    protected void showToast(String text) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG)
                .show();
    }

    protected void showSnackBar(Context context, View view, String text) {
        Snackbar.make(context, view, text, Snackbar.LENGTH_LONG).show();
    }

    protected void createInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBarInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(v.getPaddingStart(),
                    systemBarInsets.top,
                    v.getPaddingEnd(),
                    v.getPaddingBottom());

            return insets;
        });

    }
}
