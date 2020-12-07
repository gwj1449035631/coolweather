package com.example.coolweather.util;

import android.text.TextUtils;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allprovinces=new JSONArray(response);
                for (int i=0;i<allprovinces.length();i++){
                    JSONObject provinceobject=allprovinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provinceobject.getString("name"));
                    province.setProvinceCode(provinceobject.getInt("id"));
                    province.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allcities=new JSONArray(response);
                for (int i=0;i<allcities.length();i++){
                    JSONObject cityobject=allcities.getJSONObject(i);
                    City city=new City();
                   city.setCityName(cityobject.getString("name"));
                   city.setCityCode(cityobject.getInt("id"));
                   city.setProvinceId(provinceId);
                   city.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    public static boolean handleCountyResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allcounties=new JSONArray(response);
                for (int i=0;i<allcounties.length();i++){
                    JSONObject countyobject=allcounties.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(countyobject.getString("name"));
                    county.setWeatherId(countyobject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    public static Weather handleWeatherResponse(String response){
        try {
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String weatherContent=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
