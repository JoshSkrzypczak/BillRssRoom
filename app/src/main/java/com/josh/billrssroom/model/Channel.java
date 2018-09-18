package com.josh.billrssroom.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class Channel {

    @ElementList(name = "item", inline = true)
    public List<BillModel> billModels;

    @Element(required = false)
    private String pubDate;
    @Element(required = false)
    private String title;
    @Element(required = false)
    private String managingEditor;
    @Element(required = false)
    private String description;
    @Element(required = false)
    private String docs;
    @Element(required = false)
    private String link;
    @Element(required = false)
    private String lastBuildDate;
    @Element(required = false)
    private String generator;
    @Element(required = false)
    private String language;
    @Element(required = false)
    private String webMaster;

    public List<BillModel> getBillModels() {
        return billModels;
    }

    public void setBillModels(List<BillModel> billModels) {
        this.billModels = billModels;
    }
}
