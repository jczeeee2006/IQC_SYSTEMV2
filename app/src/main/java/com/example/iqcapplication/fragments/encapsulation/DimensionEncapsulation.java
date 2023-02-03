package com.example.iqcapplication.fragments.encapsulation;

public class DimensionEncapsulation {
    int id;
    String lowerSpecs,upperSpecs,minimum,average,Maximum,date,remarks ;
    String InstrumentUsed, sampleSize, sampleUnit, checkpointdc, sample1dc, sample2dc, sample3dc, sample4dc, sample5dc, sample6dc, sample7dc, sample8dc, sample9dc, sample10dc,judgement,po_number;

    public DimensionEncapsulation(int id,  String instrumentUsed, String sampleSize, String checkpointdc, String sampleUnit,
                              String sample1dc, String sample2dc, String sample3dc, String sample4dc, String sample5dc, String sample6dc, String smple7dc,
                                  String sample8dc, String sample9dc, String sample10dc, String remarks, String lowerSpecs, String upperSpecs, String minimum, String average, String maximum, String judgement, String date, String po) {
        this.id = id;
        this.InstrumentUsed = instrumentUsed;
        this.sampleSize = sampleSize;
        this.sampleUnit = sampleUnit;
        this.checkpointdc = checkpointdc;

        this.sample1dc = sample1dc;
        this.sample2dc = sample2dc;
        this.sample3dc = sample3dc;
        this.sample4dc = sample4dc;
        this.sample5dc = sample5dc;
        this.sample6dc = sample6dc;
        this.sample7dc = smple7dc;
        this.sample8dc = sample8dc;
        this.sample9dc = sample9dc;
        this.sample10dc = sample10dc;

        this.lowerSpecs = lowerSpecs;
        this.upperSpecs = upperSpecs;

        this.minimum = minimum;
        this.average = average;
        this.Maximum = maximum;
        this.judgement = judgement;
        this.date = date;
        this.remarks = remarks;
        this.po_number = po;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getPo_number() {
        return po_number;
    }

    public String getRemarks() {
        return remarks;
    }

    public String getLoweSpecs() {
        return lowerSpecs;
    }

    public String getUpperSpecs() {
        return upperSpecs;
    }

    public String getMinimum() {
        return minimum;
    }

    public String getAverage() {
        return average;
    }

    public String getMaximum() {
        return Maximum;
    }

    public String getInstrumentUsed() {
        return InstrumentUsed;
    }

    public String getSampleSize() {
        return sampleSize;
    }

    public String getSampleUnit() {
        return sampleUnit;
    }

    public String getCheckpointdc() {
        return checkpointdc;
    }

    public String getSample1dc() {
        return sample1dc;
    }

    public String getSample2dc() {
        return sample2dc;
    }

    public String getSample3dc() {
        return sample3dc;
    }

    public String getSample4dc() {
        return sample4dc;
    }

    public String getSample5dc() {
        return sample5dc;
    }

    public String getSample6dc() {
        return sample6dc;
    }

    public String getSmple7dc() {
        return sample7dc;
    }

    public String getSample8dc() {
        return sample8dc;
    }

    public String getSample9dc() {
        return sample9dc;
    }

    public String getSample10dc() {
        return sample10dc;
    }

    public String getJudgement() {
        return judgement;
    }
}
