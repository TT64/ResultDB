package com.mk.mkfighterresultdb.mvp;

import android.text.TextUtils;

import com.mk.mkfighterresultdb.FighterDao;
import com.mk.mkfighterresultdb.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddResultPresenter extends BasePresenter<AddResultActivityContract.View> implements AddResultActivityContract.Presenter, AddResultActivityContract.Model.onFinishedListener {

    private AddResultActivityContract.Model mModel;

    public AddResultPresenter(AddResultActivityContract.Model model){
        this.mModel = model;
    }

    @Override
    public boolean checkNumField(String numValue, int orderNumEd) {
        boolean result = false;
        if (!TextUtils.isEmpty(numValue.trim())){
            if (isValidNumValue(numValue))
                result = true;
            else {
                result = false;
                getView().onCheckNumFieldFailure(orderNumEd);
            }
        }
        else {
            getView().onEmptyFiled();
        }
        return result;
    }

    @Override
    public boolean checkStringField(String stringValue) {
        boolean result = false;
        if (!TextUtils.isEmpty(stringValue.trim())){
            if (isValidStringValue(stringValue))
                result = true;
            else {
                result = false;
                getView().onCheckStringFieldFailure();
            }
        }
        else {
            getView().onEmptyFiled();
        }
        return result;
    }

    @Override
    public boolean checkDate(String dateValue) {
        boolean result;
        if (!TextUtils.isEmpty(dateValue))
            result = true;
        else {
            result = false;
            getView().onCheckDateFailure();
        }
        return result;
    }

    @Override
    public void addData(Result result, FighterDao fighterDao) {
        mModel.addDataToDb(result, fighterDao, this);
    }

    @Override
    public void onFinishAddDataResponse() {
        getView().onSuccessAddDataResponse();
    }

    @Override
    public void onFailureAddDataResponse() {
        getView().onFailureAddDataResponse();
    }

    public static boolean isValidNumValue(String value) {
        String patternName = "[0-9]+\\.*[0-9]*";
        Pattern p = Pattern.compile(patternName);
        Matcher matchValue = p.matcher(value.trim());
       // String[] words = value.split("\\s*(\\s|,|!|\\.)\\s*");
        String[] words = value.split("[^a-zA-ZА-Яа-я0-9]\\.");
        return words.length == 1 && matchValue.matches();
        //return matchValue.matches();
    }

    public static boolean isValidStringValue(String value) {
        return value.length() <= 20;
    }
}
