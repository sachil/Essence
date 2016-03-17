package com.sachil.essence.ui.view;

import com.sachil.essence.model.BaseImp.REQUEST_TYPE;

public interface IBase {

    void showLoadingView();

    void hideLoadingView();

    void updateData(REQUEST_TYPE type, Object data);

}
