package com.thephoenix_it.travelbooking.repositories;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import java.io.Serializable;

import io.realm.Realm;

/**
 * Created by root on 17/11/04.
 */

public class RealmFactory implements Serializable {
    private static RealmFactory instance;
    private transient static Realm realm = null;

    public RealmFactory(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmFactory with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmFactory(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmFactory with(Activity activity) {

        if (instance == null) {
            instance = new RealmFactory(activity.getApplication());
        }
        return instance;
    }

    public static RealmFactory with(Application application) {

        if (instance == null) {
            instance = new RealmFactory(application);
        }
        return instance;
    }

    public static RealmFactory getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }
}
