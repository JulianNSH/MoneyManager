package github.julianNSH.moneymanager.ui.overview;

import java.sql.Time;
import java.util.Date;

public class OverviewModelClass {
    String tvDomain;
    Integer ivFigure; //figure int id
    String tvType; //Title of transaction
    float tvAmount; //amount of transatction
    String date;  //date of transaction
    String time;  //time of transaction
    String comment; //user comment of transaction

    public OverviewModelClass(String tvDomain, Integer ivFigure, String tvType, float tvAmount, String time, String date, String comment){
        this.tvDomain = tvDomain;
        this.ivFigure = ivFigure;
        this.tvType = tvType;
        this.tvAmount = tvAmount;
        this.time = time;
        this.date = date;
        this.comment = comment;
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
}
