package github.julianNSH.moneymanager.ui.statistics;

public class StatisticsModelClass {
    Integer ivIcon;
    String tvType;
    float tvAmount;
    String date, time, comment;

    public StatisticsModelClass(Integer ivIcon, String tvType, float tvAmount, String time, String date, String comment) {
        this.ivIcon = ivIcon;
        this.tvType = tvType;
        this.tvAmount = tvAmount;
        this.time = time;
        this.date =date;
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
