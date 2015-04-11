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
	    
		//showbutton�洢ÿ��Ӧ�ö�Ӧ�û���ѡ��
		preferences1=getSharedPreferences("showbutton",MODE_WORLD_READABLE);
		//value��ʾuid��Ӧ�İ���
		preferences2=getSharedPreferences("value",MODE_WORLD_READABLE);
		editor1=preferences1.edit();
		editor2=preferences2.edit();
		 
		  gridview = (ListView) findViewById(R.id.mainview);
		  
	        // ��ȡͼƬ��Ӧ����������
		    pManager = getPackageManager();
	        List<PackageInfo> appList = getAllApps(this);
	 
	       list = new ArrayList<AppsItemInfo>();
	 
	        for (int i = 0; i < appList.size(); i++) {
	            PackageInfo pinfo = appList.get(i);
	            AppsItemInfo shareItem = new AppsItemInfo();
	            // ����ͼƬ
	            shareItem.setIcon(pManager
	                    .getApplicationIcon(pinfo.applicationInfo));
	            // ����Ӧ�ó�������
	            shareItem.setLabel(pManager.getApplicationLabel(
	                    pinfo.applicationInfo).toString());
	            // ����Ӧ�ó���İ���
	            shareItem.setPackageName(pinfo.applicationInfo.packageName);
	            
	            
	            //��ÿ��Ӧ�õ�uid�洢��value.xml
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
	 
	        // ����gridview��Adapter
	        gridview.setAdapter(new baseAdapter());
		
		
		
	}

	
	//��ȡ���а�װ��Ӧ��
	 public  List<PackageInfo> getAllApps(Context context) {
		 
	        List<PackageInfo> apps = new ArrayList<PackageInfo>();
	        PackageManager pManager = context.getPackageManager();
	        // ��ȡ�ֻ�������Ӧ��
	        List<PackageInfo> packlist = pManager.getInstalledPackages(0);
	        for (int i = 0; i < packlist.size(); i++) {
	            PackageInfo pak = (PackageInfo) packlist.get(i);
	 
	            // �ж��Ƿ�Ϊ��ϵͳԤװ��Ӧ�ó���
	            // ���ﻹ�������ϵͳ�Դ��ģ�������Ȳ�����ˣ��������Ҫ�����Լ����
	            // if()���ֵ���<=0��Ϊ�Լ�װ�ĳ��򣬷���Ϊϵͳ�����Դ�
	            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
	                // ����Լ��Ѿ���װ��Ӧ�ó���
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
	                // ʹ��View�Ķ���itemView��R.layout.item����
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
	            //���ð�ť�󣬰��û���ѡ�񱣴浽xml
	            holder.radiogroup.setOnCheckedChangeListener(new  clicklistener(list.get(position).getPackageName().toString()));
	            int radioid=preferences1.getInt(list.get(position).getPackageName().toString(), 0);
	            //ÿ�ΰѴ洢��ѡ�����������ʾ������
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
	 
	 //ÿ��ѡ��󱣴�״̬�����û�ѡ��
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
		 
	        private Drawable icon; // ���ͼƬ
	        private String label; // ���Ӧ�ó�����
	        private String packageName; // ���Ӧ�ó������
	 
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
