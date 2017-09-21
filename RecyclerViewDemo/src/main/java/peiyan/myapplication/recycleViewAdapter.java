package peiyan.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;


public class recycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder > {

    private static List<myBean.DataBean> list;
    private Context context;
    //创建接口
    private static onItemClickListener monItemClickListener;
    private  static OnItemLongClickListener mOnItemLongClickListener;

    //设置recycleview的某个监听
    public  void setOnItemClickListener(onItemClickListener onItemClickListener){
        monItemClickListener=onItemClickListener;

    }
    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }

    public recycleViewAdapter(List<myBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    //加载布局。
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        view = LayoutInflater.from(context).inflate(R.layout.item, null);
        viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    //赋值
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        myBean.DataBean bean = list.get(position);
        //        name, occupation, age, introduction;
        final myViewHolder myViewHolder = (myViewHolder) holder;
        myViewHolder.userName.setText(list.get(position).userName+"|"+"已实名认证");
        myViewHolder.userAge.setText(list.get(position).userAge+"岁");
        myViewHolder.occupation.setText(list.get(position).occupation);
        myViewHolder.introduction.setText(list.get(position).introduction);


        Glide.with(context).load(list.get(position).img).into(myViewHolder.img);


    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    static class myViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userAge, occupation, introduction;
        ImageView img;

        public myViewHolder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            userAge = (TextView) itemView.findViewById(R.id.userAge);
            occupation = (TextView) itemView.findViewById(R.id.occupation);
            introduction = (TextView) itemView.findViewById(R.id.introduction);
            img = (ImageView) itemView.findViewById(R.id.img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(monItemClickListener!=null){
                        monItemClickListener.onItemclic(v,list.get(getLayoutPosition()).introduction);

                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemLongClickListener.onItemLongClick(view,getLayoutPosition());
                    return true;
                }
            });

        }
    }







         //点击事件recycleview点击事件的接口
         interface onItemClickListener{
             /**
              * 抽象方法，当recycleview某个被点击的时候回调
              * @param view  点击的item对象
              * @param data  点击时的数据
              */
             void onItemclic(View view,String data);

         }
    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
    }



}



