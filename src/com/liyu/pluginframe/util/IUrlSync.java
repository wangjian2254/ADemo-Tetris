package com.liyu.pluginframe.util;

import java.util.List;

import org.apache.http.NameValuePair;

import com.liyu.pluginframe.beans.UserInfo;

import android.content.Context;
import android.os.Handler;

public interface IUrlSync {
	public static final String HTTPPARAM="?";
	public static final String HTTP="http://";
	public static final String GET="get";
	public static final String POST="post";
	public static final String INFOALL="infoall";
	public static final String INFOUPDATE="infoupdate";
	public static final String INFOALLIMG="infoallimg";
	public static final String INFOSEND="infosend";
	
	public void doResult() throws Exception;
	public void doFailureResult() throws Exception;
	public boolean doPerResult()throws Exception;
	public String getResult();
	public void setResult(String result) ;
	public boolean isGet();
	public String getUri() ;
	
	public String getAppParam();
	
	public String getAllUri();

	public void setUri(String uri) ;

	public String getModth() ;

	public void setModth(String modth) ;

	public String getResultType() ;

	public void setResultType(String resultType) ;

	public String getSyncType() ;

	public void setSyncType(String syncType) ;

	

	public String getCode() ;

	public void setCode(String code) ;

	
	
	public boolean isNotice() ;
	public void setNotice(boolean isNotice) ;
	
	public String getNoticeTitle() ;
	public void setNoticeTitle(String noticeTitle) ;
	public String getNoticeContent() ;
	public void setNoticeContent(String noticeContent) ;
	public String getUrlparam() ;
	public void setUrlparam(String urlparam) ;
	public String getNoticeCode() ;
	public void setNoticeCode(String noticeCode) ;
	public String getToastContentSu() ;
	public void setToastContentSu(String toastContentSu) ;
	public String getToastContentFa() ;
	public void setToastContentFa(String toastContentFa);
	public boolean isToast();
	public void setToast(boolean isToast);
	public Handler getHandler();
	public void setHandler(Handler handler);
	public List<NameValuePair> getPrarm();
	public void setPrarm(List<NameValuePair> prarm) ;
	public List<Notice> getNoticelist() ;
	public void setNoticelist(List<Notice> noticelist) ;

	public boolean isNeedNotice();

	public void setNeedNotice(boolean needNotice);


	public List<Notice> getNotices() ;

	public String getAppcode() ;

	public void setAppcode(String appcode);

	
	public void setMainContext(Context context);
	public Context getMainContext();
	public void setPluginContext(Context context);
	public Context getPluginContext();
	public String getUserinfoparam();
	public void setUserinfoparam(String userinfoparam) ;
	
	public String getPluginActionStr();
	public void setPluginActionStr(String pluginActionStr);
	
	public boolean isNeedConnect();
	public void addConnectNum();
	public UserInfo getUser();
	public void setUser(UserInfo user);
}
