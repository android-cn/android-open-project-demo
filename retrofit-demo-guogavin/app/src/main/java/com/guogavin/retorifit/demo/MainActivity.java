package com.guogavin.retorifit.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener{

    ListView list;
    String[] interfaceName = {"callIndexSynchronous","callIndexSynchronousResponse","callIndexSynchronousObservable",
            "index.php","gavin2/Index.php","gavin2/query.php","gavin2/query.phpPathQuery","gavin2/query.phpMap",
            "postParams.phpBody","postParams.php","postParams.phpMap","postParams.php2","postParams.php2Map",
            "callIndexAddHeaders","callIndexAddHeaders2","callIndexAddHeaders3"};
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView)findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,interfaceName);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = adapter.getItem(position);
        if(item.equals("callIndexSynchronous")){
            RetrofitService.getInstance().callIndexSynchronous();
        }else if(item.equals("callIndexSynchronousResponse")){
            RetrofitService.getInstance().callIndexSynchronousResponse();
        }else if(item.equals("index.php")) {
            RetrofitService.getInstance().callIndex();
        }else if(item.equals("callIndexSynchronousObservable")){
            RetrofitService.getInstance().callIndexSynchronousObservable();
        }else if(item.equals("gavin2/Index.php")) {
            RetrofitService.getInstance().callGavin2Index();
        }else if(item.equals("gavin2/query.php")) {
            RetrofitService.getInstance().callGavin2Query();
        }else if(item.equals("gavin2/query.phpPathQuery")) {
            RetrofitService.getInstance().callGavin2PathQuery();
        }else if(item.equals("gavin2/query.phpMap")) {
            RetrofitService.getInstance().callGavin2QueryMap();
        }else if(item.equals("postParams.phpBody")) {
            RetrofitService.getInstance().callPostParamsBody();
        }else if(item.equals("postParams.php")) {
            RetrofitService.getInstance().callPostParams();
        }else if(item.equals("postParams.phpMap")) {
            RetrofitService.getInstance().callPostParamsMap();
        }else if(item.equals("postParams.php2")) {
            RetrofitService.getInstance().callPostParams2();
        }else if(item.equals("postParams.php2Map")) {
            RetrofitService.getInstance().callPostParams2Map();
        }else if(item.equals("callIndexAddHeaders")){
            RetrofitService.getInstance().callIndexAddHeaders();
        }else if(item.equals("callIndexAddHeaders2")){
            RetrofitService.getInstance().callIndexAddHeaders2();
        }else if(item.equals("callIndexAddHeaders3")){
            RetrofitService.getInstance().callIndexAddHeaders3();
        }
    }
}
