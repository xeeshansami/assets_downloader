package com.hbl.assetsmanager.realmDB;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hbl.assetsmanager.network.models.response.AssetsData;
import com.hbl.assetsmanager.network.models.response.AssetsDataModel;
import com.hbl.assetsmanager.network.models.response.AssetsResponse;
import com.hbl.assetsmanager.network.models.response.AssetsResponseModel;
import com.hbl.assetsmanager.utils.GlobalClass;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


public class RealmController<T> {
    private static RealmController instance;
    private final Realm realm;
    GlobalClass context;

    public static void resetObject() {
        instance = null;
    }

    public RealmController() {
        this.context = new GlobalClass();
        realm = Realm.getDefaultInstance();
    }


    public static RealmController getInstance() {
        if (instance == null) {
            instance = new RealmController();
        }
        return instance;
    }

    public Realm getRealm() {
        Realm realm = Realm.getDefaultInstance();
        return realm;
    }

    public void refresh() {
        Realm realm = Realm.getDefaultInstance();
        realm.refresh();
    }

    public void clearAll() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    public void setAssets(AssetsData data, String name) {
        String json = new Gson().toJson(data);
        Type listType = new TypeToken<AssetsDataModel>() {
        }.getType();
        AssetsDataModel dta = new Gson().fromJson(json, listType);
        Realm realm = Realm.getDefaultInstance();
        AssetsDataModel userObj = realm.where(AssetsDataModel.class).equalTo("name", name).findFirst();
        if (userObj != null) {
            Log.i("Progress","Update: Old "+userObj.getName()+" "+userObj.getUpdatedDate()+" Update: New "+data.getName()+" "+data.getUpdatedDate());
            realm.beginTransaction();
            userObj.setId(data.getId());
            userObj.setName(data.getName());
            userObj.setPath(data.getPath());
            userObj.setUpdatedDate(data.getUpdatedDate());
            realm.commitTransaction();

        } else {
            Log.i("Progress","Inserted New");
            realm.beginTransaction();
            realm.insertOrUpdate(dta);
            realm.commitTransaction();
        }
    }

    public ArrayList<AssetsDataModel> getAssets() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<AssetsDataModel> userObj1 = new ArrayList<>();
        userObj1.addAll(realm.copyFromRealm(realm.where(AssetsDataModel.class).findAll()));
        if (userObj1.size() != 0) {
            return userObj1;
        } else {
            return new ArrayList<AssetsDataModel>() {
            };
        }
    }
}

