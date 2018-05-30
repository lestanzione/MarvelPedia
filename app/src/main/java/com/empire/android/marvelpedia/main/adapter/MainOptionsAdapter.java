package com.empire.android.marvelpedia.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empire.android.marvelpedia.R;

public class MainOptionsAdapter extends RecyclerView.Adapter<MainOptionsAdapter.ViewHolder> {

    public interface MainOptionListener {
        void onMainOptionSelected(int position);
    }

    private MainOptionListener listener;
    private String[] mainOptionList;

    public MainOptionsAdapter(MainOptionListener listener, String[] mainOptionList){
        this.listener = listener;
        this.mainOptionList = mainOptionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_option, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String currentOption = mainOptionList[position];
        final int optionPosition = position;

        holder.mainOptionItemTitleTextView.setText(currentOption);

        holder.mainOptionItemRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMainOptionSelected(optionPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != mainOptionList ? mainOptionList.length : 0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final RelativeLayout mainOptionItemRelativeLayout;
        final TextView mainOptionItemTitleTextView;
        final ImageView mainOptionItemImageView;

        ViewHolder(View view) {
            super(view);
            mainOptionItemRelativeLayout = view.findViewById(R.id.mainOptionItemRelativeLayout);
            mainOptionItemTitleTextView = view.findViewById(R.id.mainOptionItemTextView);
            mainOptionItemImageView = view.findViewById(R.id.mainOptionItemImageView);
        }

    }

}
