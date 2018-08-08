package com.example.autolist.ui.activities;

import android.os.Bundle;

import com.example.autolist.R;
import com.example.autolist.ui.fragments.AutoEditFragment;

import static com.example.autolist.tools.Constants.ARG_AUTO_ID;

public class AutoEditActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Long autoId = getIntent().getLongExtra(ARG_AUTO_ID, -1);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, AutoEditFragment.newInstance(autoId))
                .commit();
    }
}
