package com.mk.mkfighterresultdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mk.mkfighterresultdb.mvp.OpponentActivityContract;
import com.mk.mkfighterresultdb.mvp.OpponentPresenter;

public class OpponentActivity extends AppCompatActivity implements OpponentActivityContract.View {

    String TAG = this.getClass().getSimpleName();
    FighterDao fighterDao;

    public TypedArray fighterPhoto;

    OpponentPresenter presenter;

    long id = -1;

    private RecyclerView recyclerView;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent);
        initRecyclerView();
        fighterDao = AppDatabase.getDatabase(getApplicationContext()).fighterDao();
        presenter = new OpponentPresenter(new GetAllOpponents());
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent getIntent = getIntent();
        presenter.attachView(this);
        id = getIntent.getLongExtra("id", id);
        presenter.requestOpponentList(id, fighterDao);
        loading = ProgressDialog.show(this, getString(R.string.progressDialogTitle), getString(R.string.progressDialogMessage), false, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.destroy();
    }

    @Override
    public void onResponseSuccessRequestOpponentList() {
        loading.dismiss();
    }

    @Override
    public void onResponseFailureRequestOpponentList() {
        loading.dismiss();
        Toast.makeText(this, R.string.errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListToRecyclerView(Fighter[] fighters) {
        FighterRecyclerAdapter adapter = new FighterRecyclerAdapter(fighters, getPhotoFighterArray(), id, new RecyclerViewClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setAdapter(adapter);
    }

    private TypedArray getPhotoFighterArray(){
        fighterPhoto = getResources().obtainTypedArray(R.array.fighterImage);
        return fighterPhoto;
    }

    private void initRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.opponentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

}
