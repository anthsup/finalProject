package by.dziuba.subscription.entity;

public class Genre {
    private int periodicalId;
    private String name;

    public int getPeriodicalId() {
        return periodicalId;
    }

    public void setPeriodicalId(int periodicalId) {
        this.periodicalId = periodicalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //TODO equals hashcode when done with fields
}
