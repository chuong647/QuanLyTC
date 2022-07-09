package com.example.asmfinal.TabFragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
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
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmfinal.Adapter.Adapter_Spinner_Chi;
import com.example.asmfinal.Adapter.TabKhoanChiAdapter;
import com.example.asmfinal.DAO.DaoKhoanChi;
import com.example.asmfinal.DAO.DaoLoaiChi;
import com.example.asmfinal.Model.KhoanChi;
import com.example.asmfinal.Model.LoaiChi;
import com.example.asmfinal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;

public class TabFragmentKhoanChi extends Fragment {
    public static RecyclerView rcvkchi;
    FloatingActionButton floatbtnthemkchi;
    TabKhoanChiAdapter tabKhoanChiAdapter;
    ArrayList<KhoanChi> dataKchi;
    DaoKhoanChi daoKhoanChi;
    TextView edtngay;
    EditText edttien;
    DaoLoaiChi daoLoaiChi;
    ArrayList<LoaiChi> dataloaichi;
    Adapter_Spinner_Chi adapter_spinner_chi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =inflater.inflate(R.layout.tablayoutkhoanchi,container,false);
        rcvkchi = view.findViewById(R.id.rcvkchi);
        floatbtnthemkchi=view.findViewById(R.id.floatbtnthemkchi);
        rcvkchi.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcvkchi.setLayoutManager(layoutManager);
        dataKchi = new ArrayList<KhoanChi>();
        daoKhoanChi = new DaoKhoanChi(getContext());
        dataKchi= daoKhoanChi.readAll();
        tabKhoanChiAdapter = new TabKhoanChiAdapter(getActivity(),dataKchi);
        rcvkchi.setAdapter(tabKhoanChiAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rcvkchi);
        floatbtnthemkchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater1 = getActivity().getLayoutInflater();
                View view1 = inflater1.inflate(R.layout.dialoginsertkhoanchi,null);
                builder.setView(view1);
                final EditText edttenkhoanchi = view1.findViewById(R.id.edttenkhoanchi);
                edtngay = view1.findViewById(R.id.edtngay);
                 edttien = view1.findViewById(R.id.edttien);
                final EditText edtghichu = view1.findViewById(R.id.edtghichu);
                Button btnthemkhoanchi = view1.findViewById(R.id.btnthemkhoanchi);
                Button btnhuythem = view1.findViewById(R.id.btnhuythem);
                final Spinner spinermaloai = view1.findViewById(R.id.spinermaloai);
                edttien.addTextChangedListener(onTextChangedListener());
               daoLoaiChi = new DaoLoaiChi(getContext());
               dataloaichi = new ArrayList<LoaiChi>();
               dataloaichi = daoLoaiChi.readAll();
                adapter_spinner_chi = new Adapter_Spinner_Chi(getActivity(),dataloaichi);
                spinermaloai.setAdapter(adapter_spinner_chi);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                btnthemkhoanchi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenchi = edttenkhoanchi.getText().toString().trim();
                        String ghichu = edtghichu.getText().toString().trim();
                        String str = edttien.getText().toString().trim();
                        DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                        format.setParseBigDecimal(true);
                        BigDecimal number = null;
                        try {
                            number = (BigDecimal) format.parse(str);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        double so = Double.parseDouble(number + "");
                        String ngay = edtngay.getText().toString().trim();
                        int index = spinermaloai.getSelectedItemPosition();
                        String maloai = dataloaichi.get(index).getMaloai();
                        daoKhoanChi = new DaoKhoanChi(getContext());
                        daoKhoanChi.insert(new KhoanChi(null,tenchi,ngay,so,ghichu,maloai));
                        showdulieu();
                        Toast.makeText(getContext(), "THÊM THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                    }
                });
                edtngay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datepicker();
                    }
                });
                btnhuythem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

            }
        });
        return view;
    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            final String ma = dataKchi.get(position).getMatc();
            switch (direction){
                case LEFT:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Bạn có chắc muốn xóa không ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dataKchi = new ArrayList<KhoanChi>();
                            daoKhoanChi = new DaoKhoanChi(getContext());
                            daoKhoanChi.delete(ma);
                            dataKchi = daoKhoanChi.readAll();
                            tabKhoanChiAdapter = new TabKhoanChiAdapter(getActivity(),dataKchi);
                            rcvkchi.setAdapter(tabKhoanChiAdapter);
                            tabKhoanChiAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dataKchi = new ArrayList<KhoanChi>();
                            daoKhoanChi = new DaoKhoanChi(getContext());
                            dataKchi = daoKhoanChi.readAll();
                            tabKhoanChiAdapter = new TabKhoanChiAdapter(getActivity(),dataKchi);
                            rcvkchi.setAdapter(tabKhoanChiAdapter);
                            tabKhoanChiAdapter.notifyDataSetChanged();
                            dialog.dismiss();

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    break;
            }
        }
        public void onChildDraw (Canvas c, RecyclerView recyclerView,
                                 RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                 int actionState, boolean isCurrentlyActive){

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.Do))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
    public void datepicker(){
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd");
                calendar.set(year,month,dayOfMonth);
                String date = simpleDateFormat.format(calendar.getTime());
                edtngay.setText(date);
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    private void showdulieu(){
        dataKchi.clear();
        dataKchi=daoKhoanChi.readAll();
        tabKhoanChiAdapter= new TabKhoanChiAdapter(getActivity(),dataKchi);
        rcvkchi.setAdapter(tabKhoanChiAdapter);
        tabKhoanChiAdapter.notifyDataSetChanged();
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
                edttien.removeTextChangedListener(this);

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
                    edttien.setText(formattedString);
                    edttien.setSelection(edttien.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }

                edttien.addTextChangedListener(this);
            }
        };

    }
}
