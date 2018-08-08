package com.example.autolist.ui.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.autolist.App;
import com.example.autolist.R;
import com.example.autolist.pattern.models.Automobile;
import com.example.autolist.pattern.models.AutomobileDao;
import com.example.autolist.pattern.models.CarModel;
import com.example.autolist.pattern.presenters.AutoEditPresenter;
import com.example.autolist.pattern.views.AutoEdit;
import com.example.autolist.types.TypeCoupe;
import com.example.autolist.ui.dialogs.SetImageDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.autolist.tools.Constants.ARG_AUTO_ID;
import static com.example.autolist.tools.Constants.ARG_IMAGE_URL;
import static com.example.autolist.tools.Constants.ARG_NEW_ITEM_ID;
import static com.example.autolist.tools.Constants.REQUEST_ADD;
import static com.example.autolist.tools.Constants.RESULT_ADDED;


public class AutoEditFragment extends MvpAppCompatFragment implements AutoEdit {

    @BindView(R.id.manufacturer)
    EditText mManufacturerView;
    @BindView(R.id.model)
    EditText mModelView;
    @BindView(R.id.price)
    EditText mPriceView;
    @BindView(R.id.year)
    EditText mYearView;

    @BindView(R.id.image)
    ImageView mImage;

    @BindView(R.id.type_coupe)
    Spinner mTypeView;
    @BindView(R.id.power)
    EditText mPowerView;
    @BindView(R.id.fuel_tank_volume)
    EditText mFuelTankView;
    @BindView(R.id.engine_displacement)
    EditText mEngineView;

    @BindView(R.id.length)
    EditText mLengthView;
    @BindView(R.id.width)
    EditText mWidthView;
    @BindView(R.id.height)
    EditText mHeightView;
    @BindView(R.id.weight)
    EditText mWeightView;

    @InjectPresenter
    AutoEditPresenter mPresenter;

    private Automobile mAuto;
    private Long mAutoId;
    private String imageUrl;


    public AutoEditFragment() {

    }

    public static AutoEditFragment newInstance(Long autoId) {
        Bundle args = new Bundle();
        args.putLong(ARG_AUTO_ID, autoId);
        AutoEditFragment fragment = new AutoEditFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAutoId = getArguments().getLong(ARG_AUTO_ID, -1);
        if (mAutoId != -1) {
            mAuto = App.getDaoSession().getAutomobileDao().queryBuilder()
                    .where(AutomobileDao.Properties.Id.eq(mAutoId))
                    .unique();
        } else {
            mAutoId = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_auto_edit, container, false);
        ButterKnife.bind(this, v);

        initSpinner(mTypeView, TypeCoupe.values());
        if (mAutoId != null) {
            CarModel model = mAuto.getModel();
            mManufacturerView.setText(model.getManufacturer().getName());
            mModelView.setText(model.getName());
            mPriceView.setText(String.valueOf(mAuto.getPrice()));
            mYearView.setText(String.valueOf(mAuto.getYear()));

            mTypeView.setSelection(mAuto.getTypeCoupeInt()-1);
            mPowerView.setText(String.valueOf(mAuto.getPower()));
            mFuelTankView.setText(String.valueOf(mAuto.getFuelTankVolume()));
            mEngineView.setText(String.valueOf(mAuto.getEngineDisplacement()));

            mLengthView.setText(String.valueOf(mAuto.getLength()));
            mWidthView.setText(String.valueOf(mAuto.getWidth()));
            mHeightView.setText(String.valueOf(mAuto.getHeight()));
            mWeightView.setText(String.valueOf(mAuto.getWeight()));

            String url = mAuto.getImageUrl();
            if (url != null) {
                Picasso.with(getActivity())
                        .load(url)
                        .into(mImage);
            }
        }
        mImage.setOnClickListener(view -> {
            SetImageDialog dialog = new SetImageDialog();
            if (mAuto != null) {
                Bundle args = new Bundle();
                args.putString(ARG_IMAGE_URL, mAuto.getImageUrl());
                dialog.setArguments(args);
            }
            dialog.setTargetFragment(this, REQUEST_ADD);
            dialog.show(getFragmentManager(), null);
        });
        return v;
    }

    private void initSpinner(Spinner view, TypeCoupe[] types) {
        List<String> stringTypes = new ArrayList<>(types.length);
        for (TypeCoupe type: types) {
            stringTypes.add(type.name());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, stringTypes);
        view.setAdapter(adapter);
    }

    @OnClick({R.id.btn_save})
    public void save() {
        if (isInputValid()) {
            Long manufacturerId = mPresenter.addManufacturerOrUpdate(mManufacturerView.getText().toString());
            Long modelId = mPresenter.addModelOrUpdate(manufacturerId, mModelView.getText().toString());

            int price = Integer.valueOf(mPriceView.getText().toString());
            int year = Integer.valueOf(mYearView.getText().toString());
            int typeInt = mTypeView.getSelectedItemPosition()+1;
            int power = Integer.valueOf(mPowerView.getText().toString());
            int fuelTankVolume = Integer.valueOf(mFuelTankView.getText().toString());
            int engineDisplacement = Integer.valueOf(mEngineView.getText().toString());
            int length = Integer.valueOf(mLengthView.getText().toString());
            int width = Integer.valueOf(mWidthView.getText().toString());
            int height = Integer.valueOf(mHeightView.getText().toString());
            int weight = Integer.valueOf(mWeightView.getText().toString());


            mPresenter.addAutoOrUpdate(mAuto, modelId, price, year, imageUrl,
                    typeInt, power, engineDisplacement, fuelTankVolume,
                    length, width, height, weight);
        } else {
            Toast.makeText(getActivity(), R.string.warning_wrong_value, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isInputValid() {
        if (mManufacturerView.getText().toString().equals(""))
            return false;
        if (mModelView.getText().toString().equals(""))
            return false;

        if (mTypeView.getSelectedItem() == null
                || mTypeView.getSelectedItem().equals(""))
            return false;
        if (mPowerView.getText().toString().equals(""))
            return false;
        if (mEngineView.getText().toString().equals(""))
            return false;
        if (mFuelTankView.getText().toString().equals(""))
            return false;

        if (mLengthView.getText().toString().equals(""))
            return false;
        if (mWidthView.getText().toString().equals(""))
            return false;
        if (mHeightView.getText().toString().equals(""))
            return false;
        if (mWeightView.getText().toString().equals(""))
            return false;

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            imageUrl = data.getStringExtra(ARG_IMAGE_URL);

            Picasso.with(getActivity())
                    .load(imageUrl)
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            mImage.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                            imageUrl = null;
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });
        }
    }

    @Override
    public void onComplete(Long itemId) {
        Toast.makeText(getActivity(), R.string.string_saved, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(ARG_NEW_ITEM_ID, itemId);
        getActivity().setResult(RESULT_ADDED, intent);
        getActivity().finish();
    }
}
