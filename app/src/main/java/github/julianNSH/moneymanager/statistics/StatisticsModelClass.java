package github.julianNSH.moneymanager.statistics;

import github.julianNSH.moneymanager.R;

public class StatisticsModelClass {
    public int id, repeat;
    public Integer ivIcon = R.drawable.ic_flat;
    public String tvType;
    public float tvAmount;
    public String date;
    public String time;
    public String comment;

    public StatisticsModelClass(){}

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getIvIcon() {
        return ivIcon;
    }

    public void setIvIcon(Integer ivIcon) {
        this.ivIcon = ivIcon;
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
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
