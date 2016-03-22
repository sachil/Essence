package com.sachil.essence.presenter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.sachil.essence.model.gank.GankData;
import com.sachil.essence.model.gank.GankHistoryData;
import com.sachil.essence.ui.holder.GankDateViewHolder;
import com.sachil.essence.ui.view.IBase;

import java.util.ArrayList;
import java.util.List;

public class GankDateDataPresenter extends GankPresenter {
    private static final String TAG = GankDateDataPresenter.class.getSimpleName();
    private GankDateAdapter mAdapter = null;
    private List<GankHistoryData> mDataList = null;

    public GankDateDataPresenter(IBase baseView) {
        super(baseView);
        mDataList = new ArrayList<>();
    }

    @Override
    public void onFailed(int request, Throwable throwable) {
        mBaseView.hideLoadingView();
        Log.e(TAG, "-------------------------");
    }

    @Override
    public void onSucceed(int request, Object results) {
        if (request == LIST_HISTORY) {
            Log.e(TAG, "++++++++++++++++++++++");
            mBaseView.hideLoadingView();
            List<String> historyList = (List<String>) ((GankData) results).getData();
            if (mDataList.size() != 0)
                mDataList.clear();
            for (String date : historyList)
                mDataList.add(new GankHistoryData(date, null));
            if (mAdapter == null)
                mAdapter = new GankDateAdapter();
            mBaseView.updateData(LIST_HISTORY, null);
        } else {
            GankHistoryData data = (GankHistoryData)results;
            Integer index = mDataList.indexOf(data);
            mDataList.get(index).setDateData(data.getDateData());
            mBaseView.updateData(GET_DATE_DATA, index);
        }
    }

    public GankDateAdapter getAdapter() {
        return mAdapter;
    }

    public void listHistory() {
        mBaseView.showLoadingView();
        mGankImp.listHistory();
    }

    public void getDataByDate(String year, String month, String day) {
        mGankImp.getDataByDate(year, month, day);
    }


    private class GankDateAdapter extends RecyclerView.Adapter<GankDateViewHolder> {

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        @Override
        public void onBindViewHolder(GankDateViewHolder holder, int position) {
            GankHistoryData historyData = mDataList.get(position);
            holder.setData(historyData);
            if (historyData.getDateData() == null){
                getDataByDate(historyData.getYear(), historyData.getMonth(), historyData.getDay());
            }
        }

        @Override
        public GankDateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GankDateViewHolder(parent);
        }
    }


}
