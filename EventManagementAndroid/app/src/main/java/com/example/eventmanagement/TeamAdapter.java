package com.example.eventmanagement;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanagement.Pojo.Team;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ViewHolder> {

    List<Team> teams = new ArrayList<>();
    TeamList mActivity;
    String token;

    public TeamAdapter(List<Team> teams,TeamList con,String t) {

        this.teams = teams;
        this.mActivity=con;
        token=t;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Team team =teams.get(position);
        Log.d("test","team in adapter onbind view holder"+team);
        holder.teamName.setText(team.getTeamName());
        holder.totalScore.setText(team.getTotalScore());
        holder.averageScore.setText(team.getAverageScore());
        Log.d("test","average score"+team.getAverageScore());
       /* if(team.getAverageScore()!=null || !team.getAverageScore().isEmpty()) {
            Log.d("test","average score inside if");
            holder.startedButton.setText("View");
            holder.startedButton.setVisibility(View.INVISIBLE);
        }else{
            holder.startedButton.setVisibility(View.VISIBLE);
        }*/
        /*if(team.getFlagStarted()==true){
            holder.startedButton.setVisibility(View.INVISIBLE);
        }*/

        holder.startedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,TeamSurvey.class);
                intent.putExtra("TEAM_NAME",team.getTeamName());
                intent.putExtra("BUTTON_TXT",holder.startedButton.getText());
                intent.putExtra("TOKEN",token);
                mActivity.startActivity(intent);
            }
        });

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView teamName, averageScore,totalScore;
        Button startedButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamName =itemView.findViewById(R.id.teamName);
            totalScore = itemView.findViewById(R.id.teamTotal);
            averageScore = itemView.findViewById(R.id.teamAverage);
            startedButton = itemView.findViewById(R.id.startButton);

        }
    }
}
