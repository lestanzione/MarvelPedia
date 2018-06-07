package com.empire.android.marvelpedia.character;

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
import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.character.adapter.CharacterComicAdapter;
import com.empire.android.marvelpedia.character.adapter.CharacterSerieAdapter;
import com.empire.android.marvelpedia.data.Comic;
import com.empire.android.marvelpedia.data.Serie;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.empire.android.marvelpedia.Configs.ARG_SELECTED_CHARACTER;

public class CharacterActivity extends AppCompatActivity implements CharacterContract.View, CharacterComicAdapter.CharacterComicListener, CharacterSerieAdapter.CharacterStoryListener {

    public static final String TAG = CharacterActivity.class.getSimpleName();

    @Inject
    CharacterContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.characterNameTextView)
    TextView characterNameTextView;

    @BindView(R.id.characterDescriptionTextView)
    TextView characterDescriptionTextView;

    @BindView(R.id.characterPreviewImageView)
    ImageView characterPreviewImageView;

    @BindView(R.id.characterComicsRecyclerView)
    RecyclerView characterComicsRecyclerView;

    @BindView(R.id.characterSeriesRecyclerView)
    RecyclerView characterSeriesRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        setUpInjector();
        setUpUi();

        long characterId = getIntent().getLongExtra(ARG_SELECTED_CHARACTER, 0);

        presenter.getCharacterInfo(characterId);
    }

    private void setUpUi() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Character");

        characterComicsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        characterSeriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
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
    public void setCharacterName(String name) {
        characterNameTextView.setText(name);
    }

    @Override
    public void setCharacterDescription(String description) {
        characterDescriptionTextView.setText(description);
    }

    @Override
    public void setCharacterImage(String imageUrl) {
        Picasso.with(this).load(imageUrl).into(characterPreviewImageView);
    }

    @Override
    public void showComics(List<Comic> comicList) {
        characterComicsRecyclerView.setAdapter(new CharacterComicAdapter(getApplicationContext(), this, comicList));
    }

    @Override
    public void showSeries(List<Serie> serieList) {
        characterSeriesRecyclerView.setAdapter(new CharacterSerieAdapter(getApplicationContext(), this, serieList));
    }

    @Override
    public void onCharacterComicSelected(Comic comic) {

    }

    @Override
    public void onCharacterStorySelected(Serie serie) {

    }
}
