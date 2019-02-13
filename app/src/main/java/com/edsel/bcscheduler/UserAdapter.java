package com.edsel.bcscheduler;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//import static com.example.signoril.earthquakesroomrecyclerview.MainActivity.adapter;
//import static com.example.signoril.earthquakesroomrecyclerview.MainActivity.recyclerView;

//import static com.example.signoril.earthquakesroomrecyclerview.MainActivity.context;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context mContext;  //get the parent context for use in startActivity and to pass to deleteEQID and Room.databaseBuilders
    List<CourseRecord> items;
    AppDatabaseMod db;
    CourseRecord fred;

    public UserAdapter(List<CourseRecord> items) {
        this.items = items;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_row,parent,false);
        mContext = parent.getContext();
        db = Room.databaseBuilder(mContext, AppDatabaseMod.class,AppDatabaseMod.NAME).build();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
//        holder.mag.setText(" " + items.get(position).getMagnitude());
//        holder.time.setText(" " + items.get(position).getDateTime());
//        holder.lat.setText(" " + items.get(position).getLat());
//        holder.lon.setText(" " + items.get(position).getLon());
//        //Toast toast = Toast.makeText(MainActivity.context, "Mag :" + items.get(position).getMagnitude(), Toast.LENGTH_SHORT);
//        //toast.show();
//        final int index = position;
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                items.get(index);
//                fred = items.get(index);
//                //Toast toast = Toast.makeText(mContext, "Mag :" + items.get(index).getMagnitude(), Toast.LENGTH_SHORT);
//                //toast.show();
//                Intent map = new Intent(mContext, MapActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("earthquake",items.get(index));
//                map.putExtras(bundle);
//                String m = "latlon" + items.get(index).getLat() + " " + items.get(index).getLon();
//                mContext.startActivity(map);
//            }
//        });
//
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            public boolean onLongClick(View v) {
//                items.get(index);
//                fred = items.get(index);
//                new  deleteEQID( db, recyclerView, adapter,  mContext ,  items, index).execute(items.get(index).getEQID());
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        else
            return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
//        public TextView mag;
//        public TextView time;
//        public TextView lat;
//        public TextView lon;

        public ViewHolder(View itemView) {
            super(itemView);
//            mag = itemView.findViewById(R.id.mag);
//            time= itemView.findViewById(R.id.datetime);
//            lat = itemView.findViewById(R.id.lat);
//            lon = itemView.findViewById(R.id.lon);
        }
    }
}
