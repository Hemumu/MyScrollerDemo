package com.scroller.helin.myscrollerdemo.service;

import com.scroller.helin.myscrollerdemo.bean.UserBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by helin on 2016/9/22 17:20.
 */

public interface MyService {
    @GET("user/login" )
    Observable<UserBean> login(
            @Query("username") String username,
            @Query("password") String password
    );
}
