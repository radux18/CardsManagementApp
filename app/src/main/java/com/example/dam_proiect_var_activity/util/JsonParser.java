package com.example.dam_proiect_var_activity.util;

import com.example.dam_proiect_var_activity.model.Card;
import com.example.dam_proiect_var_activity.model.MainOffice;
import com.example.dam_proiect_var_activity.model.Manager;
import com.example.dam_proiect_var_activity.model.PartnerBank;
import com.example.dam_proiect_var_activity.model.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static List<PartnerBank> fromJson(String json){
        if(json == null || json.isEmpty()){
            return new ArrayList<>();
        }
        try {
            JSONArray array = new JSONArray(json);
            return getBanksFromJson(array);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<PartnerBank> getBanksFromJson(JSONArray array) throws JSONException {
        List<PartnerBank> results1 = new ArrayList<>();
        for(int i=0; i< array.length(); i++){
            JSONObject object = array.getJSONObject(i);
            PartnerBank bank = getBankFromJson(object);
            results1.add(bank);
        }
        return results1;
    }

    private static PartnerBank getBankFromJson(JSONObject object) throws JSONException {
        String bankName = object.getString("bankName");
        int nrEmployee = object.getInt("nrEmployee");
        JSONObject office = object.getJSONObject("mainOffice");
        String origin = office.getString("origin");
        double netIncome = office.getDouble("netIncome");
        JSONObject bankManager = office.getJSONObject("bankManager");
        String name = bankManager.getString("name");
        int age = bankManager.getInt("age");
        int netWorth = bankManager.getInt("netWorth");

        return new PartnerBank(bankName,nrEmployee
                ,new MainOffice(origin,netIncome
                ,new Manager(name,age,netWorth)));
    }
}
