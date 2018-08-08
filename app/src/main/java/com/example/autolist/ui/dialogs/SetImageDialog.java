package com.example.autolist.ui.dialogs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.autolist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.autolist.tools.Constants.ARG_IMAGE_URL;
import static com.example.autolist.tools.Constants.REQUEST_ADD;
import static com.example.autolist.tools.Constants.RESULT_ADDED;

/**
 * Created by Andrei on 08.08.2018.
 */

public class SetImageDialog extends DialogFragment {

    @BindView(R.id.url)
    EditText mUrl;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle(R.string.title_enter_url);
        View v = inflater.inflate(R.layout.dialog_set_image, container, false);
        ButterKnife.bind(this, v);
        if (getArguments() != null) {
            mUrl.setText(getArguments().getString(ARG_IMAGE_URL));
        }
        return v;
    }

    @OnClick({R.id.btn_save})
    public void save() {
        Intent intent = new Intent();
        intent.putExtra(ARG_IMAGE_URL, mUrl.getText().toString());
        getTargetFragment().onActivityResult(REQUEST_ADD, RESULT_ADDED, intent);
        dismiss();
    }
}
