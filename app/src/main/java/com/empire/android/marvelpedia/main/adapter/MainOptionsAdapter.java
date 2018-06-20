package com.empire.android.marvelpedia.main.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empire.android.marvelpedia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainOptionsAdapter extends RecyclerView.Adapter<MainOptionsAdapter.ViewHolder> {

    public interface MainOptionListener {
        void onMainOptionSelected(int position);
    }

    private Context context;
    private MainOptionListener listener;
    private String[] mainOptionList;
    private int[] mainOptionIconIdList;

    public MainOptionsAdapter(Context context, MainOptionListener listener, String[] mainOptionList, int[] mainOptionIconIdList){
        this.context = context;
        this.listener = listener;
        this.mainOptionList = mainOptionList;
        this.mainOptionIconIdList = mainOptionIconIdList;
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
        holder.mainOptionItemImageView.setImageDrawable(ContextCompat.getDrawable(context, mainOptionIconIdList[position]));

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

        @BindView(R.id.mainOptionItemRelativeLayout)
        RelativeLayout mainOptionItemRelativeLayout;

        @BindView(R.id.mainOptionItemTextView)
        TextView mainOptionItemTitleTextView;

        @BindView(R.id.mainOptionItemImageView)
        ImageView mainOptionItemImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}
