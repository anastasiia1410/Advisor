package com.example.auditorapp.screens.review_datail;

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
import com.example.auditorapp.databinding.FragmentReviewDatailsBinding;
import com.example.auditorapp.utils.TextUtil;

public class ReviewDetailFragment extends BaseFragment<FragmentReviewDatailsBinding, ReviewDetailViewModel> {
    public static final String UPD_KEY  = "key";

    @Override
    protected FragmentReviewDatailsBinding createBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentReviewDatailsBinding.inflate(inflater, container, false);
    }

    @Override
    protected ReviewDetailViewModel createViewModel() {
        return new ViewModelProvider(this).get(ReviewDetailViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReviewDetailFragmentArgs args = ReviewDetailFragmentArgs.fromBundle(requireArguments());
        String objectId = args.getObjectId();
        viewModel.getReviewLD().observe(getViewLifecycleOwner(), review -> {
            binding.tvTitle.setText(review.getTitle());
            binding.tvUserName.setText(review.getAuthor());
            binding.tvTextReview.setText(review.getText());
            binding.tvAddress.setText(review.getAddress());
            binding.tvDate.setText(TextUtil.getFullDateInStr(review.getDate()));
        });
        viewModel.getReviewDetail(objectId);

        binding.ivDelete.setOnClickListener(v -> {
            viewModel.deleteReview(objectId);
            Bundle bundle = new Bundle();
            getParentFragmentManager().setFragmentResult(UPD_KEY, bundle);
            showToast("Рецензію було успішно видалено");
            NavController navController = NavHostFragment.findNavController(ReviewDetailFragment.this);
            navController.popBackStack();
        });

        binding.tvTitle.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(ReviewDetailFragment.this);
            navController.popBackStack();
        });
    }
}

