package com.example.asmfinal.TabFragment;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmfinal.Adapter.Adapter_ThongKe;
import com.example.asmfinal.DAO.Daokhoanthuchi;
import com.example.asmfinal.Model.KhoanThuChi;
import com.example.asmfinal.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class TabFragmentTK_Thu extends Fragment {
    RecyclerView rcvthongke;
    TextView txtsumtien,txtngaydau,txtngaycuoi;
    ArrayList<KhoanThuChi> datakhoanthus;
    Daokhoanthuchi daokhoanthu;
    Adapter_ThongKe adapter_thongKe;
    PieChartView pieChartView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabfragmenttk_thu,container,false);
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        rcvthongke = view .findViewById(R.id.rcvthongke);
        txtsumtien = view.findViewById(R.id.txttentien);
        txtngaydau=view.findViewById(R.id.ngaydau);
        txtngaycuoi=view.findViewById(R.id.ngaycuoi);
        pieChartView = view.findViewById(R.id.chart);
        pieChartView.setVisibility(View.INVISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvthongke.setLayoutManager(linearLayoutManager);
        datakhoanthus = new ArrayList<KhoanThuChi>();
        daokhoanthu = new Daokhoanthuchi(getContext());
        datakhoanthus = daokhoanthu.getthongke();
        adapter_thongKe = new Adapter_ThongKe(getActivity(), datakhoanthus);
        rcvthongke.setAdapter(adapter_thongKe);
        txtsumtien.setText("Tổng Tiền:" + "\t" + decimalFormat.format((daokhoanthu.gettong())) + "VNĐ");
        txtngaydau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
                        calendar.set(year,month,dayOfMonth);
                        String date = simpleDateFormat.format(calendar.getTime());
                        txtngaydau.setText(date);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        txtngaycuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
                        calendar.set(year,month,dayOfMonth);
                        String date = simpleDateFormat.format(calendar.getTime());
                        txtngaycuoi.setText(date);
                        String ngaydau = txtngaydau.getText().toString();
                        datakhoanthus = new ArrayList<KhoanThuChi>();
                        datakhoanthus = daokhoanthu.gettheongay(ngaydau, simpleDateFormat.format(calendar.getTime()));
                        adapter_thongKe = new Adapter_ThongKe(getActivity(), datakhoanthus);
                        rcvthongke.setAdapter(adapter_thongKe);
                        double db = daokhoanthu.gettongtien(ngaydau,simpleDateFormat.format(calendar.getTime()));
                        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        decimalFormat.applyPattern("#,###,###,###");
                        txtsumtien.setText("Tổng Tiền:" + "\t" + decimalFormat.format(db) + "VNĐ");
                        List<SliceValue> pieData = new ArrayList<>();
                        double tong = daokhoanthu.gettong();
                        pieData.add(new SliceValue((float) db, Color.BLUE).setLabel("Tổng Ngày"));
                       pieData.add(new SliceValue((float) (tong-db), Color.GRAY).setLabel("Còn lại"));
                        PieChartData pieChartData = new PieChartData(pieData);
                        pieChartData.setHasLabels(true).setValueLabelTextSize(10);

                        pieChartData.setHasCenterCircle(true).setCenterText1("Thống Kê").setCenterText1FontSize(20).setCenterText1Color(Color.parseColor("#0097A7"));
                        pieChartView.setPieChartData(pieChartData);
                        pieChartView.setVisibility(View.VISIBLE);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });


        return view;
    }
}
