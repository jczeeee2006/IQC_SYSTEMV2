package com.example.iqcapplication.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iqcapplication.R;
import com.example.iqcapplication.Update.LotFormActivity;
import com.example.iqcapplication.encapsulation.LotEncapsulation;

import java.util.ArrayList;

public class CustomAdapterLot extends RecyclerView.Adapter<CustomAdapterLot.MyViewHolder> {
        Context context;
        Activity activity;
        ArrayList<LotEncapsulation> lotData = new ArrayList<>();


    public CustomAdapterLot(Activity activity,  Context context, ArrayList<LotEncapsulation> lotData) {
        this.activity = activity;
        this.context = context;
        this.lotData = lotData;

    }


    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layoutrecyclerdataview,parent,false);

        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterLot.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        LotEncapsulation lotNumberlist = lotData.get(position);
        holder.id_txt.setText(String.valueOf(lotNumberlist.getId()));
        holder.invoice_txt.setText(String.valueOf(lotNumberlist.getLot_invoiceno()));
        holder.partNum_txt.setText(String.valueOf(lotNumberlist.getEt_partnum()));
        holder.goodsCode_txt.setText(String.valueOf(lotNumberlist.getGoodsc()));
        holder.partName_txt.setText(String.valueOf(lotNumberlist.getTv_partname()));
        holder.box_txt.setText(String.valueOf(lotNumberlist.getBoxnum()));
        holder.boxseq_txt.setText(String.valueOf(lotNumberlist.getBoxseqid()));
        holder.actualquant_txt.setText(String.valueOf(lotNumberlist.getQuantityrecieved()));
        holder.totalquant_txt.setText(String.valueOf(lotNumberlist.getTotalquantity()));
        holder.reject_txt.setText(String.valueOf(lotNumberlist.getReject()));
        holder.sampSize_txt.setText(String.valueOf(lotNumberlist.getSampsize()));
        holder.lotNum_txt.setText(String.valueOf(lotNumberlist.getLotno()));
        holder.lotQty_txt.setText(String.valueOf(lotNumberlist.getLotquant()));
        holder.remarks_txt.setText(String.valueOf(lotNumberlist.getRemarks()));
        holder.dateToday_txt.setText(String.valueOf(lotNumberlist.getDateToday()));

        holder.lotFormlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LotFormActivity.class);
                LotEncapsulation lotNumberlist = lotData.get(position);
                intent.putExtra("id", String.valueOf(lotNumberlist.getId()));
                intent.putExtra("lot_invoiceno", String.valueOf(lotNumberlist.getLot_invoiceno()));
                intent.putExtra("et_partnum", String.valueOf(lotNumberlist.getEt_partnum()));
                intent.putExtra("goodsc", String.valueOf(lotNumberlist.getGoodsc()));
                intent.putExtra("tv_partname", String.valueOf(lotNumberlist.getTv_partname()));
                intent.putExtra("boxnum", String.valueOf(lotNumberlist.getBoxnum()));
                intent.putExtra("boxseqid", String.valueOf(lotNumberlist.getBoxseqid()));
                intent.putExtra("quantityrecieved", String.valueOf(lotNumberlist.getQuantityrecieved()));
                intent.putExtra("totalquantity", String.valueOf(lotNumberlist.getTotalquantity()));
                intent.putExtra("reject", String.valueOf(lotNumberlist.getReject()));
                intent.putExtra("sampsize", String.valueOf(lotNumberlist.getSampsize()));
                intent.putExtra("lotno", String.valueOf(lotNumberlist.getLotno()));
                intent.putExtra("lotquant", String.valueOf(lotNumberlist.getLotquant()));
                intent.putExtra("remarks", String.valueOf(lotNumberlist.getRemarks()));
                intent.putExtra("dateToday", String.valueOf(lotNumberlist.getDateToday()));
                context.startActivity(intent);

            }
        });




    }

    @Override
    public int getItemCount() {
        return lotData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id_txt, invoice_txt, partName_txt, partNum_txt, goodsCode_txt, totalquant_txt,actualquant_txt,lotNum_txt,lotQty_txt, box_txt,reject_txt,sampSize_txt,boxseq_txt,
        remarks_txt, dateToday_txt;


        LinearLayout lotFormlayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id_txt = itemView.findViewById(R.id.idins_txt);
            invoice_txt = itemView.findViewById(R.id.invoiceNum_txt);
            partNum_txt = itemView.findViewById(R.id.partN_txt);
            goodsCode_txt = itemView.findViewById(R.id.goodsCode_txt);
            partName_txt = itemView.findViewById(R.id.partName_txt);
            box_txt = itemView.findViewById(R.id.boxNum_txt);
            boxseq_txt = itemView.findViewById(R.id.boxSequence_txt);
            actualquant_txt = itemView.findViewById(R.id.atctualQuant_txt);
            totalquant_txt = itemView.findViewById(R.id.totalQuan_txt);
            reject_txt = itemView.findViewById(R.id.reject_txt);
            sampSize_txt = itemView.findViewById(R.id.sampleSize_txt);
            lotNum_txt = itemView.findViewById(R.id.lotNumber_txt);
            lotQty_txt = itemView.findViewById(R.id.lotQuant_txt);
            remarks_txt = itemView.findViewById(R.id.remarks_txt);
            dateToday_txt = itemView.findViewById(R.id.dateToday_txt);
            lotFormlayout = itemView.findViewById(R.id.LotLayout);

        }
    }
}
