package com.example.autolist.ui.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.transition.TransitionManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.autolist.R;
import com.example.autolist.databinding.RecyclerAutoListItemBinding;
import com.example.autolist.pattern.models.Automobile;
import com.example.autolist.ui.activities.AutoEditActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.autolist.tools.Constants.ARG_AUTO_ID;
import static com.example.autolist.tools.Constants.REQUEST_ADD;

/**
 * Created by Andrei on 07.08.2018.
 */

public class AutoListAdapter extends RecyclerView.Adapter<AutoListAdapter.ViewHolder> {

    private final Activity mActivity;

    public List<Automobile> getData() {
        return data;
    }
    private List<Automobile> data;
    private final RecyclerView mView;


    public AutoListAdapter(Activity activity, RecyclerView view, List<Automobile> automobiles) {
        mActivity = activity;
        mView = view;
        data = automobiles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            default: {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                RecyclerAutoListItemBinding itemBinding = RecyclerAutoListItemBinding.inflate(layoutInflater, parent, false);
                return new ViewHolder(itemBinding);
            }
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void replaceAll(List<Automobile> filteredAutoList) {
        data = filteredAutoList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btn_edit)
        ImageView btnEdit;

        @BindView(R.id.btn_extend)
        ImageView btnExtend;

        @BindView(R.id.auto_details)
        ViewGroup details;

        private RecyclerAutoListItemBinding binding;
        private boolean isExpanded = false;


        ViewHolder(RecyclerAutoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            ButterKnife.bind(this, binding.getRoot());
        }

        void bind(final Automobile auto) {
            binding.setAuto(auto);
            binding.executePendingBindings();

            btnExtend.setOnClickListener(v -> {
                isExpanded = !isExpanded;
                details.setVisibility(isExpanded? View.VISIBLE: View.GONE);
                btnExtend.setImageDrawable(mActivity.getResources().getDrawable(
                        isExpanded?
                                R.drawable.ic_expand_less_black:
                                R.drawable.ic_expand_more_black
                ));

                TransitionManager.beginDelayedTransition(mView);
            });
            btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(mActivity, AutoEditActivity.class);
                intent.putExtra(ARG_AUTO_ID, auto.getId());
                mActivity.startActivityForResult(intent, REQUEST_ADD);
            });
        }
    }
}
