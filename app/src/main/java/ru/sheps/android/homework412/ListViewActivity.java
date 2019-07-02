package ru.sheps.android.homework412;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String LARGE_TEXT = "large_text";
    List<Map<String, String>> values = new ArrayList<>();
    public static final String APP_PREFERENCES = "mysettings";
    SharedPreferences mySharedPreferences;
    BaseAdapter listContentAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<Integer> integerArrayList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        ListView list = findViewById(R.id.list);
        mySharedPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        boolean hasVisited = mySharedPreferences.getBoolean("hasVisited", false);

        if (!hasVisited) {
            // выводим нужную активность
           // integerArrayList = savedInstanceState.getIntegerArrayList("my_key");
            SharedPreferences.Editor editor = mySharedPreferences.edit();
     //       for (int i = 0; i < integerArrayList.size(); i++) {
//        }

                editor.putBoolean("hasVisited", true);
            editor.putString(LARGE_TEXT, getString(R.string.large_text));
            editor.apply();
        }

        values = prepareContent();
//

        String[] from = {"Header", "Subheader"};
        int[] to = {R.id.header, R.id.subheader};
        listContentAdapter = createAdapter(values, from, to);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                values.remove(position);
                integerArrayList.add (position);
                listContentAdapter.notifyDataSetChanged();
            }
        });

        list.setAdapter(listContentAdapter);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private BaseAdapter createAdapter(List<Map<String, String>> values, String[] from, int[] to) {
        return new SimpleAdapter(this, values, R.layout.my_simple_list_item, from, to);
    }

    private List<Map<String, String>> prepareContent() {

        String[] strings = {""};
        if (mySharedPreferences.contains(LARGE_TEXT)) {
            strings = mySharedPreferences.getString(LARGE_TEXT, "").split("\n\n");
        }

        List<Map<String, String>> list = new ArrayList<>();

        for (int i = 0; i < strings.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Header", strings[i]);
            map.put("Subheader", String.valueOf(strings[i].length()));
            list.add(map);
        }

        return list;
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        values.clear();
        values.addAll(prepareContent());
        listContentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
      outState.putIntegerArrayList("my_key", integerArrayList);
    }
}