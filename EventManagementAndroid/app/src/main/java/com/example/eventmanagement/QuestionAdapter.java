package com.example.eventmanagement;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagement.Pojo.Question;
import com.example.eventmanagement.Pojo.Team;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>{
    List<Question> questionList = new ArrayList<>();
    TeamSurvey mActivity;
    String answer;
    Team team;
    Integer setProgress=0;


    public QuestionAdapter(List<Question> questions, TeamSurvey con, Team t) {

        this.questionList = questions;
        this.mActivity=con;
        team =t;
    }


    @NonNull
    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_row,parent,false);
        QuestionAdapter.ViewHolder viewHolder = new QuestionAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final QuestionAdapter.ViewHolder holder, final int position) {
        final Question ques =questionList.get(position);
        Log.d("sheetal","question in adapter onbind view holder"+ques.getQuestionNo());
        holder.question.setText(ques.getQuestion());
        setRadioButton(holder,ques);

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                answer = ((RadioButton)group.findViewById(checkedId)).getText().toString();
                Log.d("sheetal", "answer of this"+answer);

                if (questionList.get(position).getAnswer()==null){
                    setProgress=setProgress+1;
                }
                questionList.get(position).setAnswer(answer);
                mActivity.progressBar.setProgress(setProgress);

            }
        });


        holder.setIsRecyclable(false);

    }

    private void setRadioButton(QuestionAdapter.ViewHolder holder,Question q) {
        if(team.getQuestion2()!=null && !team.getQuestion2().isEmpty() && q.getQuestionNo().equalsIgnoreCase("2")){
            if(holder.radioButton0.getText()==team.getQuestion2()){
                holder.radioGroup.check(holder.radioButton0.getId());
            }if(holder.radioButton1.getText()==team.getQuestion2()){
                holder.radioGroup.check(holder.radioButton1.getId());
            }if(holder.radioButton2.getText()==team.getQuestion2()){
                holder.radioGroup.check(holder.radioButton2.getId());
            }if(holder.radioButton3.getText()==team.getQuestion2()){
                holder.radioGroup.check(holder.radioButton3.getId());
            }if(holder.radioButton4.getText()==team.getQuestion2()){
                holder.radioGroup.check(holder.radioButton4.getId());
            }
            q.setAnswer(team.getQuestion2());
        }
        if(team.getQuestion3()!=null && !team.getQuestion3().isEmpty() && q.getQuestionNo().equalsIgnoreCase("3")){
            if(holder.radioButton0.getText()==team.getQuestion3()){
                holder.radioGroup.check(holder.radioButton0.getId());
            }if(holder.radioButton1.getText()==team.getQuestion3()){
                holder.radioGroup.check(holder.radioButton1.getId());
            }if(holder.radioButton2.getText()==team.getQuestion3()){
                holder.radioGroup.check(holder.radioButton2.getId());
            }if(holder.radioButton3.getText()==team.getQuestion3()){
                holder.radioGroup.check(holder.radioButton3.getId());
            }if(holder.radioButton4.getText()==team.getQuestion3()){
                holder.radioGroup.check(holder.radioButton4.getId());
            }
            q.setAnswer(team.getQuestion3());

        }
        if(team.getQuestion1()!=null && !team.getQuestion1().isEmpty() && q.getQuestionNo().equalsIgnoreCase("1")){
            if(holder.radioButton0.getText()==team.getQuestion1()){
                holder.radioGroup.check(holder.radioButton0.getId());
            }if(holder.radioButton1.getText()==team.getQuestion1()){
                holder.radioGroup.check(holder.radioButton1.getId());
            }if(holder.radioButton2.getText()==team.getQuestion1()){
                holder.radioGroup.check(holder.radioButton2.getId());
            }if(holder.radioButton3.getText()==team.getQuestion1()){
                holder.radioGroup.check(holder.radioButton3.getId());
            }if(holder.radioButton4.getText()==team.getQuestion1()){
                holder.radioGroup.check(holder.radioButton4.getId());
            }
            q.setAnswer(team.getQuestion1());
        }
        if(team.getQuestion4()!=null && !team.getQuestion4().isEmpty()  && q.getQuestionNo().equalsIgnoreCase("4")){
            if(holder.radioButton0.getText()==team.getQuestion4()){
                holder.radioGroup.check(holder.radioButton0.getId());
            }if(holder.radioButton1.getText()==team.getQuestion4()){
                holder.radioGroup.check(holder.radioButton1.getId());
            }if(holder.radioButton2.getText()==team.getQuestion4()){
                holder.radioGroup.check(holder.radioButton2.getId());
            }if(holder.radioButton3.getText()==team.getQuestion4()){
                holder.radioGroup.check(holder.radioButton3.getId());
            }if(holder.radioButton4.getText()==team.getQuestion4()){
                holder.radioGroup.check(holder.radioButton4.getId());
            }
            q.setAnswer(team.getQuestion4());
        }
        if(team.getQuestion5()!=null && !team.getQuestion5().isEmpty()  && q.getQuestionNo().equalsIgnoreCase("5")){
            if(holder.radioButton0.getText()==team.getQuestion5()){
                holder.radioGroup.check(holder.radioButton0.getId());
            }if(holder.radioButton1.getText()==team.getQuestion5()){
                holder.radioGroup.check(holder.radioButton1.getId());
            }if(holder.radioButton2.getText()==team.getQuestion5()){
                holder.radioGroup.check(holder.radioButton2.getId());
            }if(holder.radioButton3.getText()==team.getQuestion5()){
                holder.radioGroup.check(holder.radioButton3.getId());
            }if(holder.radioButton4.getText()==team.getQuestion5()){
                holder.radioGroup.check(holder.radioButton4.getId());
            }
            q.setAnswer(team.getQuestion5());
        }
        if(team.getQuestion6()!=null && !team.getQuestion6().isEmpty()&& q.getQuestionNo().equalsIgnoreCase("6")){
            if(holder.radioButton0.getText()==team.getQuestion6()){
                holder.radioGroup.check(holder.radioButton0.getId());
            }if(holder.radioButton1.getText()==team.getQuestion6()){
                holder.radioGroup.check(holder.radioButton1.getId());
            }if(holder.radioButton2.getText()==team.getQuestion6()){
                holder.radioGroup.check(holder.radioButton2.getId());
            }if(holder.radioButton3.getText()==team.getQuestion6()){
                holder.radioGroup.check(holder.radioButton3.getId());
            }if(holder.radioButton4.getText()==team.getQuestion6()){
                holder.radioGroup.check(holder.radioButton4.getId());
            }
            q.setAnswer(team.getQuestion6());
        }
        if(team.getQuestion7()!=null && !team.getQuestion7().isEmpty() && q.getQuestionNo().equalsIgnoreCase("7")){
            if(holder.radioButton0.getText()==team.getQuestion7()){
                holder.radioGroup.check(holder.radioButton0.getId());
            }if(holder.radioButton1.getText()==team.getQuestion7()){
                holder.radioGroup.check(holder.radioButton1.getId());
            }if(holder.radioButton2.getText()==team.getQuestion7()){
                holder.radioGroup.check(holder.radioButton2.getId());
            }if(holder.radioButton3.getText()==team.getQuestion7()){
                holder.radioGroup.check(holder.radioButton3.getId());
            }if(holder.radioButton4.getText()==team.getQuestion7()){
                holder.radioGroup.check(holder.radioButton4.getId());
            }
            q.setAnswer(team.getQuestion7());
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView question;
        RadioGroup radioGroup;
        RadioButton radioButton0,radioButton1,radioButton2,radioButton3,radioButton4;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question =itemView.findViewById(R.id.question1);
             radioGroup = itemView.findViewById(R.id.radioGroup);
             radioButton0 = itemView.findViewById(R.id.radioButton);
             radioButton1 = itemView.findViewById(R.id.radioButton2);
             radioButton2 = itemView.findViewById(R.id.radioButton3);
             radioButton3 = itemView.findViewById(R.id.radioButton4);
             radioButton4 = itemView.findViewById(R.id.radioButton5);
        }
    }
}
