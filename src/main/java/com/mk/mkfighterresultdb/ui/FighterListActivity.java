package com.mk.mkfighterresultdb.ui;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.Toast;

import com.mk.mkfighterresultdb.App;
import com.mk.mkfighterresultdb.Constants;
import com.mk.mkfighterresultdb.R;
import com.mk.mkfighterresultdb.db.Fighter;
import com.mk.mkfighterresultdb.db.FighterDao;
import com.mk.mkfighterresultdb.di.DaggerFighterListActivityComponent;
import com.mk.mkfighterresultdb.mvp.FighterActivityContract;
import com.mk.mkfighterresultdb.mvp.FighterActivityPresenter;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import javax.inject.Inject;

public class FighterListActivity extends AppCompatActivity implements FighterActivityContract.View {

    String TAG = this.getClass().getSimpleName();

    @Inject
    public FighterDao fighterDao;

    @Inject
    FighterActivityPresenter presenter;

    private String searchQuery;

    private FighterRecyclerAdapter adapter;
    private Fighter[] mFighters;

    private FastScrollRecyclerView recyclerView;
    private ProgressDialog loading;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fighter_list);
        initRecyclerView();
        initToolbar();

        DaggerFighterListActivityComponent.builder()
                .appComponent(((App) getApplicationContext()).getAppComponent())
                .build().inject(this);

        if (savedInstanceState != null) {
            searchQuery = savedInstanceState.getString(Constants.KEY_QUERY);
        }

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
    public Object onRetainCustomNonConfigurationInstance() {
        return mFighters.clone();
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
        mFighters = fighters.clone();
        setAdapter();
    }

    private void initRecyclerView() {
        recyclerView = (FastScrollRecyclerView) findViewById(R.id.fighterList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private TypedArray getPhotoFighterArray() {
        return getResources().obtainTypedArray(R.array.fighterImage);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.fighterListToolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.titleFirstFighter);
        }
    }

    private void setAdapter() {
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