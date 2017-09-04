package com.android.yzd.http;


import com.android.yzd.tools.L;

/**
 * Created by Administrator on 2016/8/29.
 */

public class ApiException extends RuntimeException {

    public ApiException(String resultError) {
        super(resultError);
        L.i("error:" + resultError);
    }
}
