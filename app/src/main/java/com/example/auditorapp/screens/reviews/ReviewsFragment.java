package com.example.auditorapp.screens.reviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.auditorapp.core.BaseFragment;
import com.example.auditorapp.databinding.FragmentReviewsBinding;
import com.example.auditorapp.screens.drafts_detail.DraftsDetailFragment;
import com.example.auditorapp.screens.review_datail.ReviewDetailFragment;

public class ReviewsFragment extends BaseFragment<FragmentReviewsBinding, ReviewViewModel> {
    private DrawerActionViewModel actionViewModel;
    private ReviewAdapter adapter;


    @Override
    protected FragmentReviewsBinding createBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentReviewsBinding.inflate(inflater, container, false);
    }

    @Override
    protected ReviewViewModel createViewModel() {
        return new ViewModelProvider(this).get(ReviewViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionViewModel = new ViewModelProvider(requireActivity()).get(DrawerActionViewModel.class);
        adapter = new ReviewAdapter();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createInsets();
        super.onViewCreated(view, savedInstanceState);
        binding.rvRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvRecycler.setAdapter(adapter);
        binding.tvReviews.setOnClickListener(v -> binding.getRoot().openDrawer(GravityCompat.START));
        actionViewModel.getIsButtonClickLD().observe(getViewLifecycleOwner(), drawerTabs -> {
            NavController navController = NavHostFragment.findNavController(ReviewsFragment.this);
            NavDirections action;
            switch (drawerTabs) {
                case Reviews:
                    closeDrawer();
                    actionViewModel.onClearButtonClick();
                    break;
                case Drafts:
                    closeDrawer();
                    actionViewModel.onClearButtonClick();
                    action = ReviewsFragmentDirections.actionReviewsFragmentToDraftsFragment();
                    navController.navigate(action);
                    break;
                case Settings:
                    closeDrawer();
                    actionViewModel.onClearButtonClick();
                    action = ReviewsFragmentDirections.actionReviewsFragmentToSettingFragment();
                    navController.navigate(action);
                    break;
            }
        });

        getParentFragmentManager()
                .setFragmentResultListener(DraftsDetailFragment.KEY_TO_UPDATE,
                        this, (requestKey, result) -> viewModel.getReviews());

        getParentFragmentManager().setFragmentResultListener(ReviewDetailFragment.UPD_KEY,
                this, (requestKey, result) -> viewModel.getReviews());

        viewModel.getReviewLD().observe(getViewLifecycleOwner(), reviews -> adapter.updateItems(reviews));

        binding.fabAddNew.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(ReviewsFragment.this);
            NavDirections action = ReviewsFragmentDirections.actionReviewsFragmentToCreateReviewFragment();
            navController.navigate(action);
        });


        adapter.setOnReviewClick(objectId -> {
            NavController navController = NavHostFragment.findNavController(ReviewsFragment.this);
            NavDirections action = ReviewsFragmentDirections.actionReviewsFragmentToReviewDetailFragment(objectId);
            navController.navigate(action);
        });

        binding.splSwipe.setOnRefreshListener(() -> {
            viewModel.getReviews();
            binding.splSwipe.setRefreshing(false);
        });
}

    public void closeDrawer() {
        binding.getRoot().closeDrawer(GravityCompat.START);
    }
}
