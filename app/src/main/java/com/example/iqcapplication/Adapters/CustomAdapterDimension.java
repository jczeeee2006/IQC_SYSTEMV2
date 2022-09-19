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
import com.example.iqcapplication.Update.DimensionActivity;
import com.example.iqcapplication.encapsulation.DimensionEncapsulation;

import java.util.ArrayList;

public class CustomAdapterDimension extends RecyclerView.Adapter<CustomAdapterDimension.MyViewHolder> {
    Context context;
    Activity activity;
    ArrayList<DimensionEncapsulation> dimensionData = new ArrayList<>();


    public CustomAdapterDimension(Activity activity, Context context, ArrayList<DimensionEncapsulation> dimensionData) {

        this.activity = activity;
        this.context = context;
        this.dimensionData = dimensionData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_dimension_data, parent, false);

        return new CustomAdapterDimension.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterDimension.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        DimensionEncapsulation dimensionlist = dimensionData.get(position);

        holder.dcid_txt.setText(String.valueOf(dimensionlist.getId()));
        holder.dcinstrument_txt.setText(String.valueOf(dimensionlist.getInstrumentUsed()));
        holder.dcsampleSize_txt.setText(String.valueOf(dimensionlist.getSampleSize()));
        holder.sammpleUnit_txt.setText(String.valueOf(dimensionlist.getSampleUnit()));
        holder.dc_checkPoints_txt.setText(String.valueOf(dimensionlist.getCheckpointdc()));


        holder.dc1_txt.setText(String.valueOf(dimensionlist.getSample1dc()));
        holder.dc2_txt.setText(String.valueOf(dimensionlist.getSample2dc()));
        holder.dc3_txt.setText(String.valueOf(dimensionlist.getSample3dc()));
        holder.dc4_txt.setText(String.valueOf(dimensionlist.getSample4dc()));
        holder.dc5_txt.setText(String.valueOf(dimensionlist.getSample5dc()));
        holder.dc6_txt.setText(String.valueOf(dimensionlist.getSample6dc()));
        holder.dc7_txt.setText(String.valueOf(dimensionlist.getSmple7dc()));
        holder.dc8_txt.setText(String.valueOf(dimensionlist.getSample8dc()));
        holder.dc9_txt.setText(String.valueOf(dimensionlist.getSample9dc()));
        holder.dc10_txt.setText(String.valueOf(dimensionlist.getSample10dc()));
        holder.lowerSpec_txt.setText(String.valueOf(dimensionlist.getLoweSpecs()));
        holder.upperSpec_txt.setText(String.valueOf(dimensionlist.getUpperSpecs()));

        holder.dc_Minimum_txt.setText(String.valueOf(dimensionlist.getMinimum()));
        holder.dc_Average_txt.setText(String.valueOf(dimensionlist.getAverage()));
        holder.dc_Maximum_txt.setText(String.valueOf(dimensionlist.getMaximum()));

        holder.dc_Judgemen_txt.setText(String.valueOf(dimensionlist.getJudgement()));
        holder.remarks_txt.setText(String.valueOf(dimensionlist.getRemarks()));
        holder.datedim_txt.setText(String.valueOf(dimensionlist.getDate()));
        holder.dimensionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DimensionActivity.class);
                DimensionEncapsulation dimensionlist = dimensionData.get(position);

                intent.putExtra("id", String.valueOf(dimensionlist.getId()));
                intent.putExtra("getInstrumentUsed", String.valueOf(dimensionlist.getInstrumentUsed()));

                intent.putExtra("getSampleSize", String.valueOf(dimensionlist.getSampleSize()));
                intent.putExtra("getCheckpointdc", String.valueOf(dimensionlist.getCheckpointdc()));
                intent.putExtra("getSampleUnit", String.valueOf(dimensionlist.getSampleUnit()));


                intent.putExtra("getSample1dc", String.valueOf(dimensionlist.getSample1dc()));
                intent.putExtra("getSample2dc", String.valueOf(dimensionlist.getSample2dc()));

                intent.putExtra("getSample3dc", String.valueOf(dimensionlist.getSample3dc()));
                intent.putExtra("getSample4dc", String.valueOf(dimensionlist.getSample4dc()));
                intent.putExtra("getSample5dc", String.valueOf(dimensionlist.getSample5dc()));
                intent.putExtra("getSample6dc", String.valueOf(dimensionlist.getSample6dc()));
                intent.putExtra("getSample7dc", String.valueOf(dimensionlist.getSmple7dc()));

                intent.putExtra("getSample8dc", String.valueOf(dimensionlist.getSample8dc()));
                intent.putExtra("getSample9dc", String.valueOf(dimensionlist.getSample9dc()));
                intent.putExtra("getSample10dc", String.valueOf(dimensionlist.getSample10dc()));

                intent.putExtra("getLoweSpecs", String.valueOf(dimensionlist.getLoweSpecs()));
                intent.putExtra("getUpperSpecs", String.valueOf(dimensionlist.getUpperSpecs()));

                intent.putExtra("getMinimum", String.valueOf(dimensionlist.getMinimum()));
                intent.putExtra("getAverage", String.valueOf(dimensionlist.getAverage()));
                intent.putExtra("getMaximum", String.valueOf(dimensionlist.getMaximum()));
                intent.putExtra("getJudgement", String.valueOf(dimensionlist.getJudgement()));

                intent.putExtra("getdatedim", String.valueOf(dimensionlist.getDate()));
                intent.putExtra("getrem", String.valueOf(dimensionlist.getRemarks()));
                activity.startActivityForResult(intent, -1);
            }
        });


    }

    @Override
    public int getItemCount() {

        return dimensionData.size();

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


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dcid_txt, dcinstrument_txt, dc1_txt, dc2_txt, dc3_txt, dc4_txt, dc5_txt, dc6_txt, dc7_txt, dc8_txt, dc9_txt, dc10_txt, dc_Minimum_txt, dc_Maximum_txt, dc_Average_txt, dc_Judgemen_txt,
                dc_checkPoints_txt, upperSpec_txt, lowerSpec_txt, sammpleUnit_txt, dcsampleSize_txt, datedim_txt, remarks_txt;
        LinearLayout dimensionLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dcid_txt = itemView.findViewById(R.id.dcid_txt);
            dcinstrument_txt = itemView.findViewById(R.id.instrumentUsed_txt);
            sammpleUnit_txt = itemView.findViewById(R.id.sampleUnit_txt);
            dcsampleSize_txt = itemView.findViewById(R.id.sampleSizedc_txt);
            dc_checkPoints_txt = itemView.findViewById(R.id.checkPointdc);
            dc1_txt = itemView.findViewById(R.id.dc1_txt);
            dc2_txt = itemView.findViewById(R.id.dc2_txt);
            dc3_txt = itemView.findViewById(R.id.dc3_txt);
            dc4_txt = itemView.findViewById(R.id.dc4_txt);
            dc5_txt = itemView.findViewById(R.id.dc5_txt);
            dc6_txt = itemView.findViewById(R.id.dc6_txt);
            dc7_txt = itemView.findViewById(R.id.dc7_txt);
            dc8_txt = itemView.findViewById(R.id.dc8_txt);
            dc9_txt = itemView.findViewById(R.id.dc9_txt);
            dc10_txt = itemView.findViewById(R.id.dc10_txt);


            lowerSpec_txt = itemView.findViewById(R.id.lowerdc_txt);

            upperSpec_txt = itemView.findViewById(R.id.upperspecs_txt);


            dc_Minimum_txt = itemView.findViewById(R.id.dcminimum_txt);
            dc_Maximum_txt = itemView.findViewById(R.id.dcmaximum_txt);
            dc_Average_txt = itemView.findViewById(R.id.averagedc_txt);
            dc_Judgemen_txt = itemView.findViewById(R.id.dcjudgeMent_txt);
            datedim_txt = itemView.findViewById(R.id.datedim_txt);
            dimensionLayout = itemView.findViewById(R.id.dimensionLayout);
            remarks_txt = itemView.findViewById(R.id.remarksdc_txt);
        }
    }
}
