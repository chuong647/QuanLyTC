package com.example.asmfinal.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asmfinal.Model.LoaiChi;
import com.example.asmfinal.R;

import java.util.ArrayList;

public class Adapter_Spinner_Chi extends BaseAdapter {
    Context context;
    ArrayList<LoaiChi> datalchi;

    public Adapter_Spinner_Chi(Context context, ArrayList<LoaiChi> datalchi) {
        this.context = context;
        this.datalchi = datalchi;
    }

    @Override
    public int getCount() {
        return datalchi.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView= inflater.inflate(R.layout.item_spinner,null);
        TextView tv_spinner = convertView.findViewById(R.id.tv_spinnermaloai);
        LoaiChi loaiChi = datalchi.get(position);
        tv_spinner.setText(loaiChi.getTenloai());
        return convertView;
    }
}
