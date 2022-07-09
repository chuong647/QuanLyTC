package com.example.asmfinal.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmfinal.DAO.Daokhoanthuchi;
import com.example.asmfinal.Model.KhoanThuChi;
import com.example.asmfinal.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_ThongKe extends RecyclerView.Adapter<Adapter_ThongKe.ThongKe_ViewHolder> {
    Activity context;
    ArrayList<KhoanThuChi> datakhoanthu;
    Daokhoanthuchi daokhoanthuchi;

    public Adapter_ThongKe(Activity context, ArrayList<KhoanThuChi> datakhoanthu) {
        this.context = context;
        this.datakhoanthu = datakhoanthu;
    }

    @NonNull
    @Override
    public ThongKe_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.itemthongke,parent,false);
        return new ThongKe_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKe_ViewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        holder.txttenkhoan.setText(datakhoanthu.get(position).getTentc());
        holder.txttien.setText(decimalFormat.format(datakhoanthu.get(position).getTien())+"VNĐ");
        daokhoanthuchi = new Daokhoanthuchi(context);


    }

    @Override
    public int getItemCount() {
        return datakhoanthu.size();
    }

    public static class ThongKe_ViewHolder extends RecyclerView.ViewHolder{
        TextView txttenkhoan,txttien;
        public ThongKe_ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttenkhoan = itemView.findViewById(R.id.txttenkhoan);
            txttien = itemView.findViewById(R.id.txttien);

        }
    }
}

