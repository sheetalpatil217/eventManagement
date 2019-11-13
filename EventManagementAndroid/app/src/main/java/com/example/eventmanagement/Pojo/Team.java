package com.example.eventmanagement.Pojo;

import java.util.List;

public class Team {
    String teamName;
    String averageScore;
    String totalScore;
    String question1;
    String question2;
    String question3;
    String question4;
    String question5;
    String question6;
    String question7;
    List<Question> questions;
    String progress;
    Boolean flagStarted;

    public Team() {
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(String averageScore) {
        this.averageScore = averageScore;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getQuestion3() {
        return question3;
    }

    public void setQuestion3(String question3) {
        this.question3 = question3;
    }

    public String getQuestion4() {
        return question4;
    }

    public void setQuestion4(String question4) {
        this.question4 = question4;
    }

    public String getQuestion5() {
        return question5;
    }

    public void setQuestion5(String question5) {
        this.question5 = question5;
    }

    public String getQuestion6() {
        return question6;
    }

    public void setQuestion6(String question6) {
        this.question6 = question6;
    }

    public String getQuestion7() {
        return question7;
    }

    public void setQuestion7(String question7) {
        this.question7 = question7;
    }


    public Boolean getFlagStarted() {
        return flagStarted;
    }

    public void setFlagStarted(Boolean flagStarted) {
        this.flagStarted = flagStarted;
    }


    @Override
    public String toString() {
        return "Team{" +
                "teamName='" + teamName + '\'' +
                ", averageScore='" + averageScore + '\'' +
                ", totalScore='" + totalScore + '\'' +
                ", question1='" + question1 + '\'' +
                ", question2='" + question2 + '\'' +
                ", question3='" + question3 + '\'' +
                ", question4='" + question4 + '\'' +
                ", question5='" + question5 + '\'' +
                ", question6='" + question6 + '\'' +
                ", question7='" + question7 + '\'' +
                ", questions=" + questions +
                ", progress='" + progress + '\'' +
                ", flagStarted=" + flagStarted +
                '}';
    }
}
