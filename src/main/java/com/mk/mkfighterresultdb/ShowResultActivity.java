package com.mk.mkfighterresultdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.mk.mkfighterresultdb.mvp.ShowResultActivityContract;
import com.mk.mkfighterresultdb.mvp.ShowResultPresenter;

import java.util.List;

public class ShowResultActivity extends AppCompatActivity implements ShowResultActivityContract.View{

    String TAG = this.getClass().getSimpleName();

    RecyclerView recyclerView;
    int firstId, secondId;

    FighterDao fighterDao;

    List<Result> resultList;

    ShowResultPresenter presenter;
    ResultRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        initToolbar();
        initRecyclerView();
        fighterDao = AppDatabase.getDatabase(getApplicationContext()).fighterDao();
        presenter = new ShowResultPresenter(new GetResult());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent getIntent = getIntent();
        firstId = getIntent.getIntExtra("firstFighterId", firstId);
        secondId = getIntent.getIntExtra("secondFighterId", secondId);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(String.valueOf(firstId) + " vs " + String.valueOf(secondId));
        presenter.attachView(this);
        presenter.requestViewData(fighterDao, firstId, secondId);
    }

    private void initToolbar(){
        Toolbar toolbar = findViewById(R.id.showResultToolbar);
        setSupportActionBar(toolbar);
    }

    public void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.resultList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void setListToRecyclerView(List<Result> results) {
        resultList = results;
    }

    @Override
    public void onResponseSuccessRequestFighterList() {
        adapter = new ResultRecyclerAdapter(resultList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResponseFailureRequestFighterList() {
        Log.d(TAG, "onResponseFailureRequestFighterList: error");

    }
}
