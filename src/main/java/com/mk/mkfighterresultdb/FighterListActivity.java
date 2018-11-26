package com.mk.mkfighterresultdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.mk.mkfighterresultdb.mvp.FighterActivityContract;
import com.mk.mkfighterresultdb.mvp.FighterActivityPresenter;
import com.mk.mkfighterresultdb.mvp.ModelGetAllFighters;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

public class FighterListActivity extends AppCompatActivity implements FighterActivityContract.View {

    String TAG = this.getClass().getSimpleName();
    FighterDao fighterDao;

    public TypedArray fighterPhoto;

    FighterRecyclerAdapter adapter;
    Fighter[] mFighters;

    FighterActivityPresenter presenter;

    private FastScrollRecyclerView recyclerView;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fighter_list);
        initRecyclerView();
        initToolbar();

        fighterDao = AppDatabase.getDatabase(getApplicationContext()).fighterDao();
        presenter = new FighterActivityPresenter(new ModelGetAllFighters());
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.attachView(this);
        if (getLastNonConfigurationInstance() != null) {
            mFighters = (Fighter[]) getLastCustomNonConfigurationInstance();
        }

        if (mFighters == null) {
            presenter.requestFighterList(fighterDao);
            loading = ProgressDialog.show(this, getString(R.string.progressDialogTitle), getString(R.string.progressDialogMessage), false, false);
        }
        else {
            setAdapter();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.destroy();
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return mFighters;
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
        mFighters = fighters;
        setAdapter();
    }

    private void initRecyclerView() {
        recyclerView = (FastScrollRecyclerView) findViewById(R.id.fighterList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private TypedArray getPhotoFighterArray(){
        fighterPhoto = getResources().obtainTypedArray(R.array.fighterImage);
        return fighterPhoto;
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.fighterListToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.titleFirstFighter);
        }
    }

    private void setAdapter(){
        adapter = new FighterRecyclerAdapter(mFighters, getPhotoFighterArray(), -1, new RecyclerViewClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent opponentId = new Intent(getApplicationContext(), OpponentActivity.class);
                opponentId.putExtra("id", mFighters[position].getId());
                startActivity(opponentId);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}