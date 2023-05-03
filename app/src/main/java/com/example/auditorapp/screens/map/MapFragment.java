package com.example.auditorapp.screens.map;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.auditorapp.core.BaseFragment;
import com.example.auditorapp.databinding.FragmentMapBinding;
import com.example.auditorapp.utils.PermissionUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapFragment extends BaseFragment<FragmentMapBinding, MapViewModel> implements OnMapReadyCallback {
    public static final String KEY = "key";
    private GoogleMap googleMap;

    @Override
    protected MapViewModel createViewModel() {
        return new ViewModelProvider(this).get(MapViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityResultContracts.RequestMultiplePermissions contracts =
                new ActivityResultContracts.RequestMultiplePermissions();
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(contracts, result -> {
                            boolean fineLocationGranted = PermissionUtil
                                    .getOrDefault(
                                            result,
                                            Manifest.permission.ACCESS_FINE_LOCATION);
                            boolean coarseLocationGranted = PermissionUtil
                                    .getOrDefault(
                                            result,
                                            Manifest.permission.ACCESS_COARSE_LOCATION);
                            if (fineLocationGranted && coarseLocationGranted) {
                                viewModel.loadCurrentLocation();
                            } else {
                                NavController navController =
                                        NavHostFragment
                                                .findNavController(MapFragment.this);
                                navController.popBackStack();
                            }
                        }
                );
        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION});
    }

    @Override
    protected FragmentMapBinding createBinding(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        return FragmentMapBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createInsets();
        super.onViewCreated(view, savedInstanceState);
        viewModel.getLatLngLD().observe(getViewLifecycleOwner(), location -> {
            zoomToCurrentLocation();
            binding.ivPin.setVisibility(View.VISIBLE);
        });

        viewModel.getAddressLD().observe(getViewLifecycleOwner(), s -> {
            Bundle bundle = new Bundle();
            bundle.putString(KEY, s);
            getParentFragmentManager().setFragmentResult(KEY, bundle);
            NavController navController =
                    NavHostFragment.findNavController(MapFragment.this);
            navController.popBackStack();
        });

        SupportMapFragment supportMapFragment = binding.fcMap.getFragment();
        supportMapFragment.getMapAsync(this);

        binding.ivDone.setOnClickListener(v -> {
            if (googleMap != null) {
                LatLng latLng = googleMap.getCameraPosition().target;
                viewModel.saveLocation(latLng);
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        zoomToCurrentLocation();
    }

    private void zoomToCurrentLocation() {
        LatLng latLng = viewModel.getLatLngLD().getValue();
        if (latLng != null && googleMap != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }
}
