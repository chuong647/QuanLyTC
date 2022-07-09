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

import com.example.asmfinal.Adapter.Adapter_ThongKeChi;
import com.example.asmfinal.DAO.DaoKhoanChi;
import com.example.asmfinal.Model.KhoanChi;
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

public class TabFragmentTK_Chi extends Fragment {
    RecyclerView rcvthongke;
    TextView txtsumtien,txtngaydau,txtngaycuoi;
    ArrayList<KhoanChi> datakhoanchi;
    DaoKhoanChi daokhoanchi;
    Adapter_ThongKeChi adapter_thongKechi;
    PieChartView pieChartView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tabfragmenttk_chi,container,false);
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        rcvthongke = view .findViewById(R.id.rcvthongke);
        txtsumtien = view.findViewById(R.id.txttentien);
        txtngaydau=view.findViewById(R.id.ngaydau);
        txtngaycuoi=view.findViewById(R.id.ngaycuoi);
        pieChartView = view.findViewById(R.id.chart);
        pieChartView.setVisibility(View.INVISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rcvthongke.setLayoutManager(linearLayoutManager);
        datakhoanchi = new ArrayList<KhoanChi>();
        daokhoanchi = new DaoKhoanChi(getContext());
        datakhoanchi = daokhoanchi.getthongkechi();
        adapter_thongKechi = new Adapter_ThongKeChi(getActivity(), datakhoanchi);
        rcvthongke.setAdapter(adapter_thongKechi);
        txtsumtien.setText("Tổng Tiền:" + "\t" + decimalFormat.format((daokhoanchi.gettong()) )+ "VNĐ");
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
                        String ngaydau1 = txtngaydau.getText().toString().trim();
                        //String ngaycuoi1 = txtngaycuoi.getText().toString().trim();
                        datakhoanchi = new ArrayList<KhoanChi>();
                        datakhoanchi = daokhoanchi.gettheongay(ngaydau1, simpleDateFormat.format(calendar.getTime()));
                        adapter_thongKechi = new Adapter_ThongKeChi(getActivity(), datakhoanchi);
                        rcvthongke.setAdapter(adapter_thongKechi);
                        double db = daokhoanchi.gettongtienchi(ngaydau1,simpleDateFormat.format(calendar.getTime()));
                        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        decimalFormat.applyPattern("#,###,###,###");
                        txtsumtien.setText("Tổng Tiền:" + "\t" + db + "VNĐ");
                        List<SliceValue> pieData = new ArrayList<>();
                        double tong = daokhoanchi.gettong();
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
