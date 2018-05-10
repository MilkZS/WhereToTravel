package com.milkzs.android.wheretotravel.Task;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.milkzs.android.wheretotravel.Base.PlaceListInfo;
import com.milkzs.android.wheretotravel.Titanic.Titanic;
import com.milkzs.android.wheretotravel.Titanic.TitanicTextView;
import com.milkzs.android.wheretotravel.Tool.AnalysisJsonData;
import com.milkzs.android.wheretotravel.Tool.DataRequest;
import com.milkzs.android.wheretotravel.adapter.PlaceAdapter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by milkdz on 2018/4/21.
 */

public class QueryDataTask extends AsyncTask<Void, Void, ArrayList<PlaceListInfo>> {

    private String TAG = "QueryDataTask";
    private Context context;
    private PlaceAdapter placeAdapter;
    private TitanicTextView titanicTextView;
    private Titanic titanic = new Titanic();
    private RecyclerView recyclerView;
    private int position;

    public QueryDataTask(Context context, PlaceAdapter placeAdapter
            , TitanicTextView titanicTextView,RecyclerView recyclerView,int position) {
        this.titanicTextView = titanicTextView;
        this.context = context;
        this.placeAdapter = placeAdapter;
        this.recyclerView = recyclerView;
        this.position = position;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showLoad();
    }

    @Override
    protected ArrayList<PlaceListInfo> doInBackground(Void... voids) {

        try {
            String jsonString = DataRequest.getResponseFromHttpUrl(
                    DataRequest.buildUriForShowApi());
            return AnalysisJsonData.getDataFromJson(jsonString, context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<PlaceListInfo> arrayList) {
        super.onPostExecute(arrayList);
        if (arrayList != null) {
            hideLoad();
            recyclerView.getLayoutManager().scrollToPosition(position - 2);
            placeAdapter.swapData(arrayList, null, PlaceAdapter.MODE_LIST);
            Log.d(TAG,"item is " + position);
        }
    }

    private void showLoad() {
        titanicTextView.setVisibility(View.VISIBLE);
        titanic.start(titanicTextView);
    }

    private void hideLoad() {
        titanic.cancel();
        titanicTextView.setVisibility(View.INVISIBLE);
    }
}
