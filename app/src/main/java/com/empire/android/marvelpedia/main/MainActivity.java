package com.empire.android.marvelpedia.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.empire.android.marvelpedia.App;
import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.characterlist.CharacterListActivity;
import com.empire.android.marvelpedia.main.adapter.MainOptionsAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View, MainOptionsAdapter.MainOptionListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    MainContract.Presenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.mainOptionsRecyclerView)
    RecyclerView mainOptionsRecyclerView;

    private String[] mainOptionTexts = {
            "Characters"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUi();
        setUpInjector();
    }

    private void setUpUi() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MarvelPedia");

        mainOptionsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mainOptionsRecyclerView.setAdapter(new MainOptionsAdapter(this, mainOptionTexts));
    }

    private void setUpInjector() {
        ((App) getApplicationContext())
                .getApplicationComponent()
                .inject(this);

        presenter.attachView(this);
    }

    @Override
    public void onMainOptionSelected(int position) {
        Log.d(TAG, "onMainOptionSelected: " + mainOptionTexts[position]);

        Intent intent = null;

        switch (position){
            case 0:
                intent = new Intent(this, CharacterListActivity.class);
                break;
            default:
                intent = new Intent(this, CharacterListActivity.class);
                break;
        }

        startActivity(intent);
    }

}
