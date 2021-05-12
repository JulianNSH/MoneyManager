package github.julianNSH.moneymanager.scope;

public class ScopeModelClass {
    int id;
    String tvTitle, comment;
    float tvFinalAmount, tvCurrentAmount;
    String startTime, startDate, endTime, endDate;
    int pbProgressValue;

    public ScopeModelClass(int id, String tvTitle, float tvFinalAmount, float tvCurrentAmount, String startTime,
                           String startDate, String endTime, String endDate, String comment, int pbProgressValue){
        this.id = id;
        this.tvTitle = tvTitle;
        this.tvFinalAmount = tvFinalAmount;
        this.tvCurrentAmount = tvCurrentAmount;
        this.startTime = startTime;
        this.startDate = startDate;
        this.endTime = endTime;
        this.endDate = endDate;
        this.comment = comment;
        this.pbProgressValue = pbProgressValue;
    }

    public ScopeModelClass(String tvTitle, float tvFinalAmount, float tvCurrentAmount, String startTime,
                           String startDate, String endTime, String endDate, String comment, int pbProgressValue){
        this.tvTitle = tvTitle;
        this.tvFinalAmount = tvFinalAmount;
        this.tvCurrentAmount = tvCurrentAmount;
        this.startTime = startTime;
        this.startDate = startDate;
        this.endTime = endTime;
        this.endDate = endDate;
        this.comment = comment;
        this.pbProgressValue = pbProgressValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getTvCurrentAmount() {
        return tvCurrentAmount;
    }

    public void setTvCurrentAmount(float tvCurrentAmount) {
        this.tvCurrentAmount = tvCurrentAmount;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPbProgressValue() {
        return pbProgressValue;
    }

    public void setPbProgressValue(int pbProgressValue) {
        this.pbProgressValue = pbProgressValue;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public float getTvFinalAmount() {
        return tvFinalAmount;
    }

    public void setTvFinalAmount(float tvFinalAmount) {
        this.tvFinalAmount = tvFinalAmount;
    }
}
