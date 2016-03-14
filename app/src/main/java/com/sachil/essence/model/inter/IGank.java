package com.sachil.essence.model.inter;

public interface IGank {

    void listHistory();

    void getDataByCategory(String category,int count,int pageIndex);

    void getDataByDate(String year,String month,String day);

    void getDataByRandom(String category,int count);

}
