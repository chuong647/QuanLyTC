package com.example.asmfinal.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmfinal.DAO.DaoKhoanChi;
import com.example.asmfinal.Model.KhoanChi;
import com.example.asmfinal.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_ThongKeChi extends RecyclerView.Adapter<Adapter_ThongKeChi.ThongKe_ViewHolder> {
    Activity context;
    ArrayList<KhoanChi> datakhoanchi;
    DaoKhoanChi daokhoanchi;

    public Adapter_ThongKeChi(Activity context, ArrayList<KhoanChi> datakhoanthu) {
        this.context = context;
        this.datakhoanchi = datakhoanthu;
    }

    @NonNull
    @Override
    public ThongKe_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.itemthongkechi,parent,false);
        return new ThongKe_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThongKe_ViewHolder holder, int position) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        holder.txttenkhoan.setText(datakhoanchi.get(position).getTentc());
        holder.txttien.setText(decimalFormat.format(datakhoanchi.get(position).getTien())+"VNĐ");
        daokhoanchi = new DaoKhoanChi(context);


    }

    @Override
    public int getItemCount() {
        return datakhoanchi.size();
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

