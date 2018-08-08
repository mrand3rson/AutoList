package com.example.autolist.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.autolist.R;
import com.example.autolist.ui.fragments.AutoListFragment;

import static com.example.autolist.tools.Constants.ARG_NEW_ITEM_ID;
import static com.example.autolist.tools.Constants.RESULT_ADDED;

public class AutoActivity extends BaseActivity {

    private AutoListFragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragment = new AutoListFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mFragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(resultCode) {
            case RESULT_ADDED: {
                long itemId = data.getLongExtra(ARG_NEW_ITEM_ID, -1);
                if (itemId != -1) {
                    mFragment.addOrUpdateAuto(itemId);
                }
            }
        }
    }
}
