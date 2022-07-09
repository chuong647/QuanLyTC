package com.example.asmfinal.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmfinal.DAO.Daoloaithuchi;
import com.example.asmfinal.Model.LoaiThuChi;
import com.example.asmfinal.R;

import java.util.ArrayList;

import static com.example.asmfinal.TabFragment.TabFragment1.rv;

public class TabLoaiThuAdapter extends RecyclerView.Adapter<TabLoaiThuViewHolder> {
    Context context;
    ArrayList<LoaiThuChi> dataloaithu;
    Daoloaithuchi daoloaithuchi;
    TabLoaiThuAdapter tabLoaiThuAdapter;

    public TabLoaiThuAdapter(Context context, ArrayList<LoaiThuChi> dataloaithu) {
        this.context = context;
        this.dataloaithu = dataloaithu;
    }

    @NonNull
    @Override
    public TabLoaiThuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemloaithu,parent,false);
        return new TabLoaiThuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabLoaiThuViewHolder holder, final int position) {
        holder.txttenloai.setText(dataloaithu.get(position).getTenloai());
        holder.txttrangthai.setText(dataloaithu.get(position).getTrangthai());
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                View view1 = inflater.inflate(R.layout.dialogeditloaithu,null);
                builder.setView(view1);
                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                final EditText edttenloaithusua = view1.findViewById(R.id.edttenloaithusua);
                final EditText edttrangthaisua = view1.findViewById(R.id.edttrangthaisua);
                Button btnsualoaithu = view1.findViewById(R.id.btnsualoaithu);
                Button btnhuysua = view1.findViewById(R.id.btnhuysua);
                edttenloaithusua.setText(dataloaithu.get(position).getTenloai());
                edttrangthaisua.setText(dataloaithu.get(position).getTrangthai());
                btnsualoaithu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenloai = edttenloaithusua.getText().toString().trim();
                        String trangthai = edttrangthaisua.getText().toString().trim();
                        String maloai = dataloaithu.get(position).getMaloai();
                        dataloaithu = new ArrayList<LoaiThuChi>();

                        if (!tenloai.equals("") || !trangthai.equals("")){
                            daoloaithuchi = new Daoloaithuchi(context);
                            daoloaithuchi.update(maloai,tenloai,trangthai);
                            dataloaithu=daoloaithuchi.readAll();
                            tabLoaiThuAdapter = new TabLoaiThuAdapter(context,dataloaithu);
                            rv.setAdapter(tabLoaiThuAdapter);
                            tabLoaiThuAdapter.notifyDataSetChanged();

                            alertDialog.cancel();
                            Toast.makeText(context, "UPDATE THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                        }else {
                            edttenloaithusua.setError("Vui lòng nhập thông tin");
                            edttrangthaisua.setError("Vui lòng nhập thông tin");
                        }

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
//        holder.imgxoa.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String maloai = dataloaithu.get(position).getMaloai();
//                daoloaithuchi = new Daoloaithuchi(context);
//                dataloaithu = new ArrayList<LoaiThuChi>();
//                daoloaithuchi.delete(maloai);
//                dataloaithu=daoloaithuchi.readAll();
//                tabLoaiThuAdapter = new TabLoaiThuAdapter(context,dataloaithu);
//                rv.setAdapter(tabLoaiThuAdapter);
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return dataloaithu.size();
    }
}
