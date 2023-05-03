package com.example.auditorapp.screens.reviews;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.auditorapp.databinding.ItemReviewsBinding;
import com.example.auditorapp.entity.review.Review;
import com.example.auditorapp.utils.OnReviewClick;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.VH> {
    private List<Review> reviewList = new ArrayList<>();
    private OnReviewClick onReviewClick;
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemReviewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Review review = reviewList.get(position);
        holder.binding.tvTitle.setText(review.getTitle());
        holder.binding.tvTextReview.setText(review.getText());
        holder.binding.tvUserName.setText(review.getAuthor());
        holder.binding.cvCardView.setOnClickListener(v -> onReviewClick.onClick(review.getObjectId()));


    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public void updateItems(List<Review> newReviewList) {
        reviewList = newReviewList;
        notifyDataSetChanged();
    }

    public void setOnReviewClick(OnReviewClick onReviewClick) {
        this.onReviewClick = onReviewClick;
    }

    static class VH extends  RecyclerView.ViewHolder{
        private final ItemReviewsBinding binding;

        public VH(ItemReviewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
