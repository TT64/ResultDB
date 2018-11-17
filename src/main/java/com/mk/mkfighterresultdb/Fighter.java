package com.mk.mkfighterresultdb;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

@Entity
public class Fighter {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String description;

    Fighter(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Fighter[] populateData() {
        Fighter[] list = new Fighter[33];
        String[] nameList = {Constants.HERO_NAME_1, Constants.HERO_NAME_2, Constants.HERO_NAME_3, Constants.HERO_NAME_4, Constants.HERO_NAME_5,
                Constants.HERO_NAME_6, Constants.HERO_NAME_7, Constants.HERO_NAME_8, Constants.HERO_NAME_9, Constants.HERO_NAME_10,
                Constants.HERO_NAME_11, Constants.HERO_NAME_12, Constants.HERO_NAME_13, Constants.HERO_NAME_14, Constants.HERO_NAME_15,
                Constants.HERO_NAME_16, Constants.HERO_NAME_17, Constants.HERO_NAME_18, Constants.HERO_NAME_19, Constants.HERO_NAME_20,
                Constants.HERO_NAME_21, Constants.HERO_NAME_22, Constants.HERO_NAME_23, Constants.HERO_NAME_24, Constants.HERO_NAME_25,
                Constants.HERO_NAME_26, Constants.HERO_NAME_27, Constants.HERO_NAME_28, Constants.HERO_NAME_29, Constants.HERO_NAME_30,
                Constants.HERO_NAME_31, Constants.HERO_NAME_32, Constants.HERO_NAME_33};
        String[] description = {
                Constants.HERO_DESC_1, Constants.HERO_DESC_2, Constants.HERO_DESC_3, Constants.HERO_DESC_4, Constants.HERO_DESC_5,
                Constants.HERO_DESC_6, Constants.HERO_DESC_7, Constants.HERO_DESC_8, Constants.HERO_DESC_9, Constants.HERO_DESC_10,
                Constants.HERO_DESC_11, Constants.HERO_DESC_12, Constants.HERO_DESC_13, Constants.HERO_DESC_14, Constants.HERO_DESC_15,
                Constants.HERO_DESC_16, Constants.HERO_DESC_17, Constants.HERO_DESC_18, Constants.HERO_DESC_19, Constants.HERO_DESC_20,
                Constants.HERO_DESC_21, Constants.HERO_DESC_22, Constants.HERO_DESC_23, Constants.HERO_DESC_24, Constants.HERO_DESC_25,
                Constants.HERO_DESC_26, Constants.HERO_DESC_27, Constants.HERO_DESC_28, Constants.HERO_DESC_29, Constants.HERO_DESC_30,
                Constants.HERO_DESC_31, Constants.HERO_DESC_32, Constants.HERO_DESC_33};

        for (int i = 0; i < nameList.length; i++) {
            list[i] = new Fighter(nameList[i], description[i]);
        }
        return list;
    }
}
