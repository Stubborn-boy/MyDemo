package com.example.mydemo.dingdingcontact.mvp;

import android.content.Context;

import com.example.baselibrary.mvp.RxPresenter;
import com.example.baselibrary.utils.LogUtils;
import com.example.mydemo.dingdingcontact.entity.ResultVo;
import com.example.mydemo.http.RetrofitClient;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;


/**
 * Created by 71541 on 2017/9/7.
 */

public class OrganizationPresenter extends RxPresenter<IOrganizationView> {

    public OrganizationPresenter(){

    }

    public void loadData(Context context, String code){
        addSubscribe(
            RetrofitClient.getInstance(context).getApiService()
                    .getOrgContacts("", code, "1213", "311f4f508d776b115827e76bd11ae724")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ResourceSubscriber<ResultVo>() {
                        @Override
                        public void onNext(ResultVo resultVo) {
                            mView.showData(resultVo);
                        }

                        @Override
                        public void onError(Throwable t) {
                            mView.showError(t.toString());
                        }

                        @Override
                        public void onComplete() {
                            mView.complete();
                        }
                    })
        );
    }

    public void getFriendData(Context context){
        addSubscribe(
            RetrofitClient.getInstance(context).getApiService()
                    .getFriendList("1213", "1", "5000", "311f4f508d776b115827e76bd11ae724")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new ResourceSubscriber<ResultVo>() {
                        @Override
                        public void onNext(ResultVo resultVo) {
                            mView.showData(resultVo);
                        }

                        @Override
                        public void onError(Throwable t) {
                            mView.showError(t.toString());
                            LogUtils.e(t.toString());
                        }

                        @Override
                        public void onComplete() {
                            mView.complete();
                        }
                    })
        );
    }
}
