package github.julianNSH.moneymanager.overview;

import github.julianNSH.moneymanager.statistics.StatisticsModelClass;

public class OverviewModelClass{
    int id;
    String tvDomain;
    Integer ivFigure; //figure int id
    String tvType; //Title of transaction
    float tvAmount; //amount of transatction
    String date;  //date of transaction
    String time;  //time of transaction
    String comment; //user comment of transaction
    int repeat;
    public OverviewModelClass(){}

    public OverviewModelClass(int id, String tvDomain, Integer ivFigure, String tvType, float tvAmount, String time, String date, String comment){
        this.id = id;
        this.tvDomain = tvDomain;
        this.ivFigure = ivFigure;
        this.tvType = tvType;
        this.tvAmount = tvAmount;
        this.time = time;
        this.date = date;
        this.comment = comment;
    }

    public OverviewModelClass(String tvDomain, Integer ivFigure, String tvType, float tvAmount, String time, String date, String comment){
        this.tvDomain = tvDomain;
        this.ivFigure = ivFigure;
        this.tvType = tvType;
        this.tvAmount = tvAmount;
        this.time = time;
        this.date = date;
        this.comment = comment;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getTvDomain() { return tvDomain; }
    public void setTvDomain(String tvDomain) { this.tvDomain = tvDomain; }

    public Integer getIvFigure() {
        return ivFigure;
    }


    public void setIvFigure(Integer ivFigure) {
        this.ivFigure = ivFigure;
    }

    public String getTvType() {
        return tvType;
    }
    public void setTvType(String tvType) {
        this.tvType = tvType;
    }

    public float getTvAmount() {
        return tvAmount;
    }
    public void setTvAmount(float tvAmount) {
        this.tvAmount = tvAmount;
    }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }



}
