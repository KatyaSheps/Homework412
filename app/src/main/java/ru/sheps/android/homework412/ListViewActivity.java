package ru.sheps.android.homework412;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        ListView list = findViewById(R.id.list);

        List<Map<String, String>> values = prepareContent();

        String[] from = {"Header", "Subheader"};
        int[] to = {R.id.header, R.id.subheader};
        BaseAdapter listContentAdapter = createAdapter(values, from, to);

        list.setAdapter(listContentAdapter);
    }

    private BaseAdapter createAdapter(List<Map<String, String>> values, String[] from, int[] to) {
        return new SimpleAdapter(this, values, R.layout.my_simple_list_item, from, to);
    }

    private List<Map<String, String>> prepareContent() {
        String[] strings = getString(R.string.large_text).split("\n\n");

        List<Map<String, String>> list = new ArrayList<>();

        for (int i = 0; i < strings.length; i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("Header", strings[i]);
            map.put("Subheader",  String.valueOf(strings[i].length()));
            list.add(map);
        }

        return list;
    }
}