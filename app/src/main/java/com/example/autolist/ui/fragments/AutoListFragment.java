package com.example.autolist.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.autolist.App;
import com.example.autolist.R;
import com.example.autolist.pattern.models.Automobile;
import com.example.autolist.types.SortTypes;
import com.example.autolist.ui.activities.AutoEditActivity;
import com.example.autolist.ui.adapters.AutoListAdapter;
import com.example.autolist.ui.dialogs.SortTypeDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.autolist.tools.Constants.ARG_SORT_TYPE;
import static com.example.autolist.tools.Constants.REQUEST_ADD;
import static com.example.autolist.tools.Constants.REQUEST_SORT_TYPE;
import static com.example.autolist.tools.Constants.RESULT_ADDED;
import static com.example.autolist.types.SortTypes.SORT_BY_DATE_ASC;


public class AutoListFragment extends Fragment {

    @BindView(R.id.btn_remove_filter)
    View mBtnRemoveFilter;

    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    private AutoListAdapter mAdapter;
    private List<Automobile> mAutos;


    public AutoListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_auto_list, container, false);
        ButterKnife.bind(this, v);

        setHasOptionsMenu(true);
        initRecycler();
        mBtnRemoveFilter.setOnClickListener(view -> {
            view.setVisibility(View.INVISIBLE);
            mAdapter.replaceAll(mAutos);
        });
        return v;
    }

    private void initRecycler() {
        mAutos = App.getDaoSession().getAutomobileDao().loadAll();
        mAdapter = new AutoListAdapter(getActivity(), mRecycler, mAutos);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.setItemViewCacheSize(25);
        mRecycler.setAdapter(mAdapter);
    }

    @OnClick(R.id.fab_add)
    public void newAuto() {
        Intent intent = new Intent(getActivity(), AutoEditActivity.class);
        startActivityForResult(intent, REQUEST_ADD);
    }

    public void addOrUpdateAuto(long id) {
        Automobile newAuto = App.getDaoSession().getAutomobileDao().loadByRowId(id);
        newAuto.updateTypeCoupe();
        List<Automobile> data = mAdapter.getData();
        boolean updated = false;

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getId() == id) {
                data.set(i, newAuto);
                mAdapter.notifyItemChanged(i);
                updated = true;
                break;
            }
        }

        if (!updated) {
            int index = data.size();
            data.add(newAuto);
            mAdapter.notifyItemInserted(index);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.action_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = searchItem.getActionView().findViewById(R.id.action_search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                final List<Automobile> filteredAutoList = filter(mAdapter.getData(), query);
                mAdapter.replaceAll(filteredAutoList);
                mBtnRemoveFilter.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

            private List<Automobile> filter(List<Automobile> data, String query) {
                final String lowerCaseQuery = query.toLowerCase();

                final List<Automobile> filteredModelList = new ArrayList<>();
                for (Automobile auto : data) {
                    final String manufacturerName = auto.getModel().getManufacturer().getName().toLowerCase();
                    final String modelName = auto.getModel().getName().toLowerCase();
                    if (modelName.contains(lowerCaseQuery) || manufacturerName.contains(lowerCaseQuery)) {
                        filteredModelList.add(auto);
                    }
                }
                return filteredModelList;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort: {
                SortTypeDialog dialog = new SortTypeDialog();
                dialog.setTargetFragment(this, REQUEST_SORT_TYPE);
                dialog.show(getFragmentManager(), null);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_SORT_TYPE: {
                switch (resultCode) {
                    case RESULT_ADDED: {
                        if (data != null) {
                            int selectedSortType = data.getIntExtra(ARG_SORT_TYPE, SORT_BY_DATE_ASC);

                            List<Automobile> autos = mAdapter.getData();
                            Collections.sort(autos, SortTypes.getComparator(selectedSortType));
                            mAdapter.replaceAll(autos);
                        }
                        break;
                    }
                }
                break;
            }
        }
    }
}