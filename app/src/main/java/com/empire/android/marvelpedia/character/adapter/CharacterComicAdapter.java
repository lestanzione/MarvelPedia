package com.empire.android.marvelpedia.character.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.data.Comic;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterComicAdapter extends RecyclerView.Adapter<CharacterComicAdapter.ViewHolder> {

    public interface CharacterComicListener {
        void onCharacterComicSelected(Comic comic);
    }

    private Context context;
    private CharacterComicAdapter.CharacterComicListener listener;
    private List<Comic> comicList;

    public CharacterComicAdapter(Context context, CharacterComicAdapter.CharacterComicListener listener, List<Comic> comicList){
        this.context = context;
        this.listener = listener;
        this.comicList = comicList;
    }

    @Override
    public CharacterComicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_character_comic, parent, false);
        return new CharacterComicAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CharacterComicAdapter.ViewHolder holder, int position) {

        final Comic currentComic = comicList.get(position);
        final int comicPosition = position;

        String imagePath = currentComic.getImage().getPath() + "." + currentComic.getImage().getExtension();

        Picasso.with(context).load(imagePath).placeholder(ContextCompat.getDrawable(context, R.drawable.placeholder_loading_item))
                .into(holder.characterComicItemPreviewImageView);

        holder.characterComicRelativeLayout.setOnClickListener(view -> listener.onCharacterComicSelected(currentComic));

    }

    @Override
    public int getItemCount() {
        return (null != comicList ? comicList.size() : 0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.characterComicRelativeLayout)
        RelativeLayout characterComicRelativeLayout;

        @BindView(R.id.characterComicItemPreviewImageView)
        ImageView characterComicItemPreviewImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
