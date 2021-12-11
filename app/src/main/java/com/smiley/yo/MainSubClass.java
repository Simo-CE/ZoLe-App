package com.smiley.yo;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainSubClass extends MainActivity{
    //public void onc

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        //Initializing realm
        Realm.init(this);
        //Setting default realm configuration
        RealmConfiguration config = new RealmConfiguration.Builder()
                //.name("default-realm")
                .name("zole.db")
                .allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                /*.deleteRealmIfMigrationNeeded()
                .schemaVersion(0)*/
                .compactOnLaunch()
                .inMemory()
                .build();
        // set this config as the default realm
        Realm.setDefaultConfiguration(config);
    }
}
