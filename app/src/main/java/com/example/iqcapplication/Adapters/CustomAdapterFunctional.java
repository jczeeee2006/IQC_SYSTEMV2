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
import com.example.iqcapplication.Update.FunctionalActivity;
import com.example.iqcapplication.encapsulation.DimensionEncapsulation;
import com.example.iqcapplication.encapsulation.FunctionalEncapsulation;

import java.util.ArrayList;

public class CustomAdapterFunctional extends RecyclerView.Adapter<CustomAdapterFunctional.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList<FunctionalEncapsulation> functionalEncapsulations = new ArrayList<>();


    public CustomAdapterFunctional( Activity activity,Context context, ArrayList<FunctionalEncapsulation> functionalEncapsulations) {

        this.activity = activity;
        this.context = context;
        this.functionalEncapsulations = functionalEncapsulations;
    }


    @NonNull
    @Override
    public CustomAdapterFunctional.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_functional_data,parent,false);

        return  new  CustomAdapterFunctional.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterFunctional.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FunctionalEncapsulation functionlist = functionalEncapsulations.get(position);
        holder.fcid_txt.setText(String.valueOf(functionlist.getdcID()));
        holder.fcinstrument_txt.setText(String.valueOf(functionlist.getInstrumentUsed()));
        holder.fcsampleSize_txt.setText(String.valueOf(functionlist.getSampleSize()));
        holder.fc_checkPoins_txt.setText(String.valueOf(functionlist.getCheckpointdc()));
        holder.sammpleUnit_txt.setText(String.valueOf(functionlist.getSampleUnit()));

        holder.fc1_txt.setText(String.valueOf(functionlist.getSample1dc()));
        holder.fc2_txt.setText(String.valueOf(functionlist.getSample2dc()));
        holder.fc3_txt.setText(String.valueOf(functionlist.getSample3dc()));
        holder.fc4_txt.setText(String.valueOf(functionlist.getSample4dc()));
        holder.fc5_txt.setText(String.valueOf(functionlist.getSample5dc()));

        holder.fc6_txt.setText(String.valueOf(functionlist.getSample6dc()));
        holder.fc7_txt.setText(String.valueOf(functionlist.getSmple7dc()));
        holder.fc8_txt.setText(String.valueOf(functionlist.getSample8dc()));
        holder.fc9_txt.setText(String.valueOf(functionlist.getSample9dc()));
        holder.fc10_txt.setText(String.valueOf(functionlist.getSample10dc()));

        holder.lowerSpec_txt.setText(String.valueOf(functionlist.getLoweSpecs()));
        holder.upperSpec_txt.setText(String.valueOf(functionlist.getUpperSpecs()));
        holder.fc_Minimum_txt.setText(String.valueOf(functionlist.getMinimum()));
        holder.fc_Average_txt.setText(String.valueOf(functionlist.getAverage()));
        holder.fc_Maximum_txt.setText(String.valueOf(functionlist.getMaximum()));

        holder.fc_Judgemen_txt.setText(String.valueOf(functionlist.getJudgement()));
        holder.fdatedim_txt.setText(String.valueOf(functionlist.getDate()));

        holder.functionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FunctionalActivity.class);
                FunctionalEncapsulation functionalEncapsulation = functionalEncapsulations.get(position);

                intent.putExtra("id", String.valueOf(functionalEncapsulation.getdcID()));
                intent.putExtra("getInstrumentUsed", String.valueOf(functionalEncapsulation.getInstrumentUsed()));
                intent.putExtra("getSampleUnit", String.valueOf(functionalEncapsulation.getSampleUnit()));
                intent.putExtra("getSampleSize", String.valueOf(functionalEncapsulation.getSampleSize()));

                intent.putExtra("getSample1dc", String.valueOf(functionalEncapsulation.getSample1dc()));
                intent.putExtra("getSample2dc", String.valueOf(functionalEncapsulation.getSample2dc()));

                intent.putExtra("getSample3dc", String.valueOf(functionalEncapsulation.getSample3dc()));
                intent.putExtra("getSample4dc", String.valueOf(functionalEncapsulation.getSample4dc()));
                intent.putExtra("getSample5dc", String.valueOf(functionalEncapsulation.getSample5dc()));
                intent.putExtra("getSample6dc", String.valueOf(functionalEncapsulation.getSample6dc()));
                intent.putExtra("getSample7dc", String.valueOf(functionalEncapsulation.getSmple7dc()));

                intent.putExtra("getSample8dc", String.valueOf(functionalEncapsulation.getSample8dc()));
                intent.putExtra("getSample9dc", String.valueOf(functionalEncapsulation.getSample9dc()));
                intent.putExtra("getSample10dc", String.valueOf(functionalEncapsulation.getSample10dc()));


                intent.putExtra("getCheckpointdc", String.valueOf(functionalEncapsulation.getCheckpointdc()));
                intent.putExtra("getUpperSpecs",   String.valueOf(functionalEncapsulation.getUpperSpecs()));
                intent.putExtra("getLoweSpecs",    String.valueOf(functionalEncapsulation.getLoweSpecs()));
                intent.putExtra("getMinimum",      String.valueOf(functionalEncapsulation.getMinimum()));
                intent.putExtra("getAverage",      String.valueOf(functionalEncapsulation.getAverage()));
                intent.putExtra("getMaximum",      String.valueOf(functionalEncapsulation.getMaximum()));
                intent.putExtra("getJudgement",    String.valueOf(functionalEncapsulation.getJudgement()));

                intent.putExtra("getdatedim", String.valueOf(functionalEncapsulation.getDate()));

                activity.startActivityForResult(intent,-1);
            }
        });



    }

    @Override
    public int getItemCount() {
        return functionalEncapsulations.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  fcid_txt, fcinstrument_txt,  fc1_txt,fc2_txt,fc3_txt,fc4_txt,fc5_txt,fc6_txt,fc7_txt,fc8_txt,fc9_txt,fc10_txt, fc_Minimum_txt,fc_Maximum_txt,fc_Average_txt,fc_Judgemen_txt, fc_checkPoins_txt,
        upperSpec_txt,lowerSpec_txt,sammpleUnit_txt,fcsampleSize_txt, fdatedim_txt;

        LinearLayout functionLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fcid_txt = itemView.findViewById(R.id.fcid_txt);
            fcinstrument_txt = itemView.findViewById(R.id.fcinstrumentUsed_txt);


            fc1_txt = itemView.findViewById(R.id.fc1_txt);
            fc2_txt = itemView.findViewById(R.id.fc2_txt);
            fc3_txt = itemView.findViewById(R.id.fc3_txt);
            fc4_txt = itemView.findViewById(R.id.fc4_txt);
            fc5_txt = itemView.findViewById(R.id.fc5_txt);


            fc6_txt = itemView.findViewById(R.id.fc6_txt);
            fc7_txt = itemView.findViewById(R.id.fc7_txt);
            fc8_txt = itemView.findViewById(R.id.fc8_txt);
            fc9_txt = itemView.findViewById(R.id.fc9_txt);
            fc10_txt = itemView.findViewById(R.id.fc10_txt);

            fc_Minimum_txt = itemView.findViewById(R.id.fcminimum_txt);
            fc_Maximum_txt = itemView.findViewById(R.id.fcmaximum_txt);
            fc_Average_txt = itemView.findViewById(R.id.averagefc);

            fc_Judgemen_txt  = itemView.findViewById(R.id.dcjudgeMent_txt);
            fc_checkPoins_txt  = itemView.findViewById(R.id.checkPointfc);

            upperSpec_txt  = itemView.findViewById(R.id.fcupperspecs_txt);
            lowerSpec_txt  = itemView.findViewById(R.id.fclowerspec_txt);
            sammpleUnit_txt  = itemView.findViewById(R.id.fcsampleUnit_txt);
            fcsampleSize_txt  = itemView.findViewById(R.id.sampleSizefc_txt);

            fdatedim_txt =  itemView.findViewById(R.id.fdatedim_txt);
            functionLayout =  itemView.findViewById(R.id.functionalLayout);
        }
    }
}
