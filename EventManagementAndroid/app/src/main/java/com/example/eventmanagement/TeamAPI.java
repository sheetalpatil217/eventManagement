package com.example.eventmanagement;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

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
import okhttp3.Response;

public class TeamAPI {
    String teamURL;
    String jsonData;
    Team team;
    Activity mActivity;
    List<Team> teamsList= new ArrayList<>();
    List<Team> team_list;
    private RecyclerView.Adapter teamAdapters;
    String bearerToken;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public TeamAPI(String URL, Activity con, RecyclerView.Adapter tAdapter, List<Team> teams, String token) throws IOException {
        teamURL=URL;
        mActivity=con;
        team= new Team();
        teamAdapters=tAdapter;
        team_list=teams;
        bearerToken =token;
    }

    public void execute() {

        Log.d("sheetal", "hit the team execute");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(teamURL)
                .get()
                .header("Authorization","Bearer "+bearerToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("sheetal", "call fail"+e.getMessage());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                jsonData = response.body().string();
                Log.d("sheetal", "jsondata" + jsonData);
                JSONObject jsonObject=null;
                try{
                    jsonObject = new JSONObject(jsonData);
                    // Log.d("sheetal",e.getMessage());
                    if (jsonObject.getString("status").equalsIgnoreCase("200")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        Log.d("sheetal","jsonArray"+ jsonArray);
                        for(int i =0; i<jsonArray.length();i++) {
                            JSONObject prod = jsonArray.getJSONObject(i);
                            Team teams = new Team();
                            teams.setTeamName(prod.getString("teamName"));
                            teams.setTotalScore(prod.getString("totalScore"));
                            teams.setAverageScore(prod.getString("avgScore"));
                            teams.setFlagStarted(Boolean.getBoolean(prod.getString("started")));
                            teams.setProgress(prod.getString("progress"));
                            teams.setQuestion1(prod.getString("Q1"));
                            teams.setQuestion2(prod.getString("Q2"));
                            teams.setQuestion3(prod.getString("Q3"));
                            teams.setQuestion4(prod.getString("Q4"));
                            teams.setQuestion5(prod.getString("Q5"));
                            teams.setQuestion6(prod.getString("Q6"));
                            teams.setQuestion7(prod.getString("Q7"));
                            teamsList.add(teams);

                        }
                        Handler handler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                // Any UI task, example
                                team_list.addAll(teamsList);
                                teamAdapters.notifyDataSetChanged();
                            }
                        };
                        handler.sendEmptyMessage(1);


                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }});
        //   return products;
    }

}
