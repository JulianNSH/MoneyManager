package github.julianNSH.moneymanager.savings;

public class SavingsModelClass {
    int id;
    String tvTitlePeriod;
    String tvIncome;
    String tvOutcome;
    String tvResult;
    String tvResultColor;

    public SavingsModelClass(int id, String tvTitlePeriod, String tvIncome, String tvOutcome, String tvResult,
                             String tvResultColor){
        this.id = id;
        this.tvIncome = tvIncome;
        this.tvOutcome = tvOutcome;
        this.tvResult = tvResult;
        this.tvResultColor = tvResultColor;
        this.tvTitlePeriod = tvTitlePeriod;
    }

    public SavingsModelClass(String tvTitlePeriod, String tvIncome, String tvOutcome, String tvResult,
                             String tvResultColor){
        this.tvIncome = tvIncome;
        this.tvOutcome = tvOutcome;
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

    public String getTvIncome() {
        return tvIncome;
    }

    public void setTvIncome(String tvIncome) {
        this.tvIncome = tvIncome;
    }

    public String getTvOutcome() {
        return tvOutcome;
    }

    public void setTvOutcome(String tvOutcome) {
        this.tvOutcome = tvOutcome;
    }

    public String getTvResult() {
        return tvResult;
    }

    public void setTvResult(String tvResult) {
        this.tvResult = tvResult;
    }
}
