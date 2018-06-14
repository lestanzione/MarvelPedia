package com.empire.android.marvelpedia.comic;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.empire.android.marvelpedia.App;
import com.empire.android.marvelpedia.Configs;
import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.characterlist.CharacterListActivity;
import com.empire.android.marvelpedia.comic.adapter.ComicCharacterAdapter;
import com.empire.android.marvelpedia.comiclist.ComicListActivity;
import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.data.Serie;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.empire.android.marvelpedia.Configs.ARG_SELECTED_COMIC;

public class ComicActivity extends AppCompatActivity implements ComicContract.View, ComicCharacterAdapter.ComicCharacterListener {

    public static final String TAG = ComicActivity.class.getSimpleName();

    @Inject
    ComicContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.comicNameTextView)
    TextView comicNameTextView;

    @BindView(R.id.comicDescriptionTextView)
    TextView comicDescriptionTextView;

    @BindView(R.id.comicPreviewImageView)
    ImageView comicPreviewImageView;

    @BindView(R.id.comicCharactersRecyclerView)
    RecyclerView comicCharactersRecyclerView;

    @BindView(R.id.comicCharacterSeeAllTextView)
    TextView comicCharacterSeeAllTextView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);

        setUpInjector();
        setUpUi();

        long comicId = getIntent().getLongExtra(ARG_SELECTED_COMIC, 0);

        presenter.getComicInfo(comicId);
    }

    private void setUpUi() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comic");

        comicCharactersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        comicCharacterSeeAllTextView.setOnClickListener(view -> presenter.seeAllCharactersClicked());
    }

    private void setUpInjector() {
        ((App) getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        presenter.attachView(this);
    }

    @Override
    public void setProgressBarVisible(boolean visible) {
        if(visible) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setComicName(String name) {
        comicNameTextView.setBackgroundColor(Color.TRANSPARENT);
        comicNameTextView.setText(name);
    }

    @Override
    public void setComicDescription(String description) {
        comicDescriptionTextView.setBackgroundColor(Color.TRANSPARENT);
        comicDescriptionTextView.setText(description);
    }

    @Override
    public void setComicImage(String imageUrl) {
        Picasso.with(this).load(imageUrl).placeholder(ContextCompat.getDrawable(this, R.drawable.placeholder_loading_item)).into(comicPreviewImageView);
        comicPreviewImageView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void showCharacters(List<Character> characterList) {
        comicCharactersRecyclerView.setAdapter(new ComicCharacterAdapter(getApplicationContext(), this, characterList));
    }

    @Override
    public void setSeeAllCharactersVisible(boolean visible) {
        if(visible) comicCharacterSeeAllTextView.setVisibility(View.VISIBLE);
        else comicCharacterSeeAllTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void navigateToCharacterList(long comicId) {
        Intent intent = new Intent(this, CharacterListActivity.class);
        intent.putExtra(Configs.ARG_SELECTED_COMIC, comicId);
        startActivity(intent);
    }

    @Override
    public void onComicCharacterSelected(Character character) {

    }
}
