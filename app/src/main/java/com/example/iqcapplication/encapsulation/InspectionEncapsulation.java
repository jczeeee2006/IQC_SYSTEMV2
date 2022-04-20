package com.example.iqcapplication.encapsulation;

import java.sql.Date;

public class InspectionEncapsulation {


    int id;
    String dateToday;
     String preparedby, dateeee,  invoicenum,  goodc,  partName,  invoicequant,  assemblyline,  partnum,  temp,  rohscomp,  dateinspected,   humidity,
     supplier,   inspector, datereceived,   maker,  samplesize,  mattype,    inspecttype,  ulmarking, oir,
     inscoc,   prodtype,  testreport,boxseqid;

    public InspectionEncapsulation(int id, String preparedby, String dateeee, String invoicenum, String goodc, String partName, String invoicequant, String assemblyline,
                                   String partnum, String temp, String rohscomp, String dateinspected, String humidity, String supplier, String inspector, String datereceived,
                                   String maker, String samplesize, String mattype, String inspecttype, String ulmarking, String oir, String inscoc, String prodtype, String testreport, String dateToday, String boxseqid

    ) {
        this.id = id;
        this.preparedby = preparedby;
        this.dateeee = dateeee;
        this.invoicenum = invoicenum;
        this.goodc = goodc;

        this.partName = partName;
        this.invoicequant = invoicequant;
        this.assemblyline = assemblyline;
        this.partnum = partnum;
        this.temp = temp;

        this.rohscomp = rohscomp;
        this.dateinspected = dateinspected;
        this.humidity = humidity;
        this.supplier = supplier;
        this.inspector = inspector;

        this.datereceived = datereceived;
        this.maker = maker;
        this.samplesize = samplesize;
        this.mattype = mattype;
        this.inspecttype = inspecttype;

        this.ulmarking = ulmarking;
        this.oir = oir;
        this.inscoc = inscoc;
        this.prodtype = prodtype;
        this.testreport = testreport;
        this.dateToday = dateToday;
        this.boxseqid = boxseqid;
    }

    public int getId() {
        return id;
    }

    public String getBoxseqid() {
        return boxseqid;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public String getDateeee() {
        return dateeee;
    }

    public String getInvoicenum() {
        return invoicenum;
    }

    public String getGoodc() {
        return goodc;
    }

    public String getPartName() {
        return partName;
    }

    public String getInvoicequant() {
        return invoicequant;
    }

    public String getAssemblyline() {
        return assemblyline;
    }

    public String getPartnum() {
        return partnum;
    }

    public String getTemp() {
        return temp;
    }

    public String getRohscomp() {
        return rohscomp;
    }

    public String getDateinspected() {
        return dateinspected;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getDateToday() {
        return dateToday;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getInspector() {
        return inspector;
    }

    public String getDatereceived() {
        return datereceived;
    }

    public String getMaker() {
        return maker;
    }

    public String getSamplesize() {
        return samplesize;
    }

    public String getMattype() {
        return mattype;
    }

    public String getInspecttype() {
        return inspecttype;
    }

    public String getUlmarking() {
        return ulmarking;
    }

    public String getOir() {
        return oir;
    }

    public String getInscoc() {
        return inscoc;
    }

    public String getProdtype() {
        return prodtype;
    }

    public String getTestreport() {
        return testreport;
    }
}
