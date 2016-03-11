package com.sachil.essence.model.inter;

public interface IGank {

    void listHistory();

    void getDataByCategory(String category,int count,int pageIndex);

    void getDataByDate(String date);

    void getDataByRandom(String category,int count,int pageIndex);

}
