package com.empire.android.marvelpedia.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.empire.android.marvelpedia.R;
import com.empire.android.marvelpedia.main.adapter.MainOptionsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View, MainOptionsAdapter.MainOptionListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private MainContract.Presenter presenter;

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
        setUpPresenter();
    }

    private void setUpUi() {
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MarvelPedia");

        mainOptionsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mainOptionsRecyclerView.setAdapter(new MainOptionsAdapter(this, mainOptionTexts));
    }

    private void setUpPresenter() {
        presenter = new MainPresenter();
        presenter.attachView(this);
    }

    @Override
    public void onMainOptionSelected(int position) {
        Log.d(TAG, "onMainOptionSelected: " + mainOptionTexts[position]);
    }

}
