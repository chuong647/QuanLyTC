package com.example.asmfinal.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.asmfinal.R;

public class TabLoaiThuViewHolder extends RecyclerView.ViewHolder {
    public TextView txttenloai;
    public TextView txttrangthai;
    public ImageView imgedit;


    public TabLoaiThuViewHolder(View itemView) {
        super(itemView);
        txttenloai = itemView.findViewById(R.id.txttenloai);
        txttrangthai = itemView.findViewById(R.id.txttrangthai);
        imgedit = itemView.findViewById(R.id.imgedit);

    }
}
