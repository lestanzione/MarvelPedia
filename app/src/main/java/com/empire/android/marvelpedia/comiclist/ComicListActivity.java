package com.empire.android.marvelpedia.comiclist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.empire.android.marvelpedia.App;
import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.comiclist.adapter.ComicListAdapter;
import com.empire.android.marvelpedia.data.Comic;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.empire.android.marvelpedia.Configs.ARG_SELECTED_CHARACTER;

public class ComicListActivity extends AppCompatActivity implements ComicListContract.View, ComicListAdapter.ComicListener {

    public static final String TAG = ComicListActivity.class.getSimpleName();

    @Inject
    ComicListContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.comicPreviousPageButton)
    Button previousPageButton;

    @BindView(R.id.comicNextPageButton)
    Button nextPageButton;

    @BindView(R.id.comicPageTextView)
    TextView comicPageTextView;

    @BindView(R.id.comicsRecyclerView)
    RecyclerView comicsRecyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_list);

        setUpInjector();
        setUpUi();

        long characterId = getIntent().getLongExtra(ARG_SELECTED_CHARACTER, 0);

        presenter.setCharacterId(characterId);
        presenter.getComics();
    }

    private void setUpUi() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comics");

        comicsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        previousPageButton.setOnClickListener(view -> presenter.previousPageButtonClicked());
        nextPageButton.setOnClickListener(view -> presenter.nextPageButtonClicked());
    }

    private void setUpInjector() {
        ((App) getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        presenter.attachView(this);
    }

    @Override
    public void setPagesText(int currentPage, int totalPageNumber) {
        comicPageTextView.setText(currentPage + " " + "of" + " " + totalPageNumber);
    }

    @Override
    public void setProgressBarVisible(boolean visible) {
        if(visible) progressBar.setVisibility(View.VISIBLE);
        else progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setPreviousButtonEnable(boolean enabled) {
        previousPageButton.setEnabled(enabled);
    }

    @Override
    public void setNextButtonEnable(boolean enabled) {
        nextPageButton.setEnabled(enabled);
    }

    @Override
    public void showList(List<Comic> comicList) {
        comicsRecyclerView.setAdapter(new ComicListAdapter(getApplicationContext(), this, comicList));
    }

    @Override
    public void onComicSelected(Comic comic) {

    }
}
