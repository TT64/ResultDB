package com.mk.mkfighterresultdb.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.mk.mkfighterresultdb.App;
import com.mk.mkfighterresultdb.R;
import com.mk.mkfighterresultdb.db.Fighter;
import com.mk.mkfighterresultdb.db.FighterDao;
import com.mk.mkfighterresultdb.db.Result;
import com.mk.mkfighterresultdb.di.DaggerShowResultActivityComponent;
import com.mk.mkfighterresultdb.mvp.ShowResultActivityContract;
import com.mk.mkfighterresultdb.mvp.ShowResultPresenter;

import java.util.List;

import javax.inject.Inject;

public class ShowResultActivity extends AppCompatActivity implements ShowResultActivityContract.View, RecyclerItemTouchHelper.RecyclerItemTouchListener {

    String TAG = this.getClass().getSimpleName();

    @Inject
    FighterDao fighterDao;

    @Inject
    ShowResultPresenter presenter;

    private int firstId, secondId, swipePosition = -1;
    private String firstFighterTitle, secondFighterTitle, restoreTitle, toRestoredSwipePositions = "";

    private List<Result> resultList;

    private RecyclerView recyclerView;
    private ResultRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        if (savedInstanceState != null) {
            swipePosition = savedInstanceState.getInt("swipePosition");
            toRestoredSwipePositions = savedInstanceState.getString("toRestoredSwipePositions");
            restoreTitle = savedInstanceState.getString("title");
        }

        initToolbar();
        initRecyclerView();
        initTouchHelper();

        DaggerShowResultActivityComponent.builder()
                .appComponent(((App) getApplicationContext()).getAppComponent())
                .build().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent getIntent = getIntent();
        firstId = getIntent.getIntExtra("firstFighterId", firstId);
        secondId = getIntent.getIntExtra("secondFighterId", secondId);
        presenter.attachView(this);

        if (getLastNonConfigurationInstance() != null) {
            resultList = (List<Result>) getLastCustomNonConfigurationInstance();
        }

        if (resultList == null) {
            presenter.requestFirstFighter(fighterDao, firstId);
            presenter.requestSecondFighter(fighterDao, secondId);
            presenter.requestViewData(fighterDao, firstId, secondId);
        } else {
            setAdapter();
            if (swipePosition != -1) {
                String[] swipedPosition = toRestoredSwipePositions.split(",");
                for (String swPos : swipedPosition) {
                    adapter.pendingRemoval(Integer.parseInt(swPos));
                }
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.destroy();
            presenter.unsubscribeSubs();
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return resultList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("swipePosition", TextUtils.isEmpty(toRestoredSwipePositions) ? -1 : swipePosition);
        outState.putString("toRestoredSwipePositions", toRestoredSwipePositions);
        outState.putString("title", getSupportActionBar().getTitle().toString());
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ResultRecyclerAdapter.ViewHolder) {
            adapter.pendingRemoval(viewHolder.getAdapterPosition());
            swipePosition = viewHolder.getAdapterPosition();
            toRestoredSwipePositions += String.valueOf(swipePosition) + ",";
        }
    }

    @Override
    public void setListToRecyclerView(List<Result> results) {
        resultList = results;
    }

    @Override
    public void onResponseSuccessRequestFighterList() {
        setAdapter();
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!TextUtils.isEmpty(restoreTitle))
            getSupportActionBar().setTitle(restoreTitle);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.resultList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
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
        changeIntent.putExtra("recordDate", resultList.get(position).getRecordDate());
        startActivity(changeIntent);
    }

    private void cancelDeleteResult(int position) {
        adapter.pendingCancelRemoval(position);
        String deleteString = "";
        deleteString = String.valueOf(position) + ",";
        toRestoredSwipePositions = toRestoredSwipePositions.replace(deleteString, "");
    }

    private void setAdapter() {
        adapter = new ResultRecyclerAdapter(resultList, new RecyclerViewButtonClick() {
            @Override
            public void onButtonClick(int position, int order) {
                if (order == 1)
                    presenter.deleteResult(adapter.getItemId(position), position, fighterDao);
                else if (order == 2)
                    changeResult(position);
                else
                    cancelDeleteResult(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
