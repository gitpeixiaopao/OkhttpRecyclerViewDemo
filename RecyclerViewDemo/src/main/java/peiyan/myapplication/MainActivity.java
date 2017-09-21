package peiyan.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String path = "http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=20&gender=2&ts=1871746850&page=1";
    private LinearLayoutManager linearLayoutManager;
    private List<myBean.DataBean> list;
    private int page = 0;
    private recycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        initData();
        OkHttpManager.getInstance().asyncJsonStringByURL(path, new OkHttpManager.Func1() {
            @Override
            public void onResponse(String result) {

                list = new ArrayList();
                Gson gson = new Gson();
                myBean bean = gson.fromJson(result, myBean.class);
                list = bean.data;
                adapter = new recycleViewAdapter(list, MainActivity.this);
                recyclerView.setAdapter(adapter);
                linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                for (int i = 0; i < list.size(); i++) {

                    com.orhanobut.logger.Logger.d(list.get(i).userAge);

                }

                adapter.setOnItemClickListener(new recycleViewAdapter.onItemClickListener() {
                    @Override
                    public void onItemclic(View view, String data) {


                        Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                    }
                });
                //长按展示异常
                adapter.setOnItemLongClickListener(new recycleViewAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {

                        Object o=null;
                        o.toString();
                    }
                });

                recyclerView.setItemAnimator(new DefaultItemAnimator());


            }
        });


    }


    private void initData() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition == list.size() - 1) {
                    page++;
                    OkHttpManager.getInstance().asyncJsonStringByURL(path, new OkHttpManager.Func1() {
                        @Override
                        public void onResponse(String result) {

                            List<myBean.DataBean> list2 = new ArrayList();
                            Gson gson = new Gson();
                            myBean bean = gson.fromJson(result, myBean.class);
                            list2 = bean.data;
                            for (int i = 0; i < list2.size(); i++) {

                                list.add(list2.get(i));


                            }

                            adapter.notifyDataSetChanged();


                        }
                    });

                }
            }
        });

    }

}
