package com.empire.android.marvelpedia.comic.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.data.Character;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ComicCharacterAdapter extends RecyclerView.Adapter<ComicCharacterAdapter.ViewHolder> {

    public interface ComicCharacterListener {
        void onComicCharacterSelected(Character character);
    }

    private Context context;
    private ComicCharacterListener listener;
    private List<Character> characterList;

    public ComicCharacterAdapter(Context context, ComicCharacterListener listener, List<Character> characterList){
        this.context = context;
        this.listener = listener;
        this.characterList = characterList;
    }

    @Override
    public ComicCharacterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_comic_character, parent, false);
        return new ComicCharacterAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ComicCharacterAdapter.ViewHolder holder, int position) {

        final Character currentCharacter = characterList.get(position);

        String imagePath = currentCharacter.getImage().getPath() + "." + currentCharacter.getImage().getExtension();

        Picasso.with(context).load(imagePath).placeholder(ContextCompat.getDrawable(context, R.drawable.placeholder_loading_item))
                .into(holder.comicCharacterItemPreviewImageView);

        holder.comicCharacterRelativeLayout.setOnClickListener(view -> listener.onComicCharacterSelected(currentCharacter));

    }

    @Override
    public int getItemCount() {
        return (null != characterList ? characterList.size() : 0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comicCharacterRelativeLayout)
        RelativeLayout comicCharacterRelativeLayout;

        @BindView(R.id.comicCharacterItemPreviewImageView)
        ImageView comicCharacterItemPreviewImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
