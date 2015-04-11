package com.example.leon;


import java.util.ArrayList;
import java.util.List;



import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
	String deviceId;
	private ListView mainview;
	private PackageManager pmanager;
	List<AppInfo> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	    
	   mainview=(ListView)findViewById(R.id.mainview);
	   pmanager=getPackageManager();
	   List<PackageInfo> apps=getallapps(this);
	   list = new ArrayList<AppInfo>();
	   
	   for(int i=0;i<apps.size();i++)
	   {
		   PackageInfo packinfo=apps.get(i);
		   AppInfo tmp=new AppInfo();
		   
		   tmp.setIcon(pmanager.getApplicationIcon(packinfo.applicationInfo));
		   tmp.setLabel(pmanager.getApplicationLabel(packinfo.applicationInfo).toString());
		   tmp.setPackageName(packinfo.applicationInfo.packageName);
		   list.add(tmp);   
	   }
		
	   
	   mainview.setAdapter(new baseAdapter());
	   
	   
		
	}
	
	
	private class baseAdapter extends BaseAdapter
	{
	 LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
		 
		public int getCount()
		{
			
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewholder;
			if(convertView==null)
			{
				 convertView = inflater.inflate(R.layout.apps, null);
				 viewholder=new ViewHolder();
				 viewholder.icon=(ImageView)convertView.findViewById(R.id.apps_image);
				 viewholder.label=(TextView)convertView.findViewById(R.id.apps_textview);
				 convertView.setTag(viewholder);
				
			}else{
				viewholder=(ViewHolder)convertView.getTag();
				
			}
			
			viewholder.icon.setImageDrawable(list.get(position).getIcon());
			viewholder.label.setText(list.get(position).getLabel().toString());
			
			return convertView;
		}
		
		
		
		
		
	}
	
	
	 private class ViewHolder{  
	        private ImageView icon;  
	        private TextView label;  
	    }  
	
	
	
	public List<PackageInfo> getallapps(Context context)
	{
		List<PackageInfo> allpackage=new ArrayList<PackageInfo>();
		PackageManager pmanager=context.getPackageManager();
		List<PackageInfo> applist=pmanager.getInstalledPackages(0);
		for(int i=0;i<applist.size();i++)
		{
			PackageInfo apk=applist.get(i);
			if((apk.applicationInfo.flags & apk.applicationInfo.FLAG_SYSTEM) <= 0)
				{
				allpackage.add(apk);
				}
			
		}
			
		return allpackage;

	}

	
	private class AppInfo{
		
		private Drawable icon;
		private String PackageName;
		private String label;
		
		
		public Drawable getIcon(){
			
			return icon;
		}
		
		public void setIcon(Drawable icon){
			
			this.icon=icon;
		}
		
		public String getLabel()
		{
			
			return label;	
			
		}
		
		public void setLabel(String label)
		{
			this.label=label;
		}
		
		public String getPackageName()
		{
			return PackageName;
		}
		
		public void setPackageName(String PackageNmae)
		{
			this.PackageName=PackageName;
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
