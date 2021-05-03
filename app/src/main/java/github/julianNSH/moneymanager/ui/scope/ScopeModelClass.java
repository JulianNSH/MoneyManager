package github.julianNSH.moneymanager.ui.scope;

public class ScopeModelClass {
    String tvTitle, tvValues;
    int pbProgressValue;

    public ScopeModelClass(String tvTitle, String tvValues, int pbProgressValue){
        this.tvTitle = tvTitle;
        this.tvValues = tvValues;
        this.pbProgressValue = pbProgressValue;
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

    public String getTvValues() {
        return tvValues;
    }

    public void setTvValues(String tvValues) {
        this.tvValues = tvValues;
    }
}
