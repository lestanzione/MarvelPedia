package com.empire.android.marvelpedia.characterlist.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.data.Character;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    public interface CharacterListener {
        void onCharacterSelected(int position);
    }

    private CharacterListAdapter.CharacterListener listener;
    private List<Character> characterList;

    public CharacterListAdapter(CharacterListAdapter.CharacterListener listener, List<Character> characterList){
        this.listener = listener;
        this.characterList = characterList;
    }

    @Override
    public CharacterListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_character_list, parent, false);
        return new CharacterListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CharacterListAdapter.ViewHolder holder, int position) {

        final Character currentCharacter = characterList.get(position);
        final int characterPosition = position;

        holder.characterItemNameTextView.setText(currentCharacter.getName());
        holder.characterItemDescription.setText(currentCharacter.getDescription());

        holder.characterItemRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCharacterSelected(characterPosition);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != characterList ? characterList.size() : 0);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.characterItemRelativeLayout)
        RelativeLayout characterItemRelativeLayout;

        @BindView(R.id.characterItemNameTextView)
        TextView characterItemNameTextView;

        @BindView(R.id.characterItemPreviewImageView)
        ImageView characterItemPreviewImageView;

        @BindView(R.id.characterItemDescription)
        TextView characterItemDescription;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

}