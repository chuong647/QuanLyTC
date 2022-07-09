package com.example.asmfinal.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asmfinal.Model.LoaiThuChi;
import com.example.asmfinal.R;

import java.util.ArrayList;

public class Adapter_Spinner extends BaseAdapter {
    Context context;
    ArrayList<LoaiThuChi> dataloai;

    public Adapter_Spinner(Context context, ArrayList<LoaiThuChi> dataloai) {
        this.context = context;
        this.dataloai = dataloai;
    }

    @Override
    public int getCount() {
        return dataloai.size();
    }

    @Override
    public Object getItem(int position) {
        return dataloai.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_spinner,null);
        TextView spinner =convertView.findViewById(R.id.tv_spinnermaloai);
        LoaiThuChi loaiThuChi = dataloai.get(position);
        spinner.setText(loaiThuChi.getTenloai());
        return convertView;
    }
}
