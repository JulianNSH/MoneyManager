package github.julianNSH.moneymanager.savings;

public class SavingsModelClass {
    int id;
    String tvTitlePeriod;
    float tvIncome;
    float tvOutgoings;
    float tvResult;
    String date;
    String tvResultColor;
    public SavingsModelClass(){}

    public SavingsModelClass(int id, String tvTitlePeriod, String date, float tvIncome, float tvOutgoings,
                             float tvResult,String tvResultColor){

        this.id = id;
        this.tvIncome = tvIncome;
        this.date = date;
        this.tvOutgoings = tvOutgoings;
        this.tvResult = tvResult;
        this.tvResultColor = tvResultColor;
        this.tvTitlePeriod = tvTitlePeriod;
    }

    public SavingsModelClass(String tvTitlePeriod, String date, float tvIncome, float tvOutgoings, float tvResult,
                             String tvResultColor){
        this.tvIncome = tvIncome;
        this.date = date;
        this.tvOutgoings = tvOutgoings;
        this.tvResult = tvResult;
        this.tvResultColor = tvResultColor;
        this.tvTitlePeriod = tvTitlePeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTvResultColor() {
        return tvResultColor;
    }

    public void setTvResultColor(String tvResultColor) {
        this.tvResultColor = tvResultColor;
    }


    public String getTvTitlePeriod() {
        return tvTitlePeriod;
    }

    public void setTvTitlePeriod(String tvTitlePeriod) {
        this.tvTitlePeriod = tvTitlePeriod;
    }

    public float getTvIncome() {
        return tvIncome;
    }

    public void setTvIncome(float tvIncome) {
        this.tvIncome = tvIncome;
    }

    public float getTvOutgoings() {
        return tvOutgoings;
    }

    public void setTvOutgoings(float tvOutgoings) {
        this.tvOutgoings = tvOutgoings;
    }

    public float getTvResult() {
        return tvResult;
    }

    public void setTvResult(float tvResult) {
        this.tvResult = tvResult;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
