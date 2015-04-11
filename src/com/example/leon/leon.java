package com.example.leon;

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
	            	int uid=Binder.getCallingUid();
	            	 XposedBridge.log("before"+uid );
	            	
	            	  XSharedPreferences pre = new XSharedPreferences("com.example.leon", VALUE );  
		      	        String def="";
		      	        String uuid=String.valueOf(uid);
		      	         String pname=pre.getString(uuid, def);
		      	         
		      	    XSharedPreferences xvalue = new XSharedPreferences("com.example.leon", SHOWBUTTON );  
		      	         int result=xvalue.getInt(pname, 0);
		      	         
		      	         
		      	       if(result==R.id.ban)
		   			{
		      	    	   
		      	    	 resultBack=2;
		   			 	
		   			}else if(result==R.id.allow)
		   			{
		   			 resultBack=3;
		   			}else if(result==R.id.running)
		   			{
		   				
		   			 resultBack=4;
		   			}
		      	         
		      	         
		      	   XposedBridge.log("resultback"+String.valueOf(resultBack));
	            	 
	            	 
	            	 
	            	 
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