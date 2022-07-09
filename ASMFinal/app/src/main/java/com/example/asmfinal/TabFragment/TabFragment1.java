package com.example.asmfinal.TabFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmfinal.Adapter.TabLoaiThuAdapter;
import com.example.asmfinal.DAO.Daoloaithuchi;
import com.example.asmfinal.Model.LoaiThuChi;
import com.example.asmfinal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static androidx.recyclerview.widget.ItemTouchHelper.*;

public class TabFragment1 extends Fragment {
  public static   RecyclerView rv;
    public static TabLoaiThuAdapter tabLoaiThuAdapter;
    ArrayList<LoaiThuChi> dataloaithu;
    Daoloaithuchi daoloaithuchi;
    FloatingActionButton floatbtnthem;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayoutthu,container,false);

        rv = view.findViewById(R.id.rv);
        floatbtnthem = view.findViewById(R.id.floatbtnthem);
        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        daoloaithuchi = new Daoloaithuchi(getContext());
        dataloaithu = new ArrayList<LoaiThuChi>();
        dataloaithu= daoloaithuchi.readAll();
        tabLoaiThuAdapter = new TabLoaiThuAdapter(getActivity(),dataloaithu);
        rv.setAdapter(tabLoaiThuAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rv);

        floatbtnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater =((Activity)getContext()).getLayoutInflater();
                View view1 = inflater.inflate(R.layout.dialoginsert,null);
                builder.setView(view1);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                final EditText edttenloaithu = view1.findViewById(R.id.edttenloaithu);
                final EditText edttrangthai = view1.findViewById(R.id.edttrangthai);
                Button btnthemloaithu = view1.findViewById(R.id.btnthemloaithu);
                Button btnhuythem = view1.findViewById(R.id.btnhuythem);

                btnthemloaithu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenloai = edttenloaithu.getText().toString().trim();
                        String trangthai = edttrangthai.getText().toString().trim();
                        daoloaithuchi = new Daoloaithuchi(getContext());
                        if(!tenloai.equals("") || !trangthai.equals("")){
                            daoloaithuchi.insert(new LoaiThuChi(null,tenloai,trangthai));
                            alertDialog.cancel();
                            dolist();
                            Toast.makeText(getContext(), "THÊM THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                        }else {
                            edttenloaithu.setError("Vui lòng nhập đầy đủ thông tin");
                            edttrangthai.setError("Vui lòng nhập đầy đủ thông tin");
                        }


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
    SimpleCallback simpleCallback = new SimpleCallback(0, LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position= viewHolder.getAdapterPosition();
            final String ma =dataloaithu.get(position).getMaloai();

            switch (direction){
                case LEFT:
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setTitle("Thông Báo");
                            builder.setMessage("Bạn có chắc muốn xóa không?");

                            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dataloaithu = new ArrayList<LoaiThuChi>();
                                    daoloaithuchi =new Daoloaithuchi(getContext());
                                    dataloaithu =daoloaithuchi.readAll();
                                    tabLoaiThuAdapter =new TabLoaiThuAdapter(getContext(),dataloaithu);
                                    rv.setAdapter(tabLoaiThuAdapter);
                                    dialog.cancel();
                                }
                            });
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dataloaithu = new ArrayList<LoaiThuChi>();
                            daoloaithuchi =new Daoloaithuchi(getContext());
                            daoloaithuchi.delete(ma);
                            dataloaithu =daoloaithuchi.readAll();
                            tabLoaiThuAdapter =new TabLoaiThuAdapter(getContext(),dataloaithu);
                            rv.setAdapter(tabLoaiThuAdapter);
                            dialog.cancel();
                        }
                    });
                            AlertDialog alertDialog =builder.create();
                            alertDialog.show();


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



    public void dolist() {
        dataloaithu.clear();
        dataloaithu.addAll(daoloaithuchi.readAll());
        rv.setAdapter(tabLoaiThuAdapter);
    }

}
