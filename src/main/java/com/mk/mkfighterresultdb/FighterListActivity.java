package com.mk.mkfighterresultdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.mk.mkfighterresultdb.mvp.FighterActivityContract;
import com.mk.mkfighterresultdb.mvp.FighterActivityPresenter;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.util.concurrent.Executor;

public class FighterListActivity extends AppCompatActivity implements FighterActivityContract.View {

    String TAG = this.getClass().getSimpleName();
    MyTaskExecutor executor = new MyTaskExecutor();
    FighterDao fighterDao;

    public TypedArray fighterPhoto;

    FighterActivityPresenter presenter;

    //private RecyclerView recyclerView;
    private FastScrollRecyclerView recyclerView;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fighter_list);
        initRecyclerView();
        fighterDao = AppDatabase.getDatabase(getApplicationContext()).fighterDao();
        presenter = new FighterActivityPresenter(new GetAllFighters());
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.attachView(this);
        presenter.requestFighterList(fighterDao);
        loading = ProgressDialog.show(this, getString(R.string.progressDialogTitle), getString(R.string.progressDialogMessage), false, false);
    }

    public void initRecyclerView() {
        //recyclerView = (RecyclerView) findViewById(R.id.fighterList);
        recyclerView = (FastScrollRecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.destroy();
    }

    @Override
    public void onResponseSuccessRequestFighterList() {
        loading.dismiss();
    }

    @Override
    public void onResponseFailureRequestFighterList() {
        loading.dismiss();
        Toast.makeText(this, R.string.errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListToRecyclerView(final Fighter[] fighters) {
        FighterRecyclerAdapter adapter = new FighterRecyclerAdapter(fighters, getPhotoFighterArray(), -1, new RecyclerViewClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent opponentId = new Intent(getApplicationContext(), OpponentActivity.class);
                opponentId.putExtra("id", fighters[position].getId());
                startActivity(opponentId);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private TypedArray getPhotoFighterArray(){
        fighterPhoto = getResources().obtainTypedArray(R.array.fighterImage);
        return fighterPhoto;
    }

    class MyTaskExecutor implements Executor {
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }
}