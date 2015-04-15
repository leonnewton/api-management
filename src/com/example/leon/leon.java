package com.example.leon;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AndroidAppHelper;
import android.content.Context;
import android.os.Binder;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class leon implements IXposedHookLoadPackage{

	public static final String SHOWBUTTON = "showbutton";
	public static final String VALUE = "value";
	public int resultBack=0;
	

	 public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
	        /*if (!lpparam.packageName.equals("com.android.systemui"))
	            return;*/
		  
		
		 XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getDeviceId", new XC_MethodHook() {
	            @Override
	            
	            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
	                // this will be called before the clock was updated by the original method
	            
	            	
	            }
	            
	            
	            
	            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
	            		

	            	Context context = (Context) AndroidAppHelper.currentApplication();
	            	
	            	boolean isrunning=false;
	            	
	            	/*判断程序在前台还是后台*/
	            	 ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	            	    List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
	            	    for (RunningAppProcessInfo appProcess : appProcesses) {
	            	         if (appProcess.processName.equals(context.getPackageName())) {
	            	                if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
	            	                // XposedBridge.log("back");
	            	                	System.out.println("back");
	            	                 isrunning=false;
	            	                         
	            	                }else{
	            	              // 	 XposedBridge.log("front");
	            	                	System.out.println("front");
	            	               	isrunning=true;        
	            	                }
	            	           }
	            	    }
	            	
	           /*获得uid*/
	            	int uid=Binder.getCallingUid();
	            //	 XposedBridge.log("after"+uid );
	            	System.out.println("after"+uid );
	            	
	            	 
	            /*读取应用的uid*/
	            	  XSharedPreferences pre = new XSharedPreferences("com.example.leon", VALUE );  
		      	        String def="";
		      	        String uuid=String.valueOf(uid);
		      	         String pname=pre.getString(uuid, def);
		      	         
		      	         
		      	
		      	         

		      	     /*根据uid获得用户的选择*/    
		      	    XSharedPreferences xvalue = new XSharedPreferences("com.example.leon", SHOWBUTTON );  
		      	         int result=xvalue.getInt(pname, 0);
		      	         
		      	         
		      	         
		      	SimpleDateFormat mdf=new  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");       
		      	       if(result==R.id.ban)
		   			{
		     XposedBridge.log(mdf.format(new Date())+" "+pname+" called api/ban so ban");	
		 	System.out.println("banned");
		      	    	 resultBack=2;
		      	    	 param.setResult(String.valueOf(resultBack));
		   			 	
		   			}else if(result==R.id.allow)
		   			{
	         XposedBridge.log(mdf.format(new Date())+" "+pname+" called api/allow so allow");	
	     	  System.out.println("allow");
		   			 resultBack=3;
		   			 param.setResult(String.valueOf(resultBack));
		   			}else if(result==R.id.running)
		   			{
		   				if(isrunning==false)
		   				{      resultBack=4;
		   	XposedBridge.log(mdf.format(new Date())+" "+pname+" called api/running-back so ban");	 
		      	System.out.println("real back");
		   			   param.setResult(String.valueOf(resultBack));
		   				}else{
		   				 resultBack=3;
		XposedBridge.log(mdf.format(new Date())+" "+pname+" called api/running-front so allow");	 
				      	  System.out.println("real front");
			   			 param.setResult(String.valueOf(resultBack));	
		   				}
		   			 
		   			 
		   			}
		      	         
		      	 
		      	   
		      
		      	  System.out.println("resultback"+String.valueOf(resultBack));
		      	   
	            }
	        });
	    }
	 

	 public int  result()
	 {
		
		 return 1;
	 }
	 
	
	 
	
}