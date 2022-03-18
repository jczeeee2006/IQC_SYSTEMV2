package com.example.iqcapplication.encapsulation;

public class LotEncapsulation {
    int id;
    String totalquantity, quantityrecieved, lotno, lotquant, boxnum, reject, sampsize, boxseqid,remarks, lot_invoiceno, tv_partname, goodsc, et_partnum;;

    public LotEncapsulation(int id,String lot_invoiceno,String et_partnum, String goodsc,   String tv_partname,  String boxnum, String boxseqid,String quantityrecieved,  String totalquantity,
                            String reject, String sampsize,    String lotno, String lotquant,
                            String remarks ) {
        this.id = id;
        this.lot_invoiceno = lot_invoiceno;
        this.et_partnum = et_partnum;
        this.goodsc = goodsc;
        this.tv_partname = tv_partname;
        this.boxnum = boxnum;
        this.boxseqid = boxseqid;
        this.quantityrecieved = quantityrecieved;
        this.totalquantity = totalquantity;
        this.reject = reject;
        this.sampsize = sampsize;
        this.lotno = lotno;
        this.lotquant = lotquant;
        this.remarks = remarks;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTotalquantity() {
        return totalquantity;
    }

    public void setTotalquantity(String totalquantity) {
        this.totalquantity = totalquantity;
    }

    public String getQuantityrecieved() {
        return quantityrecieved;
    }

    public void setQuantityrecieved(String quantityrecieved) {
        this.quantityrecieved = quantityrecieved;
    }

    public String getLotno() {
        return lotno;
    }

    public void setLotno(String lotno) {
        this.lotno = lotno;
    }

    public String getLotquant() {
        return lotquant;
    }

    public void setLotquant(String lotquant) {
        this.lotquant = lotquant;
    }

    public String getBoxnum() {
        return boxnum;
    }

    public void setBoxnum(String boxnum) {
        this.boxnum = boxnum;
    }

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

    public String getSampsize() {
        return sampsize;
    }

    public void setSampsize(String sampsize) {
        this.sampsize = sampsize;
    }

    public String getBoxseqid() {
        return boxseqid;
    }

    public void setBoxseqid(String boxseqid) {
        this.boxseqid = boxseqid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getLot_invoiceno() {
        return lot_invoiceno;
    }

    public void setLot_invoiceno(String lot_invoiceno) {
        this.lot_invoiceno = lot_invoiceno;
    }

    public String getTv_partname() {
        return tv_partname;
    }

    public void setTv_partname(String tv_partname) {
        this.tv_partname = tv_partname;
    }

    public String getGoodsc() {
        return goodsc;
    }

    public void setGoodsc(String goodsc) {
        this.goodsc = goodsc;
    }

    public String getEt_partnum() {
        return et_partnum;
    }

    public void setEt_partnum(String et_partnum) {
        this.et_partnum = et_partnum;
    }
}
