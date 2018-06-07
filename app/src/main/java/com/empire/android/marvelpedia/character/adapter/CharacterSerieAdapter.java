package com.empire.android.marvelpedia.character.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.data.Serie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterSerieAdapter extends RecyclerView.Adapter<CharacterSerieAdapter.ViewHolder> {

    public interface CharacterStoryListener {
        void onCharacterStorySelected(Serie serie);
    }

    private Context context;
    private CharacterSerieAdapter.CharacterStoryListener listener;
    private List<Serie> serieList;

    public CharacterSerieAdapter(Context context, CharacterSerieAdapter.CharacterStoryListener listener, List<Serie> serieList){
        this.context = context;
        this.listener = listener;
        this.serieList = serieList;
    }

    @Override
    public CharacterSerieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_character_serie, parent, false);
        return new CharacterSerieAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CharacterSerieAdapter.ViewHolder holder, int position) {

        final Serie currentSerie = serieList.get(position);

        String imagePath = currentSerie.getImage().getPath() + "." + currentSerie.getImage().getExtension();

        Picasso.with(context).load(imagePath)
                .into(holder.characterSerieItemPreviewImageView);

        holder.characterSerieRelativeLayout.setOnClickListener(view -> listener.onCharacterStorySelected(currentSerie));

    }

    @Override
    public int getItemCount() {
        return (null != serieList ? serieList.size() : 0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.characterSerieRelativeLayout)
        RelativeLayout characterSerieRelativeLayout;

        @BindView(R.id.characterSerieItemPreviewImageView)
        ImageView characterSerieItemPreviewImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
