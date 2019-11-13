package com.example.eventmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagement.Pojo.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.google.gson.Gson;

public class UpdateTeamAPI {
    String rateURL;
    String jsonData;
    Team team;
    Activity mActivity;
    String bearerToken;
    String type;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public UpdateTeamAPI(String URL,Team t, Activity con,String token,String type) throws IOException {
        rateURL=URL;
        mActivity=con;
        team= t;
        bearerToken =token;
        this.type=type;
    }

    public void execute() {
        Gson gson = new Gson();
        String param = gson.toJson(team);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, param);

        Request request = new Request.Builder()
                .url(rateURL)
                .post(body)
                .header("Authorization","Bearer "+bearerToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                jsonData = response.body().string();
                Log.d("sheetal","jsondata"+jsonData);
                if(type.equalsIgnoreCase("Submit")){
                    try{
                        JSONObject jsonObject = new JSONObject(jsonData);
                        if (jsonObject.getString("status").equalsIgnoreCase("200")) {
                            Intent intent = new Intent(mActivity,TeamList.class);
                            mActivity.startActivity(intent);
                        }
                    }catch (Exception exception){
                        Log.d("sheetal",exception.getMessage());
                    }
                }

            }

        });
    }



}
