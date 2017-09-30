package com.example.baselibrary.otherlibs.literouter;

import com.example.baselibrary.otherlibs.literouter.annotations.ClassName;
import com.example.baselibrary.otherlibs.literouter.annotations.Key;
import com.example.baselibrary.otherlibs.literouter.annotations.RequestCode;

/**
 * Created by http://www.jianshu.com/p/79e9a54e85b2
 */

public interface LiteRouterService {

    @ClassName("com.hiphonezhu.test.demo.ActivityDemo2")
    @RequestCode(100)
    void intent2ActivityDemo2(@Key("platform") String platform, @Key("year") int year);

}
