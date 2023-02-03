package com.example.iqcapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.iqcapplication.Update.LotFormActivity;

public class ExampleDialog  extends AppCompatDialogFragment {
    private TextView invoiceDialog,partNumDialog,partnameDialog,goodsCodeDialog;
    private EditText POdialog, totalQuantity;
    private ExampleDialogListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_box,null);

        builder.setView(view)
                .setTitle("DOUBLE CHECK P.O IF DUPLICATE");


//
//                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                })
//                .setPositiveButton("upload", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String invoice = invoiceDialog.getText().toString();
//                        String partnum = partNumDialog.getText().toString();
//                        String partName = partnameDialog.getText().toString();
//                        String goodsc = goodsCodeDialog.getText().toString();
//                        String poDialog = POdialog.getText().toString();
//                        String totalQuant = totalQuantity.getText().toString();
//                        listener.applyTexts(invoice,partnum,partName,goodsc,poDialog,totalQuant);
//                    }
//                });

        invoiceDialog = view.findViewById(R.id.invoiceDialog_txt);
        partNumDialog = view.findViewById(R.id.partNumDialog_txt);
        partnameDialog = view.findViewById(R.id.partNameDialog_txt3);
        goodsCodeDialog = view.findViewById(R.id.goodsCodeDialog_txt);
        POdialog = view.findViewById(R.id.PoDialog_txt);
        totalQuantity = view.findViewById(R.id.totalquantityDialog_txt);
        invoiceDialog.setText(SapmpleActivityinlot.invoicenumholder);
        partNumDialog.setText(SapmpleActivityinlot.partnumholder);
        partnameDialog.setText(SapmpleActivityinlot.partnameholder);
        goodsCodeDialog.setText(SapmpleActivityinlot.goodscodeholder);
        POdialog.setText(SapmpleActivityinlot.poholder);

        return  builder.create();
    }

    @Nullable


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        LayoutInflater inflater = getActivity().getLayoutInflater();


        try{
            listener = (ExampleDialogListener) context;

        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() +
                    "must Implement Diaog Listener");
        }


    }




    public interface ExampleDialogListener{
        void applyTexts(String invoice,String partnum,String partname,String goodsCode,String P,String QTY);
    }
}
