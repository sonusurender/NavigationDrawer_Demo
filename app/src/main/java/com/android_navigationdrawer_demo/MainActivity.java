package com.android_navigationdrawer_demo;

import java.util.ArrayList;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android_navigationdrawer_demo.fragments.AboutUs_Fragment;
import com.android_navigationdrawer_demo.fragments.Home_Fragment;
import com.android_navigationdrawer_demo.fragments.Messages_Fragment;
import com.android_navigationdrawer_demo.fragments.Settings_Fragment;
import com.android_navigationdrawer_demo.fragments.UserAccount_Fragment;

public class MainActivity extends ActionBarActivity {
	private static DrawerLayout drawer;
	private static ActionBarDrawerToggle actionbarToggle;

	private static ArrayList<Navigation_Items> arrayList;
	private static Navigation_Drawer_Adapter adapter;

	private static ListView listview;

	private static FragmentManager fragment_manager;
	private static Toolbar toolbar;
	private static RelativeLayout left_slider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		populateListItems();

		// Replace the default/home fragment if savedinstance is null
		if (savedInstanceState == null) {
			selectItem(0);
		}
	}

	void init() {
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		listview = (ListView) findViewById(R.id.list_slidermenu);
		toolbar = (Toolbar) findViewById(R.id.toolbar);

		left_slider = (RelativeLayout) findViewById(R.id.slider);

		// Fragment manager to manage fragment
		fragment_manager = getSupportFragmentManager();
		arrayList = new ArrayList<Navigation_Items>();

		// Setting actionbar toggle
		actionbarToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
				R.string.drawer_open, R.string.drawer_close) {

			@Override
			public void onDrawerClosed(View drawerView) {
				Toast.makeText(MainActivity.this, "Drawer Close",
						Toast.LENGTH_SHORT).show();
				super.onDrawerClosed(drawerView);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				Toast.makeText(MainActivity.this, "Drawer Open",
						Toast.LENGTH_SHORT).show();
				super.onDrawerOpened(drawerView);
			}

		};

		// Setting drawer listener
		drawer.setDrawerListener(actionbarToggle);

	}

	// Populate navigation drawer listitems
	void populateListItems() {
		Integer Icons[] = { R.drawable.ic_home, R.drawable.ic_user,
				R.drawable.ic_messages, R.drawable.ic_settings,
				R.drawable.ic_aboutus };
		String title[] = getResources().getStringArray(R.array.list_items);
		String subtitle[] = getResources()
				.getStringArray(R.array.list_subitems);

		for (int i = 0; i < Icons.length; i++) {
			arrayList
					.add(new Navigation_Items(title[i], subtitle[i], Icons[i]));
		}

		adapter = new Navigation_Drawer_Adapter(MainActivity.this, arrayList);

		// Setting adapter
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

	// Select item method for replacing fragments
	public void selectItem(int position) {

		// Setting toolbar title
		toolbar.setTitle(arrayList.get(position).getTitle());

		// Close drawer method
		closeDrawer();
		switch (position) {
		case 0:
			replaceFragment(new Home_Fragment(), "Home");
			break;
		case 1:
			replaceFragment(new UserAccount_Fragment(), "User Account");
			break;
		case 2:
			replaceFragment(new Messages_Fragment(), "Messages");
			break;

		case 3:
			replaceFragment(new Settings_Fragment(), "Settings");
			break;
		case 4:
			replaceFragment(new AboutUs_Fragment(), "About Us");
			break;

		}
	}

	// Replace fragment method
	void replaceFragment(Fragment fragment, String tag) {

		// First find the fragment by TAG and if it null then replace the
		// fragment else do nothing
		Fragment fr = fragment_manager.findFragmentByTag(tag);
		if (fr == null) {
			fragment_manager.beginTransaction()
					.replace(R.id.frame_container, fragment, tag).commit();
		}

	}

	// close the open drawer
	void closeDrawer() {
		if (drawer.isDrawerOpen(left_slider)) {
			drawer.closeDrawer(left_slider);
		}
	}

	@Override
	public void onBackPressed() {

		// Call whenBackpressed method to do task
		whenBackPressed();
	}

	// Method to be execute on back pressed
	void whenBackPressed() {
		Fragment fr = fragment_manager.findFragmentByTag("Home");
		// First close the drawer if open
		if (drawer.isDrawerOpen(left_slider)) {
			drawer.closeDrawer(left_slider);

		}
		// else replace the home fragment if TAG is null
		else {
			if (fr == null) {
				selectItem(0);
			}

			// finally finish activity
			else {
				finish();
			}
		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync state for actionbar toggle
		actionbarToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		actionbarToggle.onConfigurationChanged(newConfig);
	}
}
