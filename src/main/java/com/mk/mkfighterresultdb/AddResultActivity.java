package com.mk.mkfighterresultdb;

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

import com.mk.mkfighterresultdb.mvp.AddResultActivityContract;
import com.mk.mkfighterresultdb.mvp.AddResultPresenter;
import com.mk.mkfighterresultdb.mvp.ModelAddResult;

public class AddResultActivity extends AppCompatActivity implements AddResultActivityContract.View {

    String TAG = this.getClass().getSimpleName();

    private EditText firstFighterMatchWinner, secondFighterMatchWinner, firstRoundWinner, secondRoundWinner,
            fatality, brutality, withoutSpecialFinish, score, matchCourse;

    int firstId = -1, secondId = -1;
    String firstFighterMatchWinnerValue, secondFighterMatchWinnerValue, firstRoundWinnerValue, secondRoundWinnerValue,
            fatalityValue, brutalityValue, withoutSpecialFinishValue, scoreValue, matchCourseValue, recordDate, restoreTitle;
    private boolean isDateSelected = false;

    FighterDao fighterDao;
    AddResultPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_result);

        if (savedInstanceState != null) {
            isDateSelected = savedInstanceState.getBoolean("isDateSelected");
            restoreTitle = savedInstanceState.getString("title");
        }

        initViews();
        initToolbar();
        fighterDao = AppDatabase.getDatabase(getApplicationContext()).fighterDao();
        presenter = new AddResultPresenter(new ModelAddResult());

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!isDateSelected) {
            initDatePicker();
            isDateSelected = true;
        }
        presenter.attachView(this);
        Intent getIntent = getIntent();
        firstId = getIntent.getIntExtra("firstFighterId", firstId);
        secondId = getIntent.getIntExtra("secondFighterId", secondId);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (presenter != null)
            presenter.destroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initDatePicker();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isDateSelected", isDateSelected);
        outState.putString("title", getSupportActionBar().getTitle().toString());
    }

    @Override
    public void onSuccessAddDataResponse() {
        Toast.makeText(this, R.string.successDataAdd, Toast.LENGTH_SHORT).show();
        Intent back = new Intent(this, FighterListActivity.class);
        back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        back.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(back);
    }

    @Override
    public void onFailureAddDataResponse() {
        Toast.makeText(this, R.string.errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEmptyFiled() {
        if (firstFighterMatchWinnerValue.isEmpty()) {
            firstFighterMatchWinner.setError(getString(R.string.emptyFieldError));
            firstFighterMatchWinner.requestFocus();
        }
        if (secondFighterMatchWinnerValue.isEmpty()) {
            secondFighterMatchWinner.setError(getString(R.string.emptyFieldError));
            secondFighterMatchWinner.requestFocus();
        }
        if (firstRoundWinnerValue.isEmpty()) {
            firstRoundWinner.setError(getString(R.string.emptyFieldError));
            firstRoundWinner.requestFocus();
        }
        if (secondRoundWinnerValue.isEmpty()) {
            secondRoundWinner.setError(getString(R.string.emptyFieldError));
            secondRoundWinner.requestFocus();
        }
        if (fatalityValue.isEmpty()) {
            fatality.setError(getString(R.string.emptyFieldError));
            fatality.requestFocus();
        }
        if (brutalityValue.isEmpty()) {
            brutality.setError(getString(R.string.emptyFieldError));
            brutality.requestFocus();
        }
        if (withoutSpecialFinishValue.isEmpty()) {
            withoutSpecialFinish.setError(getString(R.string.emptyFieldError));
            withoutSpecialFinish.requestFocus();
        }
        if (scoreValue.isEmpty()) {
            score.setError(getString(R.string.emptyFieldError));
            score.requestFocus();
        }
        if (matchCourseValue.isEmpty()) {
            matchCourse.setError(getString(R.string.emptyFieldError));
            matchCourse.requestFocus();
        }
    }

    @Override
    public void onCheckNumFieldFailure(int orderNumEd) {
        switch (orderNumEd) {
            case 1:
                firstFighterMatchWinner.setError(getString(R.string.numFieldCheckError));
                firstFighterMatchWinner.requestFocus();
                break;
            case 2:
                secondFighterMatchWinner.setError(getString(R.string.numFieldCheckError));
                secondFighterMatchWinner.requestFocus();
                break;
            case 3:
                firstRoundWinner.setError(getString(R.string.numFieldCheckError));
                firstRoundWinner.requestFocus();
                break;
            case 4:
                secondRoundWinner.setError(getString(R.string.numFieldCheckError));
                secondRoundWinner.requestFocus();
                break;
            case 5:
                fatality.setError(getString(R.string.numFieldCheckError));
                fatality.requestFocus();
                break;
            case 6:
                brutality.setError(getString(R.string.numFieldCheckError));
                brutality.requestFocus();
                break;
            case 7:
                withoutSpecialFinish.setError(getString(R.string.numFieldCheckError));
                withoutSpecialFinish.requestFocus();
                break;
            case 8:
                score.setError(getString(R.string.numFieldCheckError));
                score.requestFocus();
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckStringFieldFailure() {
        matchCourse.setError(getString(R.string.stringFieldCheckError));
        matchCourse.requestFocus();
    }

    @Override
    public void onCheckDateFailure() {
        Toast.makeText(this, R.string.dateCheckError, Toast.LENGTH_SHORT).show();
    }

    public void addResult(View view) {

        prepareValues();

        if (presenter.checkNumField(firstFighterMatchWinnerValue, 1) && presenter.checkNumField(secondFighterMatchWinnerValue, 2)
                && presenter.checkNumField(firstRoundWinnerValue, 3) && presenter.checkNumField(secondRoundWinnerValue, 4)
                && presenter.checkNumField(fatalityValue, 5) && presenter.checkNumField(brutalityValue, 6)
                && presenter.checkNumField(withoutSpecialFinishValue, 7) && presenter.checkNumField(scoreValue, 8)
                && presenter.checkStringField(matchCourseValue) && presenter.checkDate(recordDate)) {
            Result result = new Result(firstId, secondId, Double.parseDouble(firstFighterMatchWinnerValue), Double.parseDouble(secondFighterMatchWinnerValue),
                    Double.parseDouble(firstRoundWinnerValue), Double.parseDouble(secondRoundWinnerValue), Double.parseDouble(fatalityValue), Double.parseDouble(brutalityValue),
                    Double.parseDouble(withoutSpecialFinishValue), Double.parseDouble(scoreValue), matchCourseValue, recordDate);
            presenter.addData(result, fighterDao);
        }
    }

    private void initViews() {
        firstFighterMatchWinner = (EditText) findViewById(R.id.winMatchFirstFighterChgEd);
        secondFighterMatchWinner = (EditText) findViewById(R.id.winMatchSecondFighterChgEd);
        firstRoundWinner = (EditText) findViewById(R.id.winFirstRoundChgEd);
        secondRoundWinner = (EditText) findViewById(R.id.winSecondRoundChgEd);
        fatality = (EditText) findViewById(R.id.fatalityChgEd);
        brutality = (EditText) findViewById(R.id.brutalityChgEd);
        withoutSpecialFinish = (EditText) findViewById(R.id.withoutSpecFinChgEd);
        score = (EditText) findViewById(R.id.scoreChgEd);
        matchCourse = (EditText) findViewById(R.id.matchCourseChgEd);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.addDataToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            if (!TextUtils.isEmpty(restoreTitle))
                getSupportActionBar().setTitle(restoreTitle);
            else
                getSupportActionBar().setTitle(R.string.addData);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initDatePicker(){

        DialogFragment dateFragment = new DatePickerDialogFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.MODE, Constants.ADD_RESULT);
        dateFragment.setArguments(args);
        dateFragment.show(getSupportFragmentManager(), "Date");

    }

    private void prepareValues() {
        firstFighterMatchWinnerValue = firstFighterMatchWinner.getText().toString();
        secondFighterMatchWinnerValue = secondFighterMatchWinner.getText().toString();
        firstRoundWinnerValue = firstRoundWinner.getText().toString();
        secondRoundWinnerValue = secondRoundWinner.getText().toString();
        fatalityValue = fatality.getText().toString();
        brutalityValue = brutality.getText().toString();
        withoutSpecialFinishValue = withoutSpecialFinish.getText().toString();
        scoreValue = score.getText().toString();
        matchCourseValue = matchCourse.getText().toString();
    }

    public void getDate(String date) {
        recordDate = date;
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.dateTitle) + " " + recordDate);
        }
    }

    public void changeDate(MenuItem item) {
        initDatePicker();
    }
}