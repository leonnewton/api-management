package com.example.leon;

import android.content.Context;
import android.os.Binder;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class leon implements IXposedHookLoadPackage{

	 public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
	        /*if (!lpparam.packageName.equals("com.android.systemui"))
	            return;*/
		

		 XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager", lpparam.classLoader, "getDeviceId", new XC_MethodHook() {
	            @Override
	            
	            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
	                // this will be called before the clock was updated by the original method
	            	int uid=Binder.getCallingUid();
	            	 XposedBridge.log("before"+uid );
	            	 test a=new test();
	            	 int result=a.result();
	            	 
	            	 if(result==uid)
	            	 {
	             XposedBridge.log("the same");
	            	 }else{
	            		 
	            		  XposedBridge.log("not the same"); 
	            		 
	            		 
	            	 }
	            	
	            	 
	            	 
	            	 
	            	 
	            }
	            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
	            	int uid=Binder.getCallingUid();
	            	 XposedBridge.log("after"+uid );
	            }
	        });
	    }
	 
	 
	 
	 public int  result()
	 {
		
		 return 1;
	 }
	 
	
	 
	
}



