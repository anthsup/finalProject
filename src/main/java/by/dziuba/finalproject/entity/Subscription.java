package by.dziuba.subscription.entity;

import java.sql.Date;

public class Subscription {
    private int userId;
    private int periodicalId;
    private int monthsQuantity;
    private Date startDate;
    private Date endDate;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPeriodicalId() {
        return periodicalId;
    }

    public void setPeriodicalId(int periodicalId) {
        this.periodicalId = periodicalId;
    }

    public int getMonthsQuantity() {
        return monthsQuantity;
    }

    public void setMonthsQuantity(int monthsQuantity) {
        this.monthsQuantity = monthsQuantity;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
