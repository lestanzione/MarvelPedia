package com.empire.android.marvelpedia.characterlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.empire.android.marvelpedia.App;
import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.characterlist.adapter.CharacterListAdapter;
import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.main.adapter.MainOptionsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CharacterListActivity extends AppCompatActivity implements CharacterListContract.View, CharacterListAdapter.CharacterListener {

    public static final String TAG = CharacterListActivity.class.getSimpleName();

    @Inject
    CharacterListContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.characterPreviousPageButton)
    Button previousPageButton;

    @BindView(R.id.characterNextPageButton)
    Button nextPageButton;

    @BindView(R.id.characterPageTextView)
    TextView characterPageTextView;

    @BindView(R.id.charactersRecyclerView)
    RecyclerView charactersRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        setUpInjector();
        setUpUi();

        presenter.attachView(this);
        presenter.getCharacters();
    }

    private void setUpUi() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Characters");

        charactersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        characterPageTextView.setText(currentPage + " " + "of" + " " + totalPageNumber);
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
    public void showList(List<Character> characterList) {
        charactersRecyclerView.setAdapter(new CharacterListAdapter(getApplicationContext(), this, characterList));
    }

    @Override
    public void onCharacterSelected(int position) {

    }

}
