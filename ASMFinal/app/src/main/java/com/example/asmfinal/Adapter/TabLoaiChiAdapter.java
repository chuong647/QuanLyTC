package com.example.asmfinal.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmfinal.DAO.DaoLoaiChi;
import com.example.asmfinal.Model.LoaiChi;
import com.example.asmfinal.R;

import java.util.ArrayList;

import static com.example.asmfinal.TabFragment.TabFragmentLoaiChi.rvloaichi;

public class TabLoaiChiAdapter extends RecyclerView.Adapter<TabLoaiChiAdapter.LoaiChiHolder> {
   Activity context;
   ArrayList<LoaiChi> dataloaichi;
   DaoLoaiChi daoLoaiChi;
   TabLoaiChiAdapter tabLoaiChiAdapter;


    public TabLoaiChiAdapter(Activity context, ArrayList<LoaiChi> dataloaichi) {
        this.context = context;
        this.dataloaichi = dataloaichi;
    }

    @NonNull
    @Override
    public LoaiChiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = context.getLayoutInflater();
        View view = inflater.inflate(R.layout.itemloaichi,parent,false);
        return new LoaiChiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoaiChiHolder holder, final int position) {
            holder.txttenloai.setText(dataloaichi.get(position).getTenloai());
            holder.txttrangthai.setText(dataloaichi.get(position).getTrangthai());
            holder.imgedit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                    View view1 = inflater.inflate(R.layout.dialogeditloaichi,null);
                    builder.setView(view1);
                    final AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                    final EditText edttenloaichisua = view1.findViewById(R.id.edttenloaichisua);
                    final EditText edttrangthaichisua = view1.findViewById(R.id.edttrangthaichisua);
                    Button btnsualoaichi = view1.findViewById(R.id.btnsualoaichi);
                    Button btnhuysua = view1.findViewById(R.id.btnhuysua);
                    edttenloaichisua.setText(dataloaichi.get(position).getTenloai());
                    edttrangthaichisua.setText(dataloaichi.get(position).getTrangthai());
                    btnsualoaichi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String maloai = dataloaichi.get(position).getMaloai();
                            String tenloai = edttenloaichisua.getText().toString().trim();
                            String trangthai = edttrangthaichisua.getText().toString().trim();
                            daoLoaiChi = new DaoLoaiChi(context);
                            dataloaichi = new ArrayList<LoaiChi>();
                            if(!tenloai.equals("") || !trangthai.equals("")){
                                daoLoaiChi.update(maloai,tenloai,trangthai);
                                dataloaichi=daoLoaiChi.readAll();
                                tabLoaiChiAdapter = new TabLoaiChiAdapter(context,dataloaichi);
                                rvloaichi.setAdapter(tabLoaiChiAdapter);
                                tabLoaiChiAdapter.notifyDataSetChanged();
                                Toast.makeText(context, "CẬP NHẬT THÀNH CÔNG", Toast.LENGTH_SHORT).show();
                                alertDialog.cancel();
                            }else {
                                edttenloaichisua.setError("Vui lòng nhập thông tin");
                                edttrangthaichisua.setError("Vui lòng nhập thông tin");
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
    }

    @Override
    public int getItemCount() {
        return dataloaichi.size();
    }

    public static class LoaiChiHolder extends RecyclerView.ViewHolder{
        TextView txttenloai;
        TextView txttrangthai;
         ImageView imgedit;


        public LoaiChiHolder(View itemView) {
            super(itemView);
            txttenloai = itemView.findViewById(R.id.txttenloai);
            txttrangthai = itemView.findViewById(R.id.txttrangthai);
            imgedit = itemView.findViewById(R.id.imgedit);

        }
    }
}
