package com.empire.android.marvelpedia.comiclist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.data.Comic;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComicListAdapter extends RecyclerView.Adapter<ComicListAdapter.ViewHolder> {

    public interface ComicListener {
        void onComicSelected(Comic comic);
    }

    private Context context;
    private ComicListAdapter.ComicListener listener;
    private List<Comic> comicList;

    public ComicListAdapter(Context context, ComicListAdapter.ComicListener listener, List<Comic> comicList){
        this.context = context;
        this.listener = listener;
        this.comicList = comicList;
    }

    @Override
    public ComicListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comic_list, parent, false);
        return new ComicListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComicListAdapter.ViewHolder holder, int position) {

        final Comic currentComic = comicList.get(position);

        holder.comicItemNameTextView.setText(currentComic.getTitle());

        String imagePath = currentComic.getImage().getPath() + "." + currentComic.getImage().getExtension();

        Picasso.with(context).load(imagePath)
                .into(holder.comicItemPreviewImageView);

        holder.comicItemRelativeLayout.setOnClickListener(view -> listener.onComicSelected(currentComic));

    }

    @Override
    public int getItemCount() {
        return (null != comicList ? comicList.size() : 0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comicItemRelativeLayout)
        RelativeLayout comicItemRelativeLayout;

        @BindView(R.id.comicItemNameTextView)
        TextView comicItemNameTextView;

        @BindView(R.id.comicItemPreviewImageView)
        ImageView comicItemPreviewImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}