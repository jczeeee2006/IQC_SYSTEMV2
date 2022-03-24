package com.example.iqcapplication.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iqcapplication.R;
import com.example.iqcapplication.encapsulation.InspectionEncapsulation;

import java.util.ArrayList;

public class CustomAdapterInspection extends RecyclerView.Adapter<CustomAdapterInspection.MyViewHolder> {
    Context context;
    Activity activity;
    ArrayList<InspectionEncapsulation> inspectData = new ArrayList<>();


    public CustomAdapterInspection(Context context,  ArrayList<InspectionEncapsulation> inspectData) {
        this.context = context;
        this.inspectData = inspectData;
    }

    @Override
    public CustomAdapterInspection.MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.inspectrow,parent,false);

        return  new CustomAdapterInspection.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterInspection.MyViewHolder holder, int position) {
        InspectionEncapsulation inspectionlist = inspectData.get(position);
        holder.idins_txt.setText(String.valueOf(inspectionlist.getId()));
        holder.ul_Marking_txt.setText(String.valueOf(inspectionlist.getUlmarking()));
        holder.assylineins_txt.setText(String.valueOf(inspectionlist.getAssemblyline()));
        holder.makerins_txt.setText(String.valueOf(inspectionlist.getMaker()));
        holder.productionins_txt.setText(String.valueOf(inspectionlist.getProdtype()));
        holder.materialin_txtcocins_txt.setText(String.valueOf(inspectionlist.getMattype()));
        holder.supplierins_txt.setText(String.valueOf(inspectionlist.getSupplier()));
        holder.sampleSizeIns_txt.setText(String.valueOf(inspectionlist.getSamplesize()));
        holder.rejectIns_txt.setText(String.valueOf(inspectionlist.getReject()));
        holder.partNumIns_txt.setText(String.valueOf(inspectionlist.getPartnum()));
        holder.partNameIns_txt.setText(String.valueOf(inspectionlist.getPartName()));
        holder.tempIns_txt.setText(String.valueOf(inspectionlist.getTemp()));
        holder.date_received_txt.setText(String.valueOf(inspectionlist.getDatereceived()));
        holder.dateinspect_txt.setText(String.valueOf(inspectionlist.getDateinspected()));
        holder.invoiceQuantins_txt.setText(String.valueOf(inspectionlist.getInvoicequant()));
        holder.inspectorins_txt.setText(String.valueOf(inspectionlist.getInspector()));
        holder.rohsins_txt.setText(String.valueOf(inspectionlist.getRohscomp()));
        holder.inspectypeins_txt.setText(String.valueOf(inspectionlist.getInspecttype()));
        holder.cocins_txt.setText(String.valueOf(inspectionlist.getCoc()));
        holder.testReportIns_txt.setText(String.valueOf(inspectionlist.getTestreport()));
        holder.oirIns_txt.setText(String.valueOf(inspectionlist.getOir()));
        holder.humidIns_txt.setText(String.valueOf(inspectionlist.getHumidity()));
        holder.goodsCodeIns_txt.setText(String.valueOf(inspectionlist.getGoodc()));
        holder.invoiceIns_txt.setText(String.valueOf(inspectionlist.getInvoicenum()));


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView idins_txt,  ul_Marking_txt,assylineins_txt, makerins_txt,productionins_txt,materialin_txtcocins_txt,supplierins_txt,sampleSizeIns_txt,rejectIns_txt,partNumIns_txt,partNameIns_txt,tempIns_txt,
                date_received_txt,dateinspect_txt,invoiceQuantins_txt,inspectorins_txt,rohsins_txt,inspectypeins_txt,cocins_txt,testReportIns_txt,oirIns_txt, humidIns_txt,
        goodsCodeIns_txt,invoiceIns_txt;
        ConstraintLayout inspectform;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idins_txt = itemView.findViewById(R.id.idins_txt);
            ul_Marking_txt = itemView.findViewById(R.id.ul_Marking_txt);
            assylineins_txt = itemView.findViewById(R.id.assylineins_txt);
            makerins_txt = itemView.findViewById(R.id.makerins_txt);
            productionins_txt = itemView.findViewById(R.id.productionins_txt);
            materialin_txtcocins_txt = itemView.findViewById(R.id.materialin_txtcocins_txt4);
            supplierins_txt = itemView.findViewById(R.id.supplierins_txt);
            rejectIns_txt  = itemView.findViewById(R.id.rejectIns_txt);
            sampleSizeIns_txt  = itemView.findViewById(R.id.sampleSizeIns_txt);
            partNumIns_txt = itemView.findViewById(R.id.partNumIns_txt);
            partNameIns_txt = itemView.findViewById(R.id.partNameIns_txt);
            tempIns_txt = itemView.findViewById(R.id.tempIns_txt);
            date_received_txt  = itemView.findViewById(R.id.date_received_txt);
            dateinspect_txt   = itemView.findViewById(R.id.dateinspect_txt);
            invoiceQuantins_txt  = itemView.findViewById(R.id.invoiceQuantins_txt);
            inspectorins_txt = itemView.findViewById(R.id.inspectorins_txt);
            rohsins_txt = itemView.findViewById(R.id.rohsins_txt);
            inspectypeins_txt  = itemView.findViewById(R.id.inspectypeins_txt);
            cocins_txt = itemView.findViewById(R.id.cocins_txt);
            testReportIns_txt  = itemView.findViewById(R.id.testReportIns_txt);
            oirIns_txt  = itemView.findViewById(R.id.oirIns_txt);
            humidIns_txt  = itemView.findViewById(R.id.humidIns_txt);
            goodsCodeIns_txt  = itemView.findViewById(R.id.goodsCodeIns_txt);
            invoiceIns_txt = itemView.findViewById(R.id.invoiceIns_txt);
            inspectform = itemView.findViewById(R.id.inspectrow);
        }
    }

}
