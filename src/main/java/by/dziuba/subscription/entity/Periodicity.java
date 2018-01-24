package by.dziuba.subscription.entity;

public class Periodicity {
    private int id;
    private String periodicity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }
    //TODO equals hashcode when done with fields
}
