package com.sachil.essence.model.gank;

public class GankHistoryData {

    private String mDate = null;
    private String mYear = null;
    private String mMonth = null;
    private String mDay = null;
    private GankDateData mDateData = null;

    public GankHistoryData(String date, GankDateData dateData) {
        mDate = date;
        mDateData = dateData;
        if (mDate != null) {
            String[] s = date.split("-", 3);
            mYear = s[0];
            mMonth = s[1];
            mDay = s[2];
        }
    }

    public GankHistoryData(String year, String month, String day, GankDateData dateData) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mDateData = dateData;
        mDate = String.format("%s-%s-%s", mYear, mMonth, mDay);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof GankHistoryData) {
            GankHistoryData data = (GankHistoryData) o;
            return data.mDate.equalsIgnoreCase(this.mDate);
        } else
            return false;
    }

    public String getDate() {
        return mDate;
    }

    public GankDateData getDateData() {
        return mDateData;
    }

    public void setDateData(GankDateData dateData) {
        mDateData = dateData;
    }

    public String getDay() {
        return mDay;
    }

    public String getMonth() {
        return mMonth;
    }

    public String getYear() {
        return mYear;
    }
}
