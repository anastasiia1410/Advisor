package com.example.auditorapp.screens.create_reviews;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.auditorapp.core.BaseFragment;
import com.example.auditorapp.databinding.FragmentCreateReviewBinding;
import com.example.auditorapp.screens.map.MapFragment;
import com.example.auditorapp.utils.TextUtil;

import java.io.File;

public class CreateReviewFragment extends BaseFragment<FragmentCreateReviewBinding, CreateViewModel> {
    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected CreateViewModel createViewModel() {
        return new ViewModelProvider(this).get(CreateViewModel.class);
    }

    @Override
    protected FragmentCreateReviewBinding createBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentCreateReviewBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createInsets();
        super.onViewCreated(view, savedInstanceState);
        NavController navController = NavHostFragment.findNavController(CreateReviewFragment.this);
        binding.ivArrowBack.setOnClickListener(v -> navController.popBackStack());
        binding.tvAddress.setOnClickListener(v -> {
            NavDirections action = CreateReviewFragmentDirections
                    .actionCreateReviewFragmentToMapFragment();
            navController.navigate(action);
        });

        viewModel.getReviewLD().observe(getViewLifecycleOwner(), aBoolean -> navController.popBackStack());


        String path = String.valueOf(viewModel.getFileLD().getValue());
        binding.ivDone.setOnClickListener(v -> viewModel.insertReview(
                TextUtil.inputText(binding.etTitle),
                TextUtil.inputText(binding.etReview),
                TextUtil.inputText(binding.tvAddress),
                path));

        binding.tvMakePhoto.setOnClickListener(v -> {

            File file = viewModel.getFileLD().getValue();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(file != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "com.example.auditorapp.fileprovider",
                        file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                launcher.launch(intent);
            }
        });

        showPhoto();
        setAddress();
    }

    private void setAddress() {
        getParentFragmentManager()
                .setFragmentResultListener(MapFragment.KEY, this, (requestKey, result) -> {
                    String address = result.getString(requestKey);
                    if (address != null) {
                        binding.tvAddress.setText(address);
                    }
                });
    }

    private void showPhoto() {
        ActivityResultContracts.StartActivityForResult contracts = new ActivityResultContracts.StartActivityForResult();
        launcher = registerForActivityResult(contracts, result -> {
            if (result.getResultCode() == RESULT_OK) {
                File file = viewModel.getFileLD().getValue();
                Glide.with(binding.ivNewPhoto).load(file).fitCenter().centerCrop().into(binding.ivNewPhoto);
            }
        });
    }
}



