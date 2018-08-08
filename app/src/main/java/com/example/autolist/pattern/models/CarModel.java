package com.example.autolist.pattern.models;

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
public class CarModel {
    @Id(autoincrement = true)
    private Long id;
    private long manufacturerId;

    private String name;

    @ToOne(joinProperty = "manufacturerId")
    private Manufacturer manufacturer;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 718730586)
    private transient CarModelDao myDao;


    @Generated(hash = 1440831780)
    public CarModel(Long id, long manufacturerId, String name) {
        this.id = id;
        this.manufacturerId = manufacturerId;
        this.name = name;
    }
    @Generated(hash = 5810063)
    public CarModel() {
    }
    @Generated(hash = 754029993)
    private transient Long manufacturer__resolvedKey;


    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public long getManufacturerId() {
        return this.manufacturerId;
    }
    public void setManufacturerId(long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1762674612)
    public Manufacturer getManufacturer() {
        long __key = this.manufacturerId;
        if (manufacturer__resolvedKey == null
                || !manufacturer__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ManufacturerDao targetDao = daoSession.getManufacturerDao();
            Manufacturer manufacturerNew = targetDao.load(__key);
            synchronized (this) {
                manufacturer = manufacturerNew;
                manufacturer__resolvedKey = __key;
            }
        }
        return manufacturer;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1364689990)
    public void setManufacturer(@NotNull Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new DaoException(
                    "To-one property 'manufacturerId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.manufacturer = manufacturer;
            manufacturerId = manufacturer.getId();
            manufacturer__resolvedKey = manufacturerId;
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
    @Generated(hash = 739307208)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCarModelDao() : null;
    }
}
