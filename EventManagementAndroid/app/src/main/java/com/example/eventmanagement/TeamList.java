package com.example.eventmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.example.eventmanagement.Pojo.Team;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeamList extends AppCompatActivity {

    private RecyclerView teamRecyclerView;
    private RecyclerView.Adapter teamAdapter;
    private RecyclerView.LayoutManager layoutManager;
    List <Team> teamList = new ArrayList<>();
    String teamURL = "http://localhost:3000/teams";
    static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_list);
        setTitle("Teams");
        token=this.getIntent().getStringExtra("TOKEN");
        teamRecyclerView = findViewById(R.id.teamRecyclerView);
        teamRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL,false);
        teamRecyclerView.setLayoutManager(layoutManager);
        teamAdapter = new TeamAdapter(teamList,this,token);
        teamRecyclerView.setAdapter(teamAdapter);
        teamAdapter.notifyDataSetChanged();




        try {
            new TeamAPI(teamURL,this,teamAdapter,teamList,token).execute();
            //   prodAdapter.notifyDataSetChanged();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
