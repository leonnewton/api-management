package com.example.leon;




import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	String deviceId;
	
	List<AppsItemInfo> list;
	 
    private ListView gridview;
    private PackageManager pManager;
    SharedPreferences preferences1;
    SharedPreferences preferences2;
    SharedPreferences.Editor editor1;
    SharedPreferences.Editor editor2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    
		//showbutton存储每个应用对应用户的选项
		preferences1=getSharedPreferences("showbutton",MODE_WORLD_READABLE);
		//value表示uid对应的包名
		preferences2=getSharedPreferences("value",MODE_WORLD_READABLE);
		editor1=preferences1.edit();
		editor2=preferences2.edit();
		 
		  gridview = (ListView) findViewById(R.id.mainview);
		  
	        // 获取图片、应用名、包名
		    pManager = getPackageManager();
	        List<PackageInfo> appList = getAllApps(this);
	 
	       list = new ArrayList<AppsItemInfo>();
	 
	        for (int i = 0; i < appList.size(); i++) {
	            PackageInfo pinfo = appList.get(i);
	            AppsItemInfo shareItem = new AppsItemInfo();
	            // 设置图片
	            shareItem.setIcon(pManager
	                    .getApplicationIcon(pinfo.applicationInfo));
	            // 设置应用程序名字
	            shareItem.setLabel(pManager.getApplicationLabel(
	                    pinfo.applicationInfo).toString());
	            // 设置应用程序的包名
	            shareItem.setPackageName(pinfo.applicationInfo.packageName);
	            
	            
	            //把每个应用的uid存储到value.xml
	            try {
	                PackageManager pm = getPackageManager();
	                ApplicationInfo ai = pm.getApplicationInfo(pinfo.applicationInfo.packageName, PackageManager.GET_ACTIVITIES);
	                editor2.putString(String.valueOf(ai.uid), pinfo.applicationInfo.packageName);
	   			    editor2.commit();
	               } catch (NameNotFoundException e) {
	                 e.printStackTrace();
	               }
	               
	            
	            list.add(shareItem);
	 
	        }
	 
	        // 设置gridview的Adapter
	        gridview.setAdapter(new baseAdapter());
		
		
		
	}

	
	//获取所有安装的应用
	 public  List<PackageInfo> getAllApps(Context context) {
		 
	        List<PackageInfo> apps = new ArrayList<PackageInfo>();
	        PackageManager pManager = context.getPackageManager();
	        // 获取手机内所有应用
	        List<PackageInfo> packlist = pManager.getInstalledPackages(0);
	        for (int i = 0; i < packlist.size(); i++) {
	            PackageInfo pak = (PackageInfo) packlist.get(i);
	 
	            // 判断是否为非系统预装的应用程序
	            // 这里还可以添加系统自带的，这里就先不添加了，如果有需要可以自己添加
	            // if()里的值如果<=0则为自己装的程序，否则为系统工程自带
	            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
	                // 添加自己已经安装的应用程序
	                apps.add(pak);
	            }
	 
	        }
	        return apps;
	    }
	 
	 
	 private class baseAdapter extends BaseAdapter {
	        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
	 
	        @Override
	        public int getCount() {
	            // TODO Auto-generated method stub
	            return list.size();
	        }
	 
	        @Override
	        public Object getItem(int position) {
	            // TODO Auto-generated method stub
	            return null;
	        }
	 
	        @Override
	        public long getItemId(int position) {
	            // TODO Auto-generated method stub
	            return position;
	        }
	        
	        
	        
	 
	        @Override
	        public View getView(int position, View convertView, ViewGroup parent) {
	            // TODO Auto-generated method stub
	            ViewHolder holder;
	            if (convertView == null) {
	                // 使用View的对象itemView与R.layout.item关联
	                convertView = inflater.inflate(R.layout.apps, null);
	                holder = new ViewHolder();
	                holder.icon = (ImageView) convertView
	                        .findViewById(R.id.apps_image);
	                holder.label = (TextView) convertView
	                        .findViewById(R.id.apps_textview);
	                holder.radiogroup=(RadioGroup)convertView.findViewById(R.id.rg);
	                convertView.setTag(holder);
	            } else {
	                holder = (ViewHolder) convertView.getTag();
	            }
	 
	            holder.icon.setImageDrawable(list.get(position).getIcon());
	            holder.label.setText(list.get(position).getLabel().toString());
	            //设置按钮后，把用户的选择保存到xml
	            holder.radiogroup.setOnCheckedChangeListener(new  clicklistener(list.get(position).getPackageName().toString()));
	            int radioid=preferences1.getInt(list.get(position).getPackageName().toString(), 0);
	            //每次把存储的选择读出来，显示到界面
	            if(radioid!=0)
	            {
	            	// System.out.println("checkid"+radioid);
	            	 holder.radiogroup.check(radioid);
	            }else{
	            	//System.out.println(list.get(position).getPackageName().toString()+radioid);
	            	
	            }
	            
	            
	        // int id= holder.radiogroup.getCheckedRadioButtonId();
	        // holder.radiogroup.check(id);
	       //  RadioButton btn=(RadioButton)findViewById(holder.radiogroup.getCheckedRadioButtonId());
	         //   System.out.println("id"+id);
	          //  System.out.println(btn.getText());
	           // System.out.println("id"+position);
	            return convertView;
	 
	        }
	 
	    }
	 
	 //每次选择后保存状态，即用户选择
	 public class clicklistener implements OnCheckedChangeListener
	 {   
		 String pname;
		public  clicklistener(String s){
			 
			 pname=s;
		 }
		@Override
		public void onCheckedChanged(RadioGroup group, int checkid) {
			// TODO Auto-generated method stub
			// System.out.println(pname+checkid);
			 editor1.putInt(pname, checkid);
			 editor1.commit();
			if(checkid==R.id.ban)
			{
			 	
			}else if(checkid==R.id.allow)
			{
				
			}else if(checkid==R.id.running)
			{
				
				
			}
			
			
		}
		 
	 }
	 
	 
	 
	 private class ViewHolder{  
	        private ImageView icon;  
	        private TextView label;  
	        private RadioGroup radiogroup;
	    }  
	
	 private class AppsItemInfo {
		 
	        private Drawable icon; // 存放图片
	        private String label; // 存放应用程序名
	        private String packageName; // 存放应用程序包名
	 
	        public Drawable getIcon() {
	            return icon;
	        }
	 
	        public void setIcon(Drawable icon) {
	            this.icon = icon;
	        }
	 
	        public String getLabel() {
	            return label;
	        }
	 
	        public void setLabel(String label) {
	            this.label = label;
	        }
	 
	        public String getPackageName() {
	            return packageName;
	        }
	 
	        public void setPackageName(String packageName) {
	            this.packageName = packageName;
	        }
	 
	    }
	 
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
	
		
		return super.onOptionsItemSelected(item);
	}
	
	
	
}
