package com.mk.mkfighterresultdb;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mk.mkfighterresultdb.di.DaggerOpponentListActivityComponent;
import com.mk.mkfighterresultdb.mvp.OpponentActivityContract;
import com.mk.mkfighterresultdb.mvp.OpponentPresenter;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import javax.inject.Inject;

public class OpponentActivity extends AppCompatActivity implements OpponentActivityContract.View {

    String TAG = this.getClass().getSimpleName();

    @Inject
    FighterDao fighterDao;

    @Inject
    OpponentPresenter presenter;

    private TypedArray fighterPhoto;

    private FighterRecyclerAdapter adapter;

    private Fighter[] mOpponents;

    private int firstId = -1, secondId = -1;
    private String searchQuery;

    private FastScrollRecyclerView recyclerView;
    private ProgressDialog loading;
    private SearchView searchView;

    private AlertDialog chsDlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opponent);
        initRecyclerView();

        DaggerOpponentListActivityComponent.builder()
                .appComponent(((App) getApplicationContext()).getAppComponent())
                .build().inject(this);

        if (savedInstanceState != null) {
            searchQuery = savedInstanceState.getString(Constants.KEY_QUERY);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        initToolbar();
        Intent getIntent = getIntent();
        presenter.attachView(this);
        firstId = getIntent.getIntExtra("id", firstId);

        if (getLastNonConfigurationInstance() != null) {
            mOpponents = (Fighter[]) getLastCustomNonConfigurationInstance();
        }
        if (mOpponents == null) {
            presenter.requestOpponentList(firstId, fighterDao);
            loading = ProgressDialog.show(this, getString(R.string.progressDialogTitle), getString(R.string.progressDialogMessage), false, false);
        } else {
            setAdapter();
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
        return mOpponents.clone();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_widget, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });

        if (!TextUtils.isEmpty(searchQuery)) {
            searchView.setIconified(false);
            searchView.setQuery(searchQuery, false);
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!TextUtils.isEmpty(searchView.getQuery())) {
            outState.putString(Constants.KEY_QUERY, searchView.getQuery().toString());
        }
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
        mOpponents = fighters.clone();
        setAdapter();

    }

    private TypedArray getPhotoFighterArray() {
        fighterPhoto = getResources().obtainTypedArray(R.array.fighterImage);
        return fighterPhoto;
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.opponentListToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.titleSecondFighter);
        }
    }

    private void initRecyclerView() {
        recyclerView = (FastScrollRecyclerView) findViewById(R.id.opponentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initChooseDialog() {
        LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.choose_action_dialog, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                .setView(view);
        chsDlg = dialog.create();
        chsDlg.show();
    }

    public void addData(View view) {
        chsDlg.dismiss();
        Intent addData = new Intent(getApplicationContext(), AddResultActivity.class);
        prepareData(addData);
        startActivity(addData);
    }

    public void viewData(View view) {
        chsDlg.dismiss();
        Intent viewData = new Intent(getApplicationContext(), ShowResultActivity.class);
        prepareData(viewData);
        startActivity(viewData);
    }

    private void prepareData(Intent dataIntent) {
        dataIntent.putExtra("firstFighterId", firstId);
        dataIntent.putExtra("secondFighterId", secondId);
    }

    private void setAdapter() {
        adapter = new FighterRecyclerAdapter(mOpponents, getPhotoFighterArray(), firstId, new RecyclerViewClickListener() {
            @Override
            public void onItemClick(int position) {
                secondId = mOpponents[position].getId();
                initChooseDialog();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
