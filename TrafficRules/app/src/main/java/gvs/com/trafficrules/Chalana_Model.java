package gvs.com.trafficrules;

public class Chalana_Model {
    String cvehiclenum;
    String id;
    String cownername;
    String cphone;
    String ccmptitle;
    String ccmpdesc;
    String cchalana;
    String status;
    String handleby;
    String station;
    String opt;
    String comp_count;
    String date;

    public Chalana_Model() {
    }

    public Chalana_Model(String cvehiclenum, String id, String cownername, String cphone, String ccmptitle, String ccmpdesc, String cchalana, String status, String handleby, String station, String opt,String comp_count,String date) {
        this.cvehiclenum = cvehiclenum;
        this.id = id;
        this.cownername = cownername;
        this.cphone = cphone;
        this.ccmptitle = ccmptitle;
        this.ccmpdesc = ccmpdesc;
        this.cchalana = cchalana;
        this.status = status;
        this.handleby = handleby;
        this.station = station;
        this.opt = opt;
        this.comp_count=comp_count;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComp_count() {
        return comp_count;
    }

    public void setComp_count(String comp_count) {
        this.comp_count = comp_count;
    }

    public String getCvehiclenum() {
        return cvehiclenum;
    }

    public void setCvehiclenum(String cvehiclenum) {
        this.cvehiclenum = cvehiclenum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCownername() {
        return cownername;
    }

    public void setCownername(String cownername) {
        this.cownername = cownername;
    }

    public String getCphone() {
        return cphone;
    }

    public void setCphone(String cphone) {
        this.cphone = cphone;
    }

    public String getCcmptitle() {
        return ccmptitle;
    }

    public void setCcmptitle(String ccmptitle) {
        this.ccmptitle = ccmptitle;
    }

    public String getCcmpdesc() {
        return ccmpdesc;
    }

    public void setCcmpdesc(String ccmpdesc) {
        this.ccmpdesc = ccmpdesc;
    }

    public String getCchalana() {
        return cchalana;
    }

    public void setCchalana(String cchalana) {
        this.cchalana = cchalana;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHandleby() {
        return handleby;
    }

    public void setHandleby(String handleby) {
        this.handleby = handleby;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }
}
