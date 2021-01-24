package com.vilas.task1;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vilas.installedapplibrary.GetInstalledApps;

import java.util.ArrayList;

import listeners.OnItemClickListener;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, SearchView.OnQueryTextListener {

    private InstalledAppAdapter adapter;
    private ArrayList<GetInstalledApps.PackInfo> getListOfAppInstalled;
    private ArrayList<GetInstalledApps.PackInfo> listOfApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
    }

    public void initialise() {
        setListItems();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new InstalledAppAdapter(listOfApp, this, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        GetInstalledApps.PackInfo item = listOfApp.get(position);
        Intent launchApp = getPackageManager().getLaunchIntentForPackage(item.getPackageName());
        if (launchApp != null) {
            startActivity(launchApp);
        } else {
            Toast.makeText(this, "Not able to launch app", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        createSearchViewMenu(menu);
        return true;
    }

    public void createSearchViewMenu(Menu menu) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    private void setListItems(){
        getListOfAppInstalled = (GetInstalledApps.getSingleTonInstance().getInstalledApps(this));
        listOfApp = new ArrayList<>();
        listOfApp.addAll(getListOfAppInstalled);
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            adapter.setItems();
            listOfApp = getListOfAppInstalled;
            return false;
        }
        ArrayList<GetInstalledApps.PackInfo> filteredList = new ArrayList<>();
        for (GetInstalledApps.PackInfo app : listOfApp) {
            if (app.getAppName().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(app);
            }
        }

        adapter.setFilter(filteredList);
        listOfApp = filteredList;

        return false;
    }
}