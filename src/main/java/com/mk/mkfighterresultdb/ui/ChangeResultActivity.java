package com.mk.mkfighterresultdb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mk.mkfighterresultdb.App;
import com.mk.mkfighterresultdb.Constants;
import com.mk.mkfighterresultdb.R;
import com.mk.mkfighterresultdb.db.FighterDao;
import com.mk.mkfighterresultdb.di.DaggerChangeResultActivityComponent;
import com.mk.mkfighterresultdb.mvp.ChangeResultContract;
import com.mk.mkfighterresultdb.mvp.ChangeResultPresenter;

import javax.inject.Inject;

public class ChangeResultActivity extends AppCompatActivity implements ChangeResultContract.View {

    @Inject
    FighterDao fighterDao;

    @Inject
    ChangeResultPresenter presenter;

    private EditText winMatchFirstFighterChgEd, winMatchSecondFighterChgEd, winFirstRoundChgEd, winSecondRoundChgEd,
            fatalityChgEd, brutalityChgEd, withoutSpecFinChgEd, scoreChgEd, matchCourseChgEd;

    private Intent getChangeData;

    private String firstFighterMatchWinnerChgValue, secondFighterMatchWinnerChgValue, firstRoundWinnerChgValue, secondRoundWinnerChgValue,
            fatalityChgValue, brutalityChgValue, withoutSpecialFinishChgValue, scoreChgValue, matchCourseChgValue, recordDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_result);

        if (savedInstanceState != null) {
            recordDate = savedInstanceState.getString("title");
        }

        initViews();
        initToolbar();

        DaggerChangeResultActivityComponent.builder()
                .appComponent(((App) getApplicationContext()).getAppComponent())
                .build().inject(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.attachView(this);

        getChangeData = getIntent();
        winMatchFirstFighterChgEd.setText(String.valueOf(getChangeData.getDoubleExtra("firstFighterMatchWinnerValue", 0)));
        winMatchSecondFighterChgEd.setText(String.valueOf(getChangeData.getDoubleExtra("secondFighterMatchWinnerValue", 0)));
        winFirstRoundChgEd.setText(String.valueOf(getChangeData.getDoubleExtra("firstRoundWinnerValue", 0)));
        winSecondRoundChgEd.setText(String.valueOf(getChangeData.getDoubleExtra("secondRoundWinnerValue", 0)));
        fatalityChgEd.setText(String.valueOf(getChangeData.getDoubleExtra("fatalityValue", 0)));
        brutalityChgEd.setText(String.valueOf(getChangeData.getDoubleExtra("brutalityValue", 0)));
        withoutSpecFinChgEd.setText(String.valueOf(getChangeData.getDoubleExtra("withoutSpecialFinishValue", 0)));
        scoreChgEd.setText(String.valueOf(getChangeData.getDoubleExtra("scoreValue", 0)));
        matchCourseChgEd.setText(String.valueOf(getChangeData.getStringExtra("matchCourseValue")));
        if (TextUtils.isEmpty(recordDate)) {
            recordDate = getChangeData.getStringExtra("recordDate");
            getSupportActionBar().setTitle(getString(R.string.dateTitle) + " " + getChangeData.getStringExtra("recordDate"));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.destroy();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", recordDate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onEmptyFiled() {
        if (firstFighterMatchWinnerChgValue.isEmpty()) {
            winMatchFirstFighterChgEd.setError(getString(R.string.emptyFieldError));
            winMatchFirstFighterChgEd.requestFocus();
        }
        if (secondFighterMatchWinnerChgValue.isEmpty()) {
            winMatchSecondFighterChgEd.setError(getString(R.string.emptyFieldError));
            winMatchSecondFighterChgEd.requestFocus();
        }
        if (firstRoundWinnerChgValue.isEmpty()) {
            winFirstRoundChgEd.setError(getString(R.string.emptyFieldError));
            winFirstRoundChgEd.requestFocus();
        }
        if (secondRoundWinnerChgValue.isEmpty()) {
            winSecondRoundChgEd.setError(getString(R.string.emptyFieldError));
            winSecondRoundChgEd.requestFocus();
        }
        if (fatalityChgValue.isEmpty()) {
            fatalityChgEd.setError(getString(R.string.emptyFieldError));
            fatalityChgEd.requestFocus();
        }
        if (brutalityChgValue.isEmpty()) {
            brutalityChgEd.setError(getString(R.string.emptyFieldError));
            brutalityChgEd.requestFocus();
        }
        if (withoutSpecialFinishChgValue.isEmpty()) {
            withoutSpecFinChgEd.setError(getString(R.string.emptyFieldError));
            withoutSpecFinChgEd.requestFocus();
        }
        if (scoreChgValue.isEmpty()) {
            scoreChgEd.setError(getString(R.string.emptyFieldError));
            scoreChgEd.requestFocus();
        }
        if (matchCourseChgValue.isEmpty()) {
            matchCourseChgEd.setError(getString(R.string.emptyFieldError));
            matchCourseChgEd.requestFocus();
        }
    }

    @Override
    public void onCheckNumFieldFailure(int orderNumEd) {
        switch (orderNumEd) {
            case 1:
                winMatchFirstFighterChgEd.setError(getString(R.string.numFieldCheckError));
                winMatchFirstFighterChgEd.requestFocus();
                break;
            case 2:
                winMatchSecondFighterChgEd.setError(getString(R.string.numFieldCheckError));
                winMatchSecondFighterChgEd.requestFocus();
                break;
            case 3:
                winFirstRoundChgEd.setError(getString(R.string.numFieldCheckError));
                winFirstRoundChgEd.requestFocus();
                break;
            case 4:
                winSecondRoundChgEd.setError(getString(R.string.numFieldCheckError));
                winSecondRoundChgEd.requestFocus();
                break;
            case 5:
                fatalityChgEd.setError(getString(R.string.numFieldCheckError));
                fatalityChgEd.requestFocus();
                break;
            case 6:
                brutalityChgEd.setError(getString(R.string.numFieldCheckError));
                brutalityChgEd.requestFocus();
                break;
            case 7:
                withoutSpecFinChgEd.setError(getString(R.string.numFieldCheckError));
                withoutSpecFinChgEd.requestFocus();
                break;
            case 8:
                scoreChgEd.setError(getString(R.string.numFieldCheckError));
                scoreChgEd.requestFocus();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckStringFieldFailure() {
        matchCourseChgEd.setError(getString(R.string.stringFieldCheckError));
        matchCourseChgEd.requestFocus();
    }

    @Override
    public void onCheckDateFailure() {
        Toast.makeText(this, R.string.dateCheckError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessChangeResult() {
        Toast.makeText(this, R.string.successChangeResult, Toast.LENGTH_SHORT).show();
        Intent back = new Intent(this, FighterListActivity.class);
        back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        back.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(back);
    }

    @Override
    public void onErrorChangeResult() {
        Toast.makeText(this, R.string.errorMessage, Toast.LENGTH_SHORT).show();
    }

    public void chgResult(View view) {

        prepareValues();

        if (presenter.checkNumField(firstFighterMatchWinnerChgValue, 1) && presenter.checkNumField(secondFighterMatchWinnerChgValue, 2)
                && presenter.checkNumField(firstRoundWinnerChgValue, 3) && presenter.checkNumField(secondRoundWinnerChgValue, 4)
                && presenter.checkNumField(fatalityChgValue, 5) && presenter.checkNumField(brutalityChgValue, 6)
                && presenter.checkNumField(withoutSpecialFinishChgValue, 7) && presenter.checkNumField(scoreChgValue, 8)
                && presenter.checkStringField(matchCourseChgValue) && presenter.checkDate(recordDate)) {
            presenter.requestChangeResult(fighterDao, getChangeData.getLongExtra("id", -1), Double.parseDouble(firstFighterMatchWinnerChgValue), Double.parseDouble(secondFighterMatchWinnerChgValue),
                    Double.parseDouble(firstRoundWinnerChgValue), Double.parseDouble(secondRoundWinnerChgValue), Double.parseDouble(fatalityChgValue),
                    Double.parseDouble(brutalityChgValue), Double.parseDouble(withoutSpecialFinishChgValue), Double.parseDouble(scoreChgValue), matchCourseChgValue, recordDate);
        }
    }

    private void initViews() {
        winMatchFirstFighterChgEd = (EditText) findViewById(R.id.winMatchFirstFighterChgEd);
        winMatchSecondFighterChgEd = (EditText) findViewById(R.id.winMatchSecondFighterChgEd);
        winFirstRoundChgEd = (EditText) findViewById(R.id.winFirstRoundChgEd);
        winSecondRoundChgEd = (EditText) findViewById(R.id.winSecondRoundChgEd);
        fatalityChgEd = (EditText) findViewById(R.id.fatalityChgEd);
        brutalityChgEd = (EditText) findViewById(R.id.brutalityChgEd);
        withoutSpecFinChgEd = (EditText) findViewById(R.id.withoutSpecFinChgEd);
        scoreChgEd = (EditText) findViewById(R.id.scoreChgEd);
        matchCourseChgEd = (EditText) findViewById(R.id.matchCourseChgEd);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.chgDataToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            if (!TextUtils.isEmpty(recordDate))
                getSupportActionBar().setTitle(getString(R.string.dateTitle) + " " + recordDate);
            else
                getSupportActionBar().setTitle(R.string.chgData);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void prepareValues() {
        firstFighterMatchWinnerChgValue = winMatchFirstFighterChgEd.getText().toString();
        secondFighterMatchWinnerChgValue = winMatchSecondFighterChgEd.getText().toString();
        firstRoundWinnerChgValue = winFirstRoundChgEd.getText().toString();
        secondRoundWinnerChgValue = winSecondRoundChgEd.getText().toString();
        fatalityChgValue = fatalityChgEd.getText().toString();
        brutalityChgValue = brutalityChgEd.getText().toString();
        withoutSpecialFinishChgValue = withoutSpecFinChgEd.getText().toString();
        scoreChgValue = scoreChgEd.getText().toString();
        matchCourseChgValue = matchCourseChgEd.getText().toString();
    }

    public void getChangeDate(String date) {
        recordDate = date;

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.dateTitle) + " " + recordDate);
        }

    }

    public void changeDate(MenuItem item) {
        DialogFragment dateFragment = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.MODE, Constants.CHANGE_RESULT);
        dateFragment.setArguments(args);
        dateFragment.show(this.getSupportFragmentManager(), "Date");
    }
}
