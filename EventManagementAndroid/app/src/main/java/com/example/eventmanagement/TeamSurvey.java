package com.example.eventmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventmanagement.Pojo.Question;
import com.example.eventmanagement.Pojo.Team;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamSurvey extends AppCompatActivity {
    private RecyclerView questionsRecyclerView;
    private RecyclerView.Adapter questionAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List<Question> questionList = new ArrayList<>();
    Team team = new Team();
    String questionsURL = "http://localhost:3000/questions";
    String rateURL="http://localhost:3000/rate";
    String submitURL="http://localhost:3000/submit";
    static String token;
    TeamSurvey tcontext;
    ProgressBar progressBar;
    Button previousButton;
    Button nextButton;
    String ViewQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_survey);
        setTitle("Survey for "+this.getIntent().getStringExtra("TEAM_NAME"));
        team.setTeamName(this.getIntent().getStringExtra("TEAM_NAME"));
        Log.d("test","get intent button result"+this.getIntent().getStringExtra("BUTTON_TXT"));
        ViewQuestions=this.getIntent().getStringExtra("BUTTON_TXT");
        token=this.getIntent().getStringExtra("TOKEN");


        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(7);
        progressBar.setMin(0);
        previousButton = findViewById(R.id.previousButton);
        previousButton.setVisibility(View.INVISIBLE);
        nextButton = findViewById(R.id.nextButton);

        questionsRecyclerView = findViewById(R.id.recyclerView);
        questionsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        questionsRecyclerView.setLayoutManager(layoutManager);
        questionAdapter = new QuestionAdapter(questionList,this,team);
        questionsRecyclerView.setAdapter(questionAdapter);
        questionAdapter.notifyDataSetChanged();




        tcontext=this;
        try {
            new QuestionAPI(questionsURL,"1",this,questionAdapter,questionList,token).execute();
            //   prodAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }


        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String condition="2";
                Log.d("test","size"+questionList.size());
                if(questionList.size()==2){
                    if(questionList.get(0).getAnswer()==null || questionList.get(1).getAnswer()==null){
                       condition=null;
                    }
                }else{
                    condition=questionList.get(0).getAnswer();
                }
                if(condition==null){
                    Toast.makeText(getApplicationContext(),"Please Select Answer to go to next page",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("test","page number"+questionList.get(0).getPageNumber());
                    Integer pageN=Integer.parseInt(questionList.get(0).getPageNumber())-1;
                    Log.d("test","page number -1"+pageN);

                    if(pageN==1){
                        previousButton.setVisibility(View.INVISIBLE);
                    }
                    for (Question q:questionList){
                        Log.d("sheetal","questionno"+q.getQuestionNo()+""+ q.getAnswer());

                        if(q.getQuestionNo().equalsIgnoreCase("1")){
                            Log.d("sheetal","inside first if"+q.getAnswer());
                            team.setQuestion1(q.getAnswer());
                        }
                        if(q.getQuestionNo().equalsIgnoreCase("2")){
                            team.setQuestion2(q.getAnswer());
                        }
                        if(q.getQuestionNo().equalsIgnoreCase("3")){
                            team.setQuestion3(q.getAnswer());
                        }
                        if(q.getQuestionNo().equalsIgnoreCase("4")){
                            team.setQuestion4(q.getAnswer());
                        }
                        if(q.getQuestionNo().equalsIgnoreCase("5")){
                            team.setQuestion5(q.getAnswer());
                        }
                        if(q.getQuestionNo().equalsIgnoreCase("6")){
                            team.setQuestion6(q.getAnswer());
                        }
                        if(q.getQuestionNo().equalsIgnoreCase("7")){
                            team.setQuestion7(q.getAnswer());
                        }

                    }


                    questionList.clear();
                    if(pageN!=4){
                        nextButton.setText("Next");
                    }

                    Log.d("sheetal","team details"+team.toString());
                    try {
                        new UpdateTeamAPI(rateURL,team,tcontext,token,"Next").execute();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                    try {
                        new QuestionAPI(questionsURL,String.valueOf(pageN),tcontext,questionAdapter,questionList,token).execute();
                        //   prodAdapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String condition="2";
                Log.d("test","size"+questionList.size());
                if(questionList.size()==2){
                    if(questionList.get(0).getAnswer()==null || questionList.get(1).getAnswer()==null){
                        condition=null;
                    }
                }else{
                    condition=questionList.get(0).getAnswer();
                }
                if(condition==null){

                    Toast.makeText(getApplicationContext(),"Please Select Answer to go to next page",Toast.LENGTH_SHORT).show();
                }else{
                    previousButton.setVisibility(View.VISIBLE);
                    if(nextButton.getText()=="Submit"){
                        for (Question q:questionList){

                            if(q.getQuestionNo().equalsIgnoreCase("7")){
                                team.setQuestion7(q.getAnswer());
                            }

                            try {
                                new UpdateTeamAPI(submitURL,team,tcontext,token,"Submit").execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }else{
                        for (Question q:questionList){
                            Log.d("sheetal","questionno"+q.getQuestionNo()+""+ q.getAnswer());

                            if(q.getQuestionNo().equalsIgnoreCase("1")){
                                Log.d("sheetal","inside first if"+q.getAnswer());
                                team.setQuestion1(q.getAnswer());
                            }
                            if(q.getQuestionNo().equalsIgnoreCase("2")){
                                team.setQuestion2(q.getAnswer());
                            }
                            if(q.getQuestionNo().equalsIgnoreCase("3")){
                                team.setQuestion3(q.getAnswer());
                            }
                            if(q.getQuestionNo().equalsIgnoreCase("4")){
                                team.setQuestion4(q.getAnswer());
                            }
                            if(q.getQuestionNo().equalsIgnoreCase("5")){
                                team.setQuestion5(q.getAnswer());
                            }
                            if(q.getQuestionNo().equalsIgnoreCase("6")){
                                team.setQuestion6(q.getAnswer());
                            }
                            if(q.getQuestionNo().equalsIgnoreCase("7")){
                                team.setQuestion7(q.getAnswer());
                            }

                        }

                        Log.d("sheetal","next button text"+nextButton.getText());
                        Log.d("sheetal","Page number on current click"+questionList.get(0).getPageNumber());
                        Integer pageN=Integer.parseInt(questionList.get(0).getPageNumber())+1;
                        Log.d("sheetal","Page number on next click"+pageN);

                        questionList.clear();
                        if(pageN<4){
                            try {
                                new QuestionAPI(questionsURL,String.valueOf(pageN),tcontext,questionAdapter,questionList,token).execute();
                                //   prodAdapter.notifyDataSetChanged();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else if(pageN==4){
                            try {
                                new QuestionAPI(questionsURL,String.valueOf(pageN),tcontext,questionAdapter,questionList,token).execute();
                                //   prodAdapter.notifyDataSetChanged();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            nextButton.setText("Submit");
                        }
                        Log.d("sheetal","team details"+team.toString());
                        try {
                            new UpdateTeamAPI(rateURL,team,tcontext,token,"Next").execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

            }
        });
    }
}
