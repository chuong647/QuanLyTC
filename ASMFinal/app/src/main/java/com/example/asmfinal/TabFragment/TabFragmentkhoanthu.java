package com.example.asmfinal.TabFragment;

import android.app.Activity;
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

import com.example.asmfinal.Adapter.Adapter_Spinner;
import com.example.asmfinal.Adapter.TabKhoanThuAdapter;
import com.example.asmfinal.DAO.Daokhoanthuchi;
import com.example.asmfinal.DAO.Daoloaithuchi;
import com.example.asmfinal.Model.KhoanThuChi;
import com.example.asmfinal.Model.LoaiThuChi;
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

public class TabFragmentkhoanthu extends Fragment {
   public static RecyclerView rcv;
    public static TabKhoanThuAdapter tabKhoanThuAdapter;
    ArrayList<KhoanThuChi> datakhoanthu;
    ArrayList<LoaiThuChi> dataloaithu = new ArrayList<>();
    Adapter_Spinner adapter_spinner;
    Daokhoanthuchi daokhoanthuchi;
    Daoloaithuchi daoloaithuchi;
    FloatingActionButton floatbtnthem;
    TextView edtngay;
    EditText edttien;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayoutkhoanthu,container,false);
        rcv= view.findViewById(R.id.rcv);
        floatbtnthem = view.findViewById(R.id.floatbtnthem);
        rcv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),LinearLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(layoutManager);

        daokhoanthuchi = new Daokhoanthuchi(getContext());
        datakhoanthu = new ArrayList<KhoanThuChi>();
        datakhoanthu = daokhoanthuchi.readAll();
        tabKhoanThuAdapter= new TabKhoanThuAdapter(getActivity(),datakhoanthu);
        rcv.setAdapter(tabKhoanThuAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rcv);
        floatbtnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater layoutInflater = ((Activity)getContext()).getLayoutInflater();
                View view1 = layoutInflater.inflate(R.layout.dialogthemkhoan,null);
                builder.setView(view1);
                final EditText edttenkhoanthu = view1.findViewById(R.id.edttenkhoanthu);
                final Spinner spinermaloai = view1.findViewById(R.id.spinermaloai);
                edtngay = view1.findViewById(R.id.edtngay);
                 edttien = view1.findViewById(R.id.edttien);
                final EditText edtghichu = view1.findViewById(R.id.edtghichu);
                Button btnthemkhoanthu = view1.findViewById(R.id.btnthemkhoanthu);
                Button btnhuythem = view1.findViewById(R.id.btnhuythem);
                edttien.addTextChangedListener(onTextChangedListener());
                daoloaithuchi = new Daoloaithuchi(getContext());
                dataloaithu = daoloaithuchi.readAll();
                adapter_spinner = new Adapter_Spinner(getContext(),dataloaithu);
                spinermaloai.setAdapter(adapter_spinner);
                final AlertDialog alertDialog =builder.create();
                alertDialog.show();
                btnthemkhoanthu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenkhoan = edttenkhoanthu.getText().toString();
                        String ngay = edtngay.getText().toString();
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
                        int index = spinermaloai.getSelectedItemPosition();
                        String maloai = dataloaithu.get(index).getMaloai();
                        String ghichu = edtghichu.getText().toString();
                        daokhoanthuchi = new Daokhoanthuchi(getContext());
                        daokhoanthuchi.insert(new KhoanThuChi(null,tenkhoan,ngay,so,ghichu,maloai));
                        showduelieu();
                        Toast.makeText(getContext(), "THÊm THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                        alertDialog.cancel();
                    }
                });
                edtngay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        datapicker();
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
        final int positon = viewHolder.getAdapterPosition();
        final String matc = datakhoanthu.get(positon).getMatc();
        switch (direction){
            case LEFT:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có chắc muốn xóa không?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                   daokhoanthuchi = new Daokhoanthuchi(getContext());
                   datakhoanthu = new ArrayList<KhoanThuChi>();
                  daokhoanthuchi.delete(matc);
                  datakhoanthu = daokhoanthuchi.readAll();
                  tabKhoanThuAdapter = new TabKhoanThuAdapter(getContext(),datakhoanthu);
                  rcv.setAdapter(tabKhoanThuAdapter);
                  dialog.cancel();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        daokhoanthuchi = new Daokhoanthuchi(getContext());
                        datakhoanthu = new ArrayList<KhoanThuChi>();
                        datakhoanthu = daokhoanthuchi.readAll();
                        tabKhoanThuAdapter = new TabKhoanThuAdapter(getContext(),datakhoanthu);
                        rcv.setAdapter(tabKhoanThuAdapter);
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }
       }
       public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){

           new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                   .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.Do))
                   .addSwipeLeftActionIcon(R.drawable.ic_delete_black_24dp)
                   .create()
                   .decorate();

           super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
       }

   };

    private  void datapicker(){
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int dayOfMonth1) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                calendar.set(year1,month1,dayOfMonth1);
                String date = simpleDateFormat.format(calendar.getTime());
                edtngay.setText(date);

            }
        },year,month,day);
        datePickerDialog.show();

    }
    public void showduelieu(){
        datakhoanthu.clear();
        datakhoanthu = daokhoanthuchi.readAll();
        tabKhoanThuAdapter = new TabKhoanThuAdapter(getContext(),datakhoanthu);
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
