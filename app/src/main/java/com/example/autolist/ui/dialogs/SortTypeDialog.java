package com.example.autolist.ui.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.autolist.R;
import com.example.autolist.types.SortTypes;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.autolist.tools.Constants.ARG_SORT_TYPE;
import static com.example.autolist.tools.Constants.REQUEST_SORT_TYPE;
import static com.example.autolist.tools.Constants.RESULT_ADDED;

/**
 * Created by Andrei on 08.08.2018.
 */

public class SortTypeDialog extends DialogFragment {

    @BindView(R.id.lv_menu)
    ListView lv;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.title_sort_by);
        View v = inflater.inflate(R.layout.dialog_sort, container, false);
        ButterKnife.bind(this, v);
        lv.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                SortTypes.getAllSortNames(getActivity())));
        lv.setOnItemClickListener((adapterView, view, index, l) -> {
            Intent intent = new Intent();

            switch (index) {
                case 0: {
                    intent.putExtra(ARG_SORT_TYPE, SortTypes.SORT_BY_PRICE_ASC);
                    break;
                }
                case 1: {
                    intent.putExtra(ARG_SORT_TYPE, SortTypes.SORT_BY_PRICE_DESC);
                    break;
                }
                case 2: {
                    intent.putExtra(ARG_SORT_TYPE, SortTypes.SORT_BY_DATE_ASC);
                    break;
                }
                case 3: {
                    intent.putExtra(ARG_SORT_TYPE, SortTypes.SORT_BY_DATE_DESC);
                    break;
                }
            }

            getTargetFragment().onActivityResult(REQUEST_SORT_TYPE, RESULT_ADDED, intent);
            dismiss();
        });
        return v;
    }
}
