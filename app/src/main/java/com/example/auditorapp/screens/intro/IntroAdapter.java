package com.example.auditorapp.screens.intro;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auditorapp.databinding.ItemIntroBinding;
import com.example.auditorapp.entity.intro.IntroData;

import java.util.ArrayList;
import java.util.List;

public class IntroAdapter extends RecyclerView.Adapter<IntroAdapter.VH> {
    private List<IntroData> dataList = new ArrayList<>();

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemIntroBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        IntroData data = dataList.get(position);

        holder.binding.ivImage.setImageResource(data.getImage());
        holder.binding.tvText.setText(data.getText());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void updateItems(List<IntroData> newDataList) {
        dataList = newDataList;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        private final ItemIntroBinding binding;

        public VH(ItemIntroBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
