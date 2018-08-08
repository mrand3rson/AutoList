package com.example.autolist.pattern.models;

import com.example.autolist.types.TypeCoupe;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by Andrei on 06.08.2018.
 */
@Entity
public class Automobile {
    @Id(autoincrement = true)
    private Long id;
    private long modelId;

    private int price;
    private int year;
    private String imageUrl;

    //in mm
    private int length;
    private int width;
    private int height;

    private transient TypeCoupe typeCoupe;
    private int typeCoupeInt;
    private int weight; //in kg
    private int power; //in hp
    private int fuelTankVolume; //in liters
    private int engineDisplacement; //in cm3
    @ToOne(joinProperty = "modelId")
    private CarModel model;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1031163396)
    private transient AutomobileDao myDao;
    @Generated(hash = 1577731681)
    private transient Long model__resolvedKey;

    public int getPrice() {
        return price;
    }
    public int getYear() {
        return year;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    public int getLength() {
        return length;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    public void updateTypeCoupe() {
        for (TypeCoupe type: TypeCoupe.values()) {
            if (type.value() == typeCoupeInt) {
                typeCoupe = type;
                break;
            }
        }
    }
    public TypeCoupe getTypeCoupe() {
        updateTypeCoupe();
        return typeCoupe;
    }
    public int getWeight() {
        return weight;
    }
    public int getPower() {
        return power;
    }
    public int getFuelTankVolume() {
        return fuelTankVolume;
    }
    public int getEngineDisplacement() {
        return engineDisplacement;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public long getModelId() {
        return this.modelId;
    }
    public void setModelId(long modelId) {
        this.modelId = modelId;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getTypeCoupeInt() {
        return this.typeCoupeInt;
    }
    public void setTypeCoupeInt(int typeCoupeInt) {
        this.typeCoupeInt = typeCoupeInt;
        updateTypeCoupe();
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    public void setPower(int power) {
        this.power = power;
    }
    public void setFuelTankVolume(int fuelTankVolume) {
        this.fuelTankVolume = fuelTankVolume;
    }
    public void setEngineDisplacement(int engineDisplacement) {
        this.engineDisplacement = engineDisplacement;
    }

    @Override
    public String toString() {
        return String.format("%1s %2s", getModel().getManufacturer().getName(), getModel().getName());
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 19827635)
    public CarModel getModel() {
        long __key = this.modelId;
        if (model__resolvedKey == null || !model__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CarModelDao targetDao = daoSession.getCarModelDao();
            CarModel modelNew = targetDao.load(__key);
            synchronized (this) {
                model = modelNew;
                model__resolvedKey = __key;
            }
        }
        return model;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1823236788)
    public void setModel(@NotNull CarModel model) {
        if (model == null) {
            throw new DaoException(
                    "To-one property 'modelId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.model = model;
            modelId = model.getId();
            model__resolvedKey = modelId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1875162733)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAutomobileDao() : null;
    }

    public Automobile(long id,
                      CarModel model,
                      int price,
                      int year,
                      String imageUrl,
                      int length,
                      int width,
                      int height,
                      TypeCoupe typeCoupe,
                      int weight,
                      int power,
                      int fuelTankVolume,
                      int engineDisplacement) {
        this.id = id;
        this.model = model;
        this.price = price;
        this.year = year;
        this.imageUrl = imageUrl;
        this.length = length;
        this.width = width;
        this.height = height;
        this.typeCoupe = typeCoupe;
        this.weight = weight;
        this.power = power;
        this.fuelTankVolume = fuelTankVolume;
        this.engineDisplacement = engineDisplacement;
    }
    @Generated(hash = 443392026)
    public Automobile(Long id, long modelId, int price, int year, String imageUrl, int length,
            int width, int height, int typeCoupeInt, int weight, int power, int fuelTankVolume,
            int engineDisplacement) {
        this.id = id;
        this.modelId = modelId;
        this.price = price;
        this.year = year;
        this.imageUrl = imageUrl;
        this.length = length;
        this.width = width;
        this.height = height;
        this.typeCoupeInt = typeCoupeInt;
        this.weight = weight;
        this.power = power;
        this.fuelTankVolume = fuelTankVolume;
        this.engineDisplacement = engineDisplacement;
    }
    @Generated(hash = 1421477843)
    public Automobile() {
    }
}