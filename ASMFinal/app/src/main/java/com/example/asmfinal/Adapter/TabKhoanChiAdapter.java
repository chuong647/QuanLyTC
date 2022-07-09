package com.example.asmfinal.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmfinal.DAO.DaoKhoanChi;
import com.example.asmfinal.DAO.DaoLoaiChi;
import com.example.asmfinal.Model.KhoanChi;
import com.example.asmfinal.Model.LoaiChi;
import com.example.asmfinal.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.asmfinal.TabFragment.TabFragmentKhoanChi.rcvkchi;

public class TabKhoanChiAdapter extends RecyclerView.Adapter<TabKhoanChiAdapter.TabKhoanChiViewHolder> {
    Activity context;
    ArrayList<KhoanChi> dataKchi;
    DaoLoaiChi daoLoaiChi;
    DaoKhoanChi daoKhoanChi;
    ArrayList<LoaiChi> dataloaichi;
    TabKhoanChiAdapter tabKhoanChiAdapter;
    Adapter_Spinner_Chi adapter_spinner_chi;
    Spinner spinersuamaloai;
    TextView edtsuangay;
    EditText edtsuatien;

    public TabKhoanChiAdapter(Activity context, ArrayList<KhoanChi> dataKchi) {
        this.context = context;
        this.dataKchi = dataKchi;
    }

    @NonNull
    @Override
    public TabKhoanChiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.itemkhoanchi, parent, false);
        return new TabKhoanChiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TabKhoanChiViewHolder holder, final int position) {
        final DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        decimalFormat.applyPattern("#,###,###,###");
        holder.txttenkhoan.setText(dataKchi.get(position).getTentc());
        holder.txtngay.setText(dataKchi.get(position).getNgay());
        holder.txttien.setText(decimalFormat.format(dataKchi.get(position).getTien()));
        holder.txtghichu.setText(dataKchi.get(position).getGhichu());
        daoLoaiChi = new DaoLoaiChi(context);
        dataloaichi = new ArrayList<LoaiChi>();
        dataloaichi = daoLoaiChi.readAll();
        int vi_tri = Integer.parseInt(dataKchi.get(position).getMaloai());
        holder.txtmaloai.setText(daoLoaiChi.giaTriten(vi_tri + ""));
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view1 = inflater.inflate(R.layout.dialogeditkhoanchi, null);
                builder.setView(view1);
                final EditText edtsuatenkhoanchi = view1.findViewById(R.id.edtsuatenkhoanchi);
                spinersuamaloai = view1.findViewById(R.id.spinersuamaloai);
                edtsuatien = view1.findViewById(R.id.edtsuatien);
                final EditText edtsuaghichu = view1.findViewById(R.id.edtsuaghichu);
                edtsuangay = view1.findViewById(R.id.edtsuangay);
                Button btnsuakhoanchi = view1.findViewById(R.id.btnsuakhoanchi);
                Button btnhuysua = view1.findViewById(R.id.btnhuysua);
                daoLoaiChi = new DaoLoaiChi(context);
                dataloaichi = new ArrayList<LoaiChi>();
                dataloaichi = daoLoaiChi.readAll();
                adapter_spinner_chi = new Adapter_Spinner_Chi(context, dataloaichi);
                spinersuamaloai.setAdapter(adapter_spinner_chi);
                String tenmaloai = daoLoaiChi.giaTriten(dataKchi.get(position).getMaloai());
                for (int i = 0; i < dataloaichi.size(); i++) {
                    if (dataloaichi.get(i).getTenloai().equals(tenmaloai)) {
                        spinersuamaloai.setSelection(i);
                        break;
                    }
                }
                edtsuaghichu.setText(dataKchi.get(position).getGhichu());
                edtsuatenkhoanchi.setText(dataKchi.get(position).getTentc());
                edtsuatien.setText(decimalFormat.format(dataKchi.get(position).getTien()));
                edtsuatien.addTextChangedListener(onTextChangedListener());
                edtsuangay.setText(dataKchi.get(position).getNgay());
                final AlertDialog dialog = builder.create();
                dialog.show();
                btnsuakhoanchi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String machi = dataKchi.get(position).getMatc();
                        String tenkchi = edtsuatenkhoanchi.getText().toString().trim();
                        String ghichu = edtsuaghichu.getText().toString().trim();
                        String ngay = edtsuangay.getText().toString().trim();
                        String str = edtsuatien.getText().toString().trim();
                        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        format.setParseBigDecimal(true);
                        BigDecimal number = null;
                        try {
                            number = (BigDecimal) format.parse(str);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        double so = Double.parseDouble(number + "");
                        int index = spinersuamaloai.getSelectedItemPosition();
                        dataloaichi = new ArrayList<LoaiChi>();
                        dataloaichi = daoLoaiChi.readAll();
                        String maloai = dataloaichi.get(index).getMaloai();
                        daoKhoanChi = new DaoKhoanChi(context);
                        daoKhoanChi.update(machi, tenkchi, ngay, so, ghichu, maloai);
                        dataKchi = new ArrayList<KhoanChi>();
                        dataKchi = daoKhoanChi.readAll();
                        tabKhoanChiAdapter = new TabKhoanChiAdapter(context, dataKchi);
                        rcvkchi.setAdapter(tabKhoanChiAdapter);
                        Toast.makeText(context, "CẬP NHẬt THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                });
                edtsuangay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
                });
                btnhuysua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataKchi.size();
    }

    public static class TabKhoanChiViewHolder extends RecyclerView.ViewHolder {
        public TextView txttenkhoan, txtngay, txttien, txtghichu, txtmaloai;
        public ImageView imgedit;

        public TabKhoanChiViewHolder(@NonNull View itemView) {
            super(itemView);
            txttenkhoan = itemView.findViewById(R.id.txttenkhoanchi);
            txtngay = itemView.findViewById(R.id.txtngaychi);
            txttien = itemView.findViewById(R.id.txttienchi);
            txtghichu = itemView.findViewById(R.id.txtghichuchi);
            txtmaloai = itemView.findViewById(R.id.txtmaloai);
            imgedit = itemView.findViewById(R.id.imgedit);
        }
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

