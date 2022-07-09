package com.example.asmfinal.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmfinal.DAO.Daokhoanthuchi;
import com.example.asmfinal.DAO.Daoloaithuchi;
import com.example.asmfinal.Model.KhoanThuChi;
import com.example.asmfinal.Model.LoaiThuChi;
import com.example.asmfinal.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.asmfinal.TabFragment.TabFragmentkhoanthu.rcv;


public class TabKhoanThuAdapter extends RecyclerView.Adapter<TabKhoanThuViewHolder> {
    Context context;
    ArrayList<KhoanThuChi> datakhoanthu;
    ArrayList<LoaiThuChi> dataloaithu;
    Daokhoanthuchi daokhoanthuchi;
    Daoloaithuchi daoloaithuchi;
    TabKhoanThuAdapter tabKhoanThuAdapter;
    TextView edtsuangay;
    Adapter_Spinner adapter_spinner;
    Spinner spinersuamaloai;
    EditText edtsuatien;


    public TabKhoanThuAdapter(Context context, ArrayList<KhoanThuChi> datakhoanthu) {
        this.context = context;
        this.datakhoanthu = datakhoanthu;
    }

    @NonNull
    @Override
    public TabKhoanThuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemkhoanthu, parent, false);
        return new TabKhoanThuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabKhoanThuViewHolder holder, final int position) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        holder.txttenkhoan.setText(datakhoanthu.get(position).getTentc());
        holder.txtngay.setText(datakhoanthu.get(position).getNgay());
        holder.txttien.setText(decimalFormat.format(datakhoanthu.get(position).getTien()));
        holder.txtghichu.setText(datakhoanthu.get(position).getGhichu());
        Daoloaithuchi daoloaithuchi = new Daoloaithuchi(context);
        dataloaithu = new ArrayList<>();
        dataloaithu = daoloaithuchi.readAll();
        int idmaloai = Integer.parseInt(datakhoanthu.get(position).getMaloai());
        holder.txtmaloai.setText(daoloaithuchi.getTen(idmaloai + ""));
//        Toast.makeText(context, "Ma"+idmaloai, Toast.LENGTH_SHORT).show();
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view1 = inflater.inflate(R.layout.dialogeditkhoanthu, null);
                builder.setView(view1);
                final EditText edtsuatenkhoanthu = view1.findViewById(R.id.edtsuatenkhoanthu);
                spinersuamaloai = view1.findViewById(R.id.spinersuamaloai);
                 edtsuatien = view1.findViewById(R.id.edtsuatien);
                final EditText edtsuaghichu = view1.findViewById(R.id.edtsuaghichu);
                edtsuangay = view1.findViewById(R.id.edtsuangay);
                Button btnsuakhoanthu = view1.findViewById(R.id.btnsuakhoanthu);
                Button btnhuysua = view1.findViewById(R.id.btnhuysua);


                Daoloaithuchi daoloai = new Daoloaithuchi(context);
                ArrayList<LoaiThuChi> dataloaithus = new ArrayList<>();
                dataloaithus = daoloai.readAll();
                adapter_spinner = new Adapter_Spinner(context, dataloaithus);
                spinersuamaloai.setAdapter(adapter_spinner);
                // int vi_tri = Integer.parseInt(datakhoanthu.get(position).getMaloai());

                //spinersuamaloai.setSelection(vi_tri-1);
                // int idmaloais = Integer.parseInt(datakhoanthu.get(position).getMaloai());
                String ten_ma = daoloai.getTen(datakhoanthu.get(position).getMaloai());
//                selectSpinnerValue(spinersuamaloai,ten_ma);

                for (int i = 0; i < dataloaithus.size(); i++) {
                    if (dataloaithus.get(i).getTenloai().equals(ten_ma)) {
                        spinersuamaloai.setSelection(i);
//                        Toast.makeText(context, "Mã"+i, Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

//                Toast.makeText(context, "Mã"+index, Toast.LENGTH_SHORT).show();
//                spinersuamaloai.setSelection(index);
                DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                format.applyPattern("#,###,###,###");
                edtsuaghichu.setText(datakhoanthu.get(position).getGhichu());
                edtsuatenkhoanthu.setText(datakhoanthu.get(position).getTentc());
                edtsuatien.setText(format.format(datakhoanthu.get(position).getTien()));
//
                edtsuangay.setText(datakhoanthu.get(position).getNgay());
                edtsuatien.addTextChangedListener(onTextChangedListener());
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                btnsuakhoanthu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String matc = datakhoanthu.get(position).getMatc();
                        String tenkhoan = edtsuatenkhoanthu.getText().toString().trim();



//                        double tien=0.0;
//                        try {
//
////                            l = DecimalFormat.getNumberInstance().parse(str).doubleValue();
//                            tien = DecimalFormat.getNumberInstance().parse(str).doubleValue();
//                            System.out.println(tien); //111111.23
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
                        String str = edtsuatien.getText().toString().trim();
                        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        format.setParseBigDecimal(true);
                        BigDecimal number = null;
                        try {
                            number = (BigDecimal) format.parse(str);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        double so = Double.parseDouble(number+"");
                        String ghichu = edtsuaghichu.getText().toString().trim();
                        int index = spinersuamaloai.getSelectedItemPosition();
//                        Toast.makeText(context, "Ma index"+index, Toast.LENGTH_SHORT).show();
                        datakhoanthu = new ArrayList<KhoanThuChi>();
                        String idloai = dataloaithu.get(index).getMaloai();
                        String ngay = edtsuangay.getText().toString();
                        daokhoanthuchi = new Daokhoanthuchi(context);
                        daokhoanthuchi.update(matc, tenkhoan, ngay, so, ghichu, idloai);
                        Toast.makeText(context, "CẬP NHẬT THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                        showduelieu();
                        alertDialog.cancel();
                    }
                });
                edtsuangay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datapicker();
                    }
                });
                btnhuysua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return datakhoanthu.size();
    }

    //    @Override
//    public long getItemId(int position) {
//        return super.getItemId(position);
//    }
//
//    private void selectSpinnerValue(Spinner spinner, String myString)
//    {
//        int index = 0;
//        for(int i = 0; i < spinner.getCount(); i++){
//            if(spinner.getItemAtPosition(i).toString().equals(myString)){
//                spinner.setSelection(i);
//                //index = i;
//                break;
//            }
//        }
//        //return index;
//    }
    private void datapicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year2, int month2, int dayOfMonth2) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                calendar.set(year2, month2, dayOfMonth2);
                String date = dateFormat.format(calendar.getTime());
                edtsuangay.setText(date);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void showduelieu() {
        datakhoanthu.clear();
        datakhoanthu = daokhoanthuchi.readAll();
        tabKhoanThuAdapter = new TabKhoanThuAdapter(context, datakhoanthu);
        rcv.setAdapter(tabKhoanThuAdapter);
    }

    private TextWatcher onTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtsuatien.removeTextChangedListener(this);

                try {
                    String originalString = s.toString();

                    Long longval;
                    if (originalString.contains(",")) {
                        originalString = originalString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(originalString);

                    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    formatter.applyPattern("#,###,###,###");
                    String formattedString = formatter.format(longval);

                    //setting text after format to EditText
                    edtsuatien.setText(formattedString);
                    edtsuatien.setSelection(edtsuatien.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edtsuatien.addTextChangedListener(this);
            }
        };
    }
}


