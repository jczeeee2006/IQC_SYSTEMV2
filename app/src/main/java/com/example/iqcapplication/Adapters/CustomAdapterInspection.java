package com.example.iqcapplication.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iqcapplication.ConnectionClass;
import com.example.iqcapplication.R;
import com.example.iqcapplication.SapmpleActivityinlot;
import com.example.iqcapplication.Update.InspectionActivity;
import com.example.iqcapplication.Update.LotFormActivity;
import com.example.iqcapplication.encapsulation.InspectionEncapsulation;
import com.example.iqcapplication.encapsulation.LotEncapsulation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomAdapterInspection extends RecyclerView.Adapter<CustomAdapterInspection.MyViewHolder> implements Filterable {
    Context context;
    Activity activity;
    ArrayList<InspectionEncapsulation> inspectData = new ArrayList<>();
    ArrayList<InspectionEncapsulation> inspectDataa ;

    public CustomAdapterInspection(Activity activity, Context context, ArrayList<InspectionEncapsulation> inspectData) {
        this.activity = activity;
        this.context = context;
        this.inspectData = inspectData;
        this.inspectDataa = new ArrayList<>(inspectData);
    }

    @Override
    public CustomAdapterInspection.MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.inspectrow,parent,false);

        return  new CustomAdapterInspection.MyViewHolder(view);
    }



    @Override
    public Filter getFilter() {
        return filter;
    }

    //------------FILTERING OF DATA IN FRAMELAYOUT-----------------//
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<InspectionEncapsulation> filteredair = new ArrayList<>();

            //-------------RECYCLERVIEW LIST OF DATA ---------------------
            if (inspectDataa.size() == 0) inspectDataa = new ArrayList<>(inspectData);
            if (constraint.toString().isEmpty() || constraint.length() ==0) {
                filteredair.addAll(inspectDataa);
            } else {
                for (InspectionEncapsulation lot : inspectDataa) {

                    if (lot.getDateeee().toLowerCase().contains(constraint.toString().toLowerCase()) || lot.getDateinspected().contains(constraint.toString().toLowerCase())) {
                        filteredair.add(lot);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredair;
            return filterResults;
        }

        ///-----------------------------DISPLAY RESULT WHEN FILTERING------------//
        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            inspectData.clear();
            inspectData.addAll((Collection<? extends InspectionEncapsulation>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    @Override
    public void onBindViewHolder(@NonNull CustomAdapterInspection.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        InspectionEncapsulation inspectionlist = inspectData.get(position);

        holder.idins_txt.setText(String.valueOf(inspectionlist.getId()));
        holder.prepared_txt.setText(String.valueOf(inspectionlist.getPreparedby()));
        holder.prepareddate_txt.setText(String.valueOf(inspectionlist.getDateeee()));
        holder.invoiceIns_txt.setText(String.valueOf(inspectionlist.getInvoicenum()));
        holder.goodsCodeIns_txt.setText(String.valueOf(inspectionlist.getGoodc()));

        holder.partNameIns_txt.setText(String.valueOf(inspectionlist.getPartName()));
        holder.invoiceQuantins_txt.setText(String.valueOf(inspectionlist.getInvoicequant()));
        holder.assylineins_txt.setText(String.valueOf(inspectionlist.getAssemblyline()));
        holder.partNumIns_txt.setText(String.valueOf(inspectionlist.getPartnum()));
        holder.tempIns_txt.setText(String.valueOf(inspectionlist.getTemp()));

        holder.rohsins_txt.setText(String.valueOf(inspectionlist.getRohscomp()));
        holder.dateinspect_txt.setText(String.valueOf(inspectionlist.getDateinspected()));
        holder.humidIns_txt.setText(String.valueOf(inspectionlist.getHumidity()));
        holder.supplierins_txt.setText(String.valueOf(inspectionlist.getSupplier()));
        holder.inspectorins_txt.setText(String.valueOf(inspectionlist.getInspector()));

        holder.date_received_txt.setText(String.valueOf(inspectionlist.getDatereceived()));
        holder.makerins_txt.setText(String.valueOf(inspectionlist.getMaker()));
        holder.sampleSizeIns_txt.setText(String.valueOf(inspectionlist.getSamplesize()));
        holder.materialin_txtcocins_txt.setText(String.valueOf(inspectionlist.getMattype()));
        holder.inspectypeins_txt.setText(String.valueOf(inspectionlist.getInspecttype()));

        holder.ul_Marking_txt.setText(String.valueOf(inspectionlist.getUlmarking()));
        holder.oirIns_txt.setText(String.valueOf(inspectionlist.getOir()));
        holder.cocins_txt.setText(String.valueOf(inspectionlist.getInscoc()));
        holder.productionins_txt.setText(String.valueOf(inspectionlist.getProdtype()));
        holder.testReportIns_txt.setText(String.valueOf(inspectionlist.getTestreport()));
        holder.dateToday_txt.setText(String.valueOf(inspectionlist.getDateToday()));
        holder.boxseqid_txt.setText(String.valueOf(inspectionlist.getBoxseqid()));

        holder.inspectform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, InspectionActivity.class);

                InspectionEncapsulation lotNumberlist = inspectData.get(position);
                intent.putExtra("idins_txt", String.valueOf(lotNumberlist.getId()));
                intent.putExtra("prepared_txt", String.valueOf(lotNumberlist.getPreparedby()));
                intent.putExtra("prepareddate_txt", String.valueOf(lotNumberlist.getDateeee()));
                intent.putExtra("invoiceIns_txt", String.valueOf(lotNumberlist.getInvoicenum()));
                intent.putExtra("goodsCodeIns_txt", String.valueOf(lotNumberlist.getGoodc()));
                intent.putExtra("partNameIns_txt", String.valueOf(lotNumberlist.getPartName()));
                intent.putExtra("invoiceQuantins_txt", String.valueOf(lotNumberlist.getInvoicequant()));
                intent.putExtra("assylineins_txt", String.valueOf(lotNumberlist.getAssemblyline()));
                intent.putExtra("partNumIns_txt", String.valueOf(lotNumberlist.getPartnum()));
                intent.putExtra("tempIns_txt", String.valueOf(lotNumberlist.getTemp()));
                intent.putExtra("rohsins_txt", String.valueOf(lotNumberlist.getRohscomp()));
                intent.putExtra("dateinspect_txt", String.valueOf(lotNumberlist.getDateinspected()));


                intent.putExtra("humidIns_txt", String.valueOf(lotNumberlist.getHumidity()));
                intent.putExtra("supplierins_txt", String.valueOf(lotNumberlist.getSupplier()));
                intent.putExtra("inspectorins_txt", String.valueOf(lotNumberlist.getInspector()));


                intent.putExtra("date_received_txt", String.valueOf(lotNumberlist.getDatereceived()));
                intent.putExtra("makerins_txt", String.valueOf(lotNumberlist.getMaker()));
                intent.putExtra("sampleSizeIns_txt", String.valueOf(lotNumberlist.getSamplesize()));

                intent.putExtra("materialin_txtcocins_txt", String.valueOf(lotNumberlist.getMattype()));

                intent.putExtra("inspectypeins_txt", String.valueOf(lotNumberlist.getInspecttype()));
                intent.putExtra("ul_Marking_txt", String.valueOf(lotNumberlist.getUlmarking()));
                intent.putExtra("oirIns_txt", String.valueOf(lotNumberlist.getOir()));
                intent.putExtra("productionins_txt", String.valueOf(lotNumberlist.getProdtype()));
                intent.putExtra("itestReportIns_txtd", String.valueOf(lotNumberlist.getTestreport()));
                intent.putExtra("cocins_txt", String.valueOf(lotNumberlist.getInscoc()));
                intent.putExtra("dateToday_txt", String.valueOf(lotNumberlist.getDateToday()));
                intent.putExtra("boxseqid_txt", String.valueOf(lotNumberlist.getBoxseqid()));
                activity.startActivityForResult(intent, -1);








            }
        });


    }

    @Override
    public int getItemCount() {
        return inspectData.size();
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView prepared_txt,prepareddate_txt, idins_txt,  ul_Marking_txt,assylineins_txt, makerins_txt,productionins_txt,materialin_txtcocins_txt,supplierins_txt,sampleSizeIns_txt,partNumIns_txt,partNameIns_txt,tempIns_txt,
                date_received_txt,dateinspect_txt,invoiceQuantins_txt,inspectorins_txt,rohsins_txt,inspectypeins_txt,cocins_txt,testReportIns_txt,oirIns_txt, humidIns_txt,
        goodsCodeIns_txt,invoiceIns_txt,dateToday_txt,boxseqid_txt;


        LinearLayout inspectform;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            idins_txt = itemView.findViewById(R.id.id_txt);
            prepared_txt  = itemView.findViewById(R.id.preparedins_txt);
            prepareddate_txt  = itemView.findViewById(R.id.prepareddate_txt);
            invoiceIns_txt = itemView.findViewById(R.id.invoiceIns_txt);
            goodsCodeIns_txt  = itemView.findViewById(R.id.goodsCodeIns_txt);

            partNameIns_txt = itemView.findViewById(R.id.partNameIns_txt);
            invoiceQuantins_txt  = itemView.findViewById(R.id.invoiceQuantins_txt);
            assylineins_txt   = itemView.findViewById(R.id.assylineins_txt);
            partNumIns_txt = itemView.findViewById(R.id.partNumIns_txt);
            tempIns_txt = itemView.findViewById(R.id.tempIns_txt);

            rohsins_txt = itemView.findViewById(R.id.rohsins_txt);
            dateinspect_txt   = itemView.findViewById(R.id.dateinspect_txt);
            humidIns_txt  = itemView.findViewById(R.id.humidIns_txt);
            supplierins_txt = itemView.findViewById(R.id.supplierins_txt);
            inspectorins_txt = itemView.findViewById(R.id.inspectorins_txt);

            date_received_txt  = itemView.findViewById(R.id.date_received_txt);
            makerins_txt = itemView.findViewById(R.id.makerins_txt);
            sampleSizeIns_txt  = itemView.findViewById(R.id.sampleSizeIns_txt);
            materialin_txtcocins_txt = itemView.findViewById(R.id.materialin_txtcocins_txt4);
            inspectypeins_txt  = itemView.findViewById(R.id.inspectypeins_txt);

            ul_Marking_txt = itemView.findViewById(R.id.ul_Marking_txt);
            oirIns_txt  = itemView.findViewById(R.id.oirIns_txt);
            cocins_txt = itemView.findViewById(R.id.cocins_txt);
            productionins_txt = itemView.findViewById(R.id.productionins_txt);
            testReportIns_txt  = itemView.findViewById(R.id.testReportIns_txt);
            dateToday_txt  = itemView.findViewById(R.id.dateTodayins_txt);
            boxseqid_txt = itemView.findViewById(R.id.boxseqid_txt);
            inspectform = itemView.findViewById(R.id.inspecta);
        }
    }

}
