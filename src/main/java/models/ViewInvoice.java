package models;

import java.util.ArrayList;

public class ViewInvoice {
    private Invoice invoice;
    private ArrayList<InvoiceItem> items ;

    public ViewInvoice(){
        this.items = new ArrayList<InvoiceItem>();
    }

    public Invoice getInvoice(){
        return this.invoice;
    }
    public void addToInvoiceList(InvoiceItem item){
        this.items.add(item);
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }



}
