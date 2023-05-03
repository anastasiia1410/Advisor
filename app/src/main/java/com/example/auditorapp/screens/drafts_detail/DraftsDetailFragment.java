package com.example.auditorapp.screens.drafts_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.auditorapp.core.BaseFragment;
import com.example.auditorapp.databinding.FragmentDraftsDetailsBinding;
import com.example.auditorapp.entity.drafts.Drafts;
import com.example.auditorapp.utils.DraftsStatusUtil;
import com.example.auditorapp.utils.ImageUtil;
import com.example.auditorapp.utils.TextUtil;

public class DraftsDetailFragment extends BaseFragment<FragmentDraftsDetailsBinding, DetailsViewModel> {
    public static final String KEY_TO_UPDATE = "KEY";

    @Override
    protected DetailsViewModel createViewModel() {
        return new ViewModelProvider(this).get(DetailsViewModel.class);
    }

    @Override
    protected FragmentDraftsDetailsBinding createBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentDraftsDetailsBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createInsets();
        super.onViewCreated(view, savedInstanceState);
        DraftsDetailFragmentArgs args = DraftsDetailFragmentArgs.fromBundle(requireArguments());
        String title = args.getTitle();
        viewModel.getDetailInfo(title);
        viewModel.getReviewLD().observe(getViewLifecycleOwner(), drafts -> {
            binding.tvTitle.setText(drafts.getTitle());
            if (drafts.getImage().equals("null")) {
                binding.ivPhoto.setVisibility(View.GONE);
            } else {
                binding.ivPhoto.setVisibility(View.VISIBLE);
                ImageUtil.load(binding.ivPhoto, drafts.getImage());
            }
            binding.tvTextReview.setText(drafts.getTextReview());
            binding.tvDate.setText(TextUtil.getFullDateInStr(drafts.getDate()));
            binding.tvAddress.setText(drafts.getLocation());
            DraftsStatusUtil.showDraftsStatus(drafts, binding.tvSend, binding.tvSending, binding.tvSent);
        });

        viewModel.getThrowableLD().observe(getViewLifecycleOwner(), throwable ->
                showSnackBar(requireContext(), binding.getRoot(),
                        "Sorry, something wrong with your internet connection"));

        binding.tvSend.setOnClickListener(v -> {
            Drafts drafts = viewModel.getReviewLD().getValue();
            if (drafts != null) {
                viewModel.sendDrafts(drafts);
            }
        });

        binding.tvTitle.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            getParentFragmentManager().setFragmentResult(KEY_TO_UPDATE, bundle);
            NavController navController = NavHostFragment.findNavController(DraftsDetailFragment.this);
            navController.popBackStack();
        });
    }
}
