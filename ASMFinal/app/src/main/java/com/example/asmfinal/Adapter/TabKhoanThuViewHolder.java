package com.example.asmfinal.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmfinal.R;

public class TabKhoanThuViewHolder extends RecyclerView.ViewHolder {


    public TextView txttenkhoan,txtngay,txttien,txtghichu,txtmaloai;
    public ImageView imgedit;
    public TabKhoanThuViewHolder(@NonNull View itemView) {

        super(itemView);
        txttenkhoan = itemView.findViewById(R.id.txttenkhoan);
        txtngay = itemView.findViewById(R.id.txtngay);
        txttien = itemView.findViewById(R.id.txttien);
        txtghichu = itemView.findViewById(R.id.txtghichu);
        txtmaloai = itemView.findViewById(R.id.txtmaloai);
        imgedit = itemView.findViewById(R.id.imgedit);
    }
}
