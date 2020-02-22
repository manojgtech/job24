package com.example.tripstory;



import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class Jobsadaptor extends RecyclerView.Adapter<Jobsadaptor.ViewHolder> {
    private List<Jobs>list_data;
    private Context context;

    public Jobsadaptor(List<Jobs> list_data, Context context) {
        this.list_data = list_data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.joblist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Jobs listData=list_data.get(position);

//
//        Picasso.with(context)
//                .load(listData
//                        .getImage_url())
//                .into(holder.img);

        holder.jobtitle.setText(listData.getTitle());
        holder.jobtype.setText(listData.getExp());
        holder.jobcomp.setText(listData.getCompany());
        holder.jobloc.setText(listData.getLocation());
        holder.jobdate.setText(listData.getDate());
        holder.jobcatg.setText(listData.getCategory());

    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView jobtitle,jobtype,jobcomp,jobloc,jobdate,jobcatg;
        public ViewHolder(View itemView) {
            super(itemView);

            jobtitle=(TextView)itemView.findViewById(R.id.jobtitle);
            jobcomp=(TextView)itemView.findViewById(R.id.jobcompany);
            jobtype=(TextView)itemView.findViewById(R.id.jobtype);
            jobloc=(TextView)itemView.findViewById(R.id.joblocation);
            jobdate=(TextView)itemView.findViewById(R.id.postdate);
            jobcatg=(TextView)itemView.findViewById(R.id.jobcat);

        }
    }
}