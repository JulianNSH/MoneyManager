package github.julianNSH.moneymanager.overview;

public class OverviewModelClass{
    int id;
    public String tvDomain;
    public Integer ivFigure; //figure int id
    public String tvType; //Title of transaction
    public float tvAmount; //amount of transatction
    public String date;  //date of transaction
    public String time;  //time of transaction
    public String comment; //user comment of transaction
    int repeat;
    public OverviewModelClass(){}

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
