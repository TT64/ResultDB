package com.mk.mkfighterresultdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mk.mkfighterresultdb.mvp.ModelGetAllOpponents;
import com.mk.mkfighterresultdb.mvp.OpponentActivityContract;
import com.mk.mkfighterresultdb.mvp.OpponentPresenter;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

public class OpponentActivity extends AppCompatActivity implements OpponentActivityContract.View {

    String TAG = this.getClass().getSimpleName();
    FighterDao fighterDao;

    public TypedArray fighterPhoto;

    OpponentPresenter presenter;

    int firstId = -1, secondId = -1;

    private FastScrollRecyclerView recyclerView;
    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent);
        initRecyclerView();
        fighterDao = AppDatabase.getDatabase(getApplicationContext()).fighterDao();
        presenter = new OpponentPresenter(new ModelGetAllOpponents());
    }

    @Override
    protected void onStart() {
        super.onStart();

        initToolbar();
        Intent getIntent = getIntent();
        presenter.attachView(this);
        firstId = getIntent.getIntExtra("id", firstId);
        presenter.requestOpponentList(firstId, fighterDao);
        loading = ProgressDialog.show(this, getString(R.string.progressDialogTitle), getString(R.string.progressDialogMessage), false, false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.destroy();
    }

    @Override
    public void onSuccessRequestOpponentListResponse() {
        loading.dismiss();
    }

    @Override
    public void onFailureRequestOpponentListResponse() {
        loading.dismiss();
        Toast.makeText(this, R.string.errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListToRecyclerView(final Fighter[] fighters) {
        FighterRecyclerAdapter adapter = new FighterRecyclerAdapter(fighters, getPhotoFighterArray(), firstId, new RecyclerViewClickListener() {
            @Override
            public void onItemClick(int position) {
                secondId = fighters[position].getId();
                initChooseDialog();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private TypedArray getPhotoFighterArray(){
        fighterPhoto = getResources().obtainTypedArray(R.array.fighterImage);
        return fighterPhoto;
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.opponentListToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.titleSecondFighter);
        }
    }

    private void initRecyclerView(){
        recyclerView = (FastScrollRecyclerView) findViewById(R.id.opponentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initChooseDialog(){
        LinearLayout view = (LinearLayout)getLayoutInflater().inflate(R.layout.choose_action_dialog, null);
        AlertDialog.Builder chsDlg = new AlertDialog.Builder(this)
                .setView(view);
        chsDlg.create();
        chsDlg.show();
    }

    public void addData(View view) {
        Intent addData = new Intent(getApplicationContext(), AddResultActivity.class);
        prepareData(addData);
        startActivity(addData);
    }

    public void viewData(View view) {
        Intent viewData = new Intent(getApplicationContext(), ShowResultActivity.class);
        prepareData(viewData);
        startActivity(viewData);
    }

    private void prepareData(Intent dataIntent){
        dataIntent.putExtra("firstFighterId", firstId);
        dataIntent.putExtra("secondFighterId", secondId);
    }
}
