package com.example.iqcapplication.encapsulation;

public class InspectionEncapsulation {

    int id;
   String invoicenum, preparedby, temp, assemblyline, partnum,partName, humidity, supplier,maker,  goodc,
    dateeee, inspector, dateinspected,samplesize,datereceived,invoicequant, reject,
    oir,inspecttype,testreport,mattype,inscoc,rohscomp,prodtype,ulmarking,coc;

    public InspectionEncapsulation(int id, String invoicenum, String preparedby, String temp, String assemblyline, String partnum, String partName, String humidity, String supplier, String maker,
                                   String goodc, String dateeee, String inspector, String dateinspected, String samplesize, String datereceived, String invoicequant,String reject, String oir, String inspecttype,
                                   String testreport, String mattype, String inscoc, String rohscomp, String prodtype, String ulmarking, String coc) {
        this.id = id;
        this.invoicenum = invoicenum;
        this.preparedby = preparedby;
        this.temp = temp;
        this.assemblyline = assemblyline;
        this.partnum = partnum;
        this.partName = partName;
        this.humidity = humidity;
        this.supplier = supplier;
        this.maker = maker;
        this.goodc = goodc;
        this.dateeee = dateeee;
        this.inspector = inspector;
        this.dateinspected = dateinspected;
        this.samplesize = samplesize;
        this.datereceived = datereceived;
        this.invoicequant = invoicequant;
        this.reject = reject;
        this.oir = oir;
        this.inspecttype = inspecttype;
        this.testreport = testreport;
        this.mattype = mattype;
        this.inscoc = inscoc;
        this.rohscomp = rohscomp;
        this.prodtype = prodtype;
        this.ulmarking = ulmarking;

        this.coc = coc;
    }

    public int getId() {
        return id;
    }

    public String getInvoicenum() {
        return invoicenum;
    }

    public String getPreparedby() {
        return preparedby;
    }

    public String getTemp() {
        return temp;
    }

    public String getAssemblyline() {
        return assemblyline;
    }

    public String getPartnum() {
        return partnum;
    }

    public String getPartName() {
        return partName;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getMaker() {
        return maker;
    }

    public String getGoodc() {
        return goodc;
    }

    public String getDateeee() {
        return dateeee;
    }

    public String getInspector() {
        return inspector;
    }

    public String getDateinspected() {
        return dateinspected;
    }

    public String getSamplesize() {
        return samplesize;
    }

    public String getDatereceived() {
        return datereceived;
    }

    public String getInvoicequant() {
        return invoicequant;
    }

    public String getReject() {
        return reject;
    }

    public String getOir() {
        return oir;
    }

    public String getInspecttype() {
        return inspecttype;
    }

    public String getTestreport() {
        return testreport;
    }

    public String getMattype() {
        return mattype;
    }

    public String getInscoc() {
        return inscoc;
    }

    public String getRohscomp() {
        return rohscomp;
    }

    public String getProdtype() {
        return prodtype;
    }

    public String getUlmarking() {
        return ulmarking;
    }
    public String getCoc() {
        return coc;
    }
}
