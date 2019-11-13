package com.example.eventmanagement;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagement.Pojo.Question;
import com.example.eventmanagement.Pojo.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QuestionAPI {
    String questionURL;
    String jsonData;
    String pageNumber;
    Question question;
    Activity mActivity;
    List<Question> questionList= new ArrayList<>();
    List<Question> question_list;
    private RecyclerView.Adapter questionAdapters;
    String bearerToken;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public QuestionAPI(String URL,String number ,Activity con, RecyclerView.Adapter qAdapter, List<Question> ques, String token) throws IOException {
        questionURL=URL;
        mActivity=con;
        pageNumber=number;
        question= new Question();
        questionAdapters=qAdapter;
        question_list=ques;
        bearerToken =token;
    }

    public void execute() {

        Log.d("sheetal", "hit the question execute"+pageNumber);
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder httpBuider = HttpUrl.parse(questionURL).newBuilder();
        httpBuider .addQueryParameter("pageNo",pageNumber);
        httpBuider .addQueryParameter("size","2");

        Request request = new Request.Builder()
                .url(httpBuider.build())
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
                            Question ques = new Question();
                            ques.setQuestionNo(prod.getString("questionNo"));
                            ques.setQuestion(prod.getString("question"));
                        /*    ques.setAnswer(prod.getString("answer"));*/
                            ques.setPageNumber(pageNumber);
                            questionList.add(ques);
                        }
                        Handler handler = new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message msg) {
                                // Any UI task, example
                                question_list.addAll(questionList);
                                questionAdapters.notifyDataSetChanged();
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
