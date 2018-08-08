package com.example.autolist.pattern.presenters;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.autolist.App;
import com.example.autolist.pattern.models.Automobile;
import com.example.autolist.pattern.models.CarModel;
import com.example.autolist.pattern.models.CarModelDao;
import com.example.autolist.pattern.models.Manufacturer;
import com.example.autolist.pattern.models.ManufacturerDao;
import com.example.autolist.pattern.views.AutoEdit;

/**
 * Created by Andrei on 08.08.2018.
 */
@InjectViewState
public class AutoEditPresenter extends MvpPresenter<AutoEdit> {
    public Long addManufacturerOrUpdate(String name) {
        Manufacturer manufacturer = App.getDaoSession().getManufacturerDao().queryBuilder()
                .where(ManufacturerDao.Properties.Name.eq(name))
                .unique();

        if (manufacturer != null) {
            manufacturer.setName(name);
        } else {
            manufacturer =
                    new Manufacturer(null, name);
        }
        App.getDaoSession().getManufacturerDao().insertOrReplace(manufacturer);
        return App.getDaoSession().getManufacturerDao().getKey(manufacturer);
    }
    public Long addModelOrUpdate(long manufacturerId, String name) {
        CarModel model = App.getDaoSession().getCarModelDao().queryBuilder()
                .where(CarModelDao.Properties.Name.eq(name))
                .unique();

        if (model != null) {
            model.setName(name);
        } else {
            model = new CarModel(null, manufacturerId, name);
        }
        App.getDaoSession().getCarModelDao().insertOrReplace(model);
        return App.getDaoSession().getCarModelDao().getKey(model);
    }
    public Long addAutoOrUpdate(Automobile mAuto, Long modelId, int price, int year, String imageUrl,
                                 int typeInt, int power, int engineDisplacement, int fuelTankVolume,
                                 int length, int width, int height, int weight) {
        if (mAuto != null) {
            mAuto.setModelId(modelId);
            mAuto.setPrice(price);
            mAuto.setYear(year);
            mAuto.setTypeCoupeInt(typeInt);
            mAuto.setPower(power);
            mAuto.setEngineDisplacement(engineDisplacement);
            mAuto.setFuelTankVolume(fuelTankVolume);
            mAuto.setLength(length);
            mAuto.setWidth(width);
            mAuto.setHeight(height);
            mAuto.setWeight(weight);
            mAuto.setImageUrl(imageUrl);
        } else {
            mAuto = new Automobile(null, modelId, price, year, imageUrl,
                    length, width, height, typeInt, weight, power, fuelTankVolume, engineDisplacement);
        }
        Long itemId = App.getDaoSession().getAutomobileDao().insertOrReplace(mAuto);

        getViewState().onComplete(itemId);
        return App.getDaoSession().getAutomobileDao().getKey(mAuto);
    }
}
