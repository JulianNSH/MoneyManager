package github.julianNSH.moneymanager.statistics;

public class StatisticsModelClass implements Comparable<StatisticsModelClass>{
    int id, repeat;
    Integer ivIcon;
    String tvType;
    float tvAmount;
    String date;
    String time;
    String comment;

    public StatisticsModelClass(){}

    public StatisticsModelClass(int id, String tvType, float tvAmount, String time,String date, String comment) {
        this.id = id;
        this.tvType = tvType;
        this.tvAmount = tvAmount;
        this.time = time;
        this.date = date;
        this.comment = comment;
    }
    //Constructor fo Outgoings
    public StatisticsModelClass(int id, Integer ivIcon, String tvType, float tvAmount, String time,String date, String comment) {
        this.id = id;
        this.ivIcon = ivIcon;
        this.tvType = tvType;
        this.tvAmount = tvAmount;
        this.time = time;
        this.date = date;
        this.comment = comment;
    }
    public StatisticsModelClass(Integer ivIcon, String tvType, float tvAmount, String time,String date, String comment) {
        this.ivIcon = ivIcon;
        this.tvType = tvType;
        this.tvAmount = tvAmount;
        this.time = time;
        this.date = date;
        this.comment = comment;
    }

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

    @Override
    public int compareTo(StatisticsModelClass o) {
        float comp = ((StatisticsModelClass)o).getTvAmount();
        return (int) (comp-this.tvAmount);
    }
}
