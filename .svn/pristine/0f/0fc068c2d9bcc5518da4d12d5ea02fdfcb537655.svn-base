package cn.ruicz.basecore.webview;

import android.content.Intent;
import android.os.Bundle;

import com.just.agentweb.MiddlewareWebClientBase;

import cn.ruicz.basecore.webview.sonic.SonicImpl;
import cn.ruicz.basecore.webview.sonic.SonicJavaScriptInterface;

import static cn.ruicz.basecore.webview.sonic.SonicJavaScriptInterface.PARAM_CLICK_TIME;

/**
 * Created by cenxiaozhong on 2017/12/18.
 *
 * If you wanna use VasSonic to fast open first page , please
 * follow as sample to update your code;
 */

public class VasSonicFragment extends WebFragment {

    private SonicImpl mSonicImpl;

    public static VasSonicFragment newInstance(String url){
        VasSonicFragment mVasSonicFragment =new VasSonicFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(PARAM_CLICK_TIME, System.currentTimeMillis());
        bundle.putString(URL_KEY, url);
        mVasSonicFragment.setArguments(bundle);
        return mVasSonicFragment;
    }


    @Override
    public void init() {
        // 1. 首先创建SonicImpl
        mSonicImpl = new SonicImpl(this.getArguments().getString(URL_KEY), this.getContext());
        // 2. 调用 onCreateSession
        mSonicImpl.onCreateSession();
        super.init();
        //4. 注入 JavaScriptInterface
        Intent intent = new Intent();
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, this.getArguments().getLong(PARAM_CLICK_TIME, -1));
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        mAgentWeb.getJsInterfaceHolder().addJavaObject("sonic", new SonicJavaScriptInterface(mSonicImpl.getSonicSessionClient(), intent));
        //5. 最后绑定AgentWeb
        mSonicImpl.bindAgentWeb(mAgentWeb);
    }

    //在步骤3的时候应该传入给AgentWeb
    @Override
    public MiddlewareWebClientBase getMiddlewareWebClient() {
        return mSonicImpl.createSonicClientMiddleWare();
    }

    //getUrl 应该为null
    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //销毁SonicSession
        if(mSonicImpl !=null){
            mSonicImpl.destrory();
        }
    }
}
