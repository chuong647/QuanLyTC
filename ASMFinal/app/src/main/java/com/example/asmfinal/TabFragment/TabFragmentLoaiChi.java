package com.example.asmfinal.TabFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
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

import com.example.asmfinal.Adapter.TabLoaiChiAdapter;
import com.example.asmfinal.DAO.DaoLoaiChi;
import com.example.asmfinal.Model.LoaiChi;
import com.example.asmfinal.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;

public class TabFragmentLoaiChi extends Fragment {
   public static RecyclerView rvloaichi;
    ArrayList<LoaiChi> datachi;
    DaoLoaiChi daoLoaiChi;
    public static TabLoaiChiAdapter tabLoaiChiAdapter;
    FloatingActionButton floatbtnthemchi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tablayoutloaichi,container,false);
        rvloaichi = view.findViewById(R.id.rvloaichi);
        floatbtnthemchi =  view.findViewById(R.id.floatbtnthemchi);
        rvloaichi.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rvloaichi.setLayoutManager(layoutManager);
        daoLoaiChi = new DaoLoaiChi(getContext());
        datachi = new ArrayList<LoaiChi>();
        datachi = daoLoaiChi.readAll();
        tabLoaiChiAdapter = new TabLoaiChiAdapter(getActivity(),datachi);
        rvloaichi.setAdapter(tabLoaiChiAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvloaichi);
        floatbtnthemchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(getContext());
                LayoutInflater inflater1 = getActivity().getLayoutInflater();
                View view1 = inflater1.inflate(R.layout.dialoginsertloaichi,null);
                builder.setView(view1);

                final EditText edttenloaichi1 = (EditText) view1.findViewById(R.id.edttenloaichi);
                final EditText edttrangthai1 = (EditText)view1.findViewById(R.id.edttrangthaichi);
                Button btnthemloaichi1 = view1.findViewById(R.id.btnthemloaichi);
                Button btnhuythem1 = view1.findViewById(R.id.btnhuythem);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                btnthemloaichi1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenloai1 = edttenloaichi1.getText().toString().trim();
                        String trangthai1 = edttrangthai1.getText().toString();
                        daoLoaiChi = new DaoLoaiChi(getContext());
                        if (!tenloai1.equals("") || !trangthai1.equals("")){
                            daoLoaiChi.insert(new LoaiChi(null,tenloai1,trangthai1));
                            Toast.makeText(getContext(), "THÊM THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                            showdulieu();
                            alertDialog.cancel();
                        }else {
                            edttenloaichi1.setError("Vui lòng nhập thông tin");
                            edttrangthai1.setError("Vui lòng nhập thông tin");
                        }

                    }

                });
                btnhuythem1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });
            }
        });
        return view;

    }
    private void showdulieu(){
        datachi.clear();
        datachi.addAll(daoLoaiChi.readAll());
        tabLoaiChiAdapter= new TabLoaiChiAdapter(getActivity(),datachi);
        rvloaichi.setAdapter(tabLoaiChiAdapter);
        tabLoaiChiAdapter.notifyDataSetChanged();
    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int index = viewHolder.getAdapterPosition();
            final String maloai = datachi.get(index).getMaloai();
            switch (direction){
                case LEFT:
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Bạn có chắc muốn xóa không ?");
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            daoLoaiChi = new DaoLoaiChi(getContext());
                            datachi = new ArrayList<LoaiChi>();
                            daoLoaiChi.delete(maloai);
                            datachi = daoLoaiChi.readAll();
                            tabLoaiChiAdapter = new TabLoaiChiAdapter(getActivity(),datachi);
                            rvloaichi.setAdapter(tabLoaiChiAdapter);
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            daoLoaiChi = new DaoLoaiChi(getContext());
                            datachi = new ArrayList<LoaiChi>();
                            datachi = daoLoaiChi.readAll();
                            tabLoaiChiAdapter = new TabLoaiChiAdapter(getActivity(),datachi);
                            rvloaichi.setAdapter(tabLoaiChiAdapter);
                        dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
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
}
