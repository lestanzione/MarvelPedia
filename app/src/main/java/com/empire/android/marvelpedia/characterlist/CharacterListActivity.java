package com.empire.android.marvelpedia.characterlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.empire.android.marvelpedia.App;
import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.characterlist.adapter.CharacterListAdapter;
import com.empire.android.marvelpedia.data.Character;
import com.empire.android.marvelpedia.main.adapter.MainOptionsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListActivity extends AppCompatActivity implements CharacterListContract.View, CharacterListAdapter.CharacterListener {

    public static final String TAG = CharacterListActivity.class.getSimpleName();

    @Inject
    CharacterListContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.charactersRecyclerView)
    RecyclerView charactersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_list);

        setUpUi();
        setUpInjector();

        presenter.attachView(this);
        presenter.getCharacters();
    }

    private void setUpUi() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Characters");

        charactersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpInjector() {
        ((App) getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        presenter.attachView(this);
    }

    @Override
    public void showList(List<Character> characterList) {
        charactersRecyclerView.setAdapter(new CharacterListAdapter(this, characterList));
    }

    @Override
    public void onCharacterSelected(int position) {

    }
}
