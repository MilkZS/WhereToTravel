package com.milkzs.android.wheretotravel.search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.milkzs.android.wheretotravel.Base.BaseInfo;
import com.milkzs.android.wheretotravel.R;
import com.milkzs.android.wheretotravel.search.task.SearchNameTask;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements SearchView.SearchViewListener {

    private String TAG = "SearchActivity";
    private ArrayAdapter<String> autoCompleteDataAdapter;
    private ArrayList<String> autoCompleteData = new ArrayList<>();
    private SearchNameTask searchNameTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView searchView = findViewById(R.id.search_define_view);
        searchView.setClickListener(this);
        autoCompleteDataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_expandable_list_item_1
                ,autoCompleteData );
        searchView.setAutoCompleteAdapter(autoCompleteDataAdapter);

    }

    private void autoCompleteEditView(final String text) {

        if(searchNameTask != null){
            searchNameTask.cancel(true);
            searchNameTask = null;
        }
        searchNameTask = new SearchNameTask(this,autoCompleteDataAdapter);
        searchNameTask.execute(text);
    }

    @Override
    public void startToSearch(String text) {
        //Toast.makeText(this,"search",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
        intent.putExtra(BaseInfo.IntentFlag.FLAG_MODE_SEARCH_NAME, text);
        startActivity(intent);
    }

    @Override
    public void refreshSearchEdit(String text) {
        Log.d(TAG, "refresh data");
        autoCompleteEditView(text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(searchNameTask != null){
            searchNameTask.cancel(true);
            searchNameTask = null;
        }
    }
}
