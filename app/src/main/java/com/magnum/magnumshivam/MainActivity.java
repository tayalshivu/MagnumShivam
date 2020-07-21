package com.magnum.magnumshivam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.magnum.magnumshivam.adapter.MainAdapter;
import com.magnum.magnumshivam.data.MainData;
import com.magnum.magnumshivam.data.MainItem;
import java.util.ArrayList;
import java.util.Arrays;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<MainItem> dataArrayList = new ArrayList<>();
    MainAdapter adapter;
    int page=1 , limit=10;
    String q = "saransh";

    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        nestedScrollView=findViewById(R.id.scrool_view);
        recyclerView=findViewById(R.id.recycler_view);
        progressBar=findViewById(R.id.progress_bar);

        adapter = new MainAdapter(MainActivity.this,dataArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        detData(page,limit);

//        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
//                    page++;
//                    progressBar.setVisibility(View.VISIBLE);
//                    detData(page,limit);
//                }
//            }
//        });


    }




    private void detData(final int page, final int limit) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainInterface mainInterface = retrofit.create(MainInterface.class);

        Call<MainData> call = mainInterface.STRING_CALL();


        call.enqueue(new Callback<MainData>() {
            @Override
            public void onResponse(Call<MainData> call, Response<MainData> response) {
                if (response.isSuccessful() && response.body()!=null){

                    try {
                        progressBar.setVisibility(View.GONE);
                        MainData mainData = response.body();
                        dataArrayList = new ArrayList<>(Arrays.asList(mainData.getItems()));
                        Log.d(TAG,""+dataArrayList.size());
                        adapter = new MainAdapter(MainActivity.this,dataArrayList);
                        recyclerView.setAdapter(adapter);

                        Intent intent = getIntent();
                        String name = intent.getStringExtra("name");
                        adapter=new MainAdapter(MainActivity.this,dataArrayList);
                        recyclerView.setAdapter(adapter);
                        adapter.getFilter().filter(name);
                    }
                    catch (Exception e){
                        Log.d(TAG,""+e);
                    }

                }
                else{
                    Snackbar.make(findViewById(android.R.id.content),"Server error Try Restarting App",Snackbar.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<MainData> call, Throwable t) {
                Snackbar.make(findViewById(android.R.id.content),"Network Error",Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

}

