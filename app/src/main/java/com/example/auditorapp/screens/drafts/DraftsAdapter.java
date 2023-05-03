package com.example.auditorapp.screens.drafts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.auditorapp.databinding.ItemDraftsBinding;
import com.example.auditorapp.entity.drafts.Drafts;
import com.example.auditorapp.utils.DraftsStatusUtil;
import com.example.auditorapp.utils.ImageUtil;
import com.example.auditorapp.utils.OnButtonSendClick;
import com.example.auditorapp.utils.OnDraftsClick;

import java.util.ArrayList;
import java.util.List;

public class DraftsAdapter extends RecyclerView.Adapter<DraftsAdapter.VH> {
    private List<Drafts> draftsList = new ArrayList<>();
    private OnDraftsClick onClick;
    private OnButtonSendClick onSendClick;

    @NonNull
    @Override
    public DraftsAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemDraftsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DraftsAdapter.VH holder, int position) {
        Drafts drafts = draftsList.get(position);
        DraftsStatusUtil.showDraftsStatus(
                drafts,
                holder.binding.tvSend,
                holder.binding.tvSending,
                holder.binding.tvSent);
        holder.binding.tvTitle.setText(drafts.getTitle());
        holder.binding.tvTextReview.setText(drafts.getTextReview());
        if (drafts.getImage().equals("null")) {
            holder.binding.ivPhoto.setVisibility(View.GONE);
        } else {
            holder.binding.ivPhoto.setVisibility(View.VISIBLE);
            ImageUtil.load(holder.binding.ivPhoto, drafts.getImage());
        }
        holder.binding.cvCardView.setOnClickListener(v -> onClick.onClick(drafts.getTitle()));
        holder.binding.tvSend.setOnClickListener(v -> onSendClick.onClick(drafts));

    }

    @Override
    public int getItemCount() {
        return draftsList.size();
    }

    public void updateItems(List<Drafts> newDraftsList) {
        draftsList = newDraftsList;
        notifyDataSetChanged();
    }

    public void setOnClick(OnDraftsClick onClick) {
        this.onClick = onClick;
    }

    public void setOnSendClick(OnButtonSendClick onSendClick) {
        this.onSendClick = onSendClick;
    }

    static class VH extends RecyclerView.ViewHolder {
        private final ItemDraftsBinding binding;

        public VH(ItemDraftsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
