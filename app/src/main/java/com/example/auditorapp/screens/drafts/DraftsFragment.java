package com.example.auditorapp.screens.drafts;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.auditorapp.core.BaseFragment;
import com.example.auditorapp.databinding.FragmentDraftsBinding;
import com.example.auditorapp.screens.drafts_detail.DraftsDetailFragment;

public class DraftsFragment extends BaseFragment<FragmentDraftsBinding, DraftsViewModel> {
    public static final String KEY_TO_UPD = "_KEY_";
    private DraftsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DraftsAdapter();
    }

    @Override
    protected FragmentDraftsBinding createBinding(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return FragmentDraftsBinding.inflate(inflater, container, false);
    }

    @Override
    protected DraftsViewModel createViewModel() {
        return new ViewModelProvider(this).get(DraftsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createInsets();
        super.onViewCreated(view, savedInstanceState);
        binding.rvRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvRecycler.setAdapter(adapter);

        getParentFragmentManager()
                .setFragmentResultListener(DraftsDetailFragment.KEY_TO_UPDATE,
                        this, (requestKey, result) ->
                   viewModel.getDraftsReviews());

        viewModel.getDraftsLD().observe(getViewLifecycleOwner(), drafts -> adapter.updateItems(drafts));

        adapter.setOnClick(title -> {
            NavController navController = NavHostFragment.findNavController(DraftsFragment.this);
            NavDirections action = DraftsFragmentDirections.actionDraftsFragmentToDraftsDetailFragment(title);
            navController.navigate(action);
        });

        viewModel.getThrowableLD().observe(getViewLifecycleOwner(), throwable ->
                showSnackBar(requireContext(), binding.getRoot(),
                        "Sorry, something wrong with your internet connection"));

        adapter.setOnSendClick(draft -> viewModel.sendDrafts(draft));

        binding.tvDrafts.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            getParentFragmentManager().setFragmentResult(KEY_TO_UPD, bundle);
            NavController navController = NavHostFragment.findNavController(DraftsFragment.this);
            navController.popBackStack();
        });




    }
}

