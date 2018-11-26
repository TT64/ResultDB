package com.mk.mkfighterresultdb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.mk.mkfighterresultdb.mvp.ModelOperateResult;
import com.mk.mkfighterresultdb.mvp.ShowResultActivityContract;
import com.mk.mkfighterresultdb.mvp.ShowResultPresenter;

import java.util.List;

public class ShowResultActivity extends AppCompatActivity implements ShowResultActivityContract.View {

    String TAG = this.getClass().getSimpleName();

    RecyclerView recyclerView;
    int firstId, secondId;
    String firstFighterTitle, secondFighterTitle;

    FighterDao fighterDao;

    AlertDialog.Builder confirmDialog;

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
        presenter = new ShowResultPresenter(new ModelOperateResult());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent getIntent = getIntent();
        firstId = getIntent.getIntExtra("firstFighterId", firstId);
        secondId = getIntent.getIntExtra("secondFighterId", secondId);
        presenter.attachView(this);
        presenter.requestFirstFighter(fighterDao, firstId);
        presenter.requestSecondFighter(fighterDao, secondId);
        presenter.requestViewData(fighterDao, firstId, secondId);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.destroy();
    }

    @Override
    public void setListToRecyclerView(List<Result> results) {
        resultList = results;
    }

    @Override
    public void onResponseSuccessRequestFighterList() {
        adapter = new ResultRecyclerAdapter(resultList);
        recyclerView.setAdapter(adapter);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(firstFighterTitle + " vs " + secondFighterTitle);
    }

    @Override
    public void onResponseSuccessRequestFirstFighter(List<Fighter> fighterList) {
        firstFighterTitle = fighterList.get(0).getName();
    }

    @Override
    public void onResponseSuccessRequestSecondFighter(List<Fighter> fighterList) {
        secondFighterTitle = fighterList.get(0).getName();
    }

    @Override
    public void onResponseErrorRequestFirstFighter() {
        firstFighterTitle = getString(R.string.firstFighterTitle);
    }

    @Override
    public void onResponseErrorRequestSecondFighter() {
        secondFighterTitle = getString(R.string.secondFighterTitle);
    }

    @Override
    public void onResponseFailureRequestFighterList() {
        Toast.makeText(this, R.string.errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseSuccessDeleteResult(int position) {
        adapter.notifyItemRemoved(position);
        Toast.makeText(this, R.string.successResultDelete, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponseFailureDeleteResult() {
        Toast.makeText(this, R.string.errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                changeResult(item.getGroupId());
                break;
            case 2:
                deleteResult(item.getGroupId());
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.showResultToolbar);
        setSupportActionBar(toolbar);
    }

    public void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.resultList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void deleteResult(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirmDialogTitle)
                .setNegativeButton(R.string.noBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.yesBtn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteResult(adapter.getItemId(position), position, fighterDao);
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    private void changeResult(int position) {
        Intent changeIntent = new Intent(getApplicationContext(), ChangeResultActivity.class);
        changeIntent.putExtra("id", resultList.get(position).getId());
        changeIntent.putExtra("firstFighterMatchWinnerValue", resultList.get(position).getFirstFighterMatchWinner());
        changeIntent.putExtra("secondFighterMatchWinnerValue", resultList.get(position).getSecondFighterMatchWinner());
        changeIntent.putExtra("firstRoundWinnerValue", resultList.get(position).getFirstRoundWinner());
        changeIntent.putExtra("secondRoundWinnerValue", resultList.get(position).getSecondRoundWinner());
        changeIntent.putExtra("fatalityValue", resultList.get(position).getFatality());
        changeIntent.putExtra("brutalityValue", resultList.get(position).getBrutality());
        changeIntent.putExtra("withoutSpecialFinishValue", resultList.get(position).getWithoutSpecialFinish());
        changeIntent.putExtra("scoreValue", resultList.get(position).getScore());
        changeIntent.putExtra("matchCourseValue", resultList.get(position).getMatchCourse());
        startActivity(changeIntent);
    }
}
