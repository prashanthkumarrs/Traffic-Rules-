package gvs.com.trafficrules;

public class Complaint_Model {
    String cmptitle;
    String id;
    String cmpdesc;
    String cmpic;
    String cmp_by;
    String cmp_date;
    String cmp_status;
    String psname;

    public Complaint_Model() {
    }

    public Complaint_Model(String cmptitle, String id, String cmpdesc, String cmpic,String cmp_by,String cmp_date,String cmp_status,String psname) {
        this.cmptitle = cmptitle;
        this.id = id;
        this.cmpdesc = cmpdesc;
        this.cmpic = cmpic;
        this.cmp_by = cmp_by;
        this.cmp_date = cmp_date;
        this.cmp_status = cmp_status;
        this.psname=psname;
    }

    public String getPsname() {
        return psname;
    }

    public void setPsname(String psname) {
        this.psname = psname;
    }

    public String getCmp_date() {
        return cmp_date;
    }

    public void setCmp_date(String cmp_date) {
        this.cmp_date = cmp_date;
    }

    public String getCmp_status() {
        return cmp_status;
    }

    public void setCmp_status(String cmp_status) {
        this.cmp_status = cmp_status;
    }

    public String getCmp_by() {
        return cmp_by;
    }

    public void setCmp_by(String cmp_by) {
        this.cmp_by = cmp_by;
    }

    public String getCmptitle() {
        return cmptitle;
    }

    public void setCmptitle(String cmptitle) {
        this.cmptitle = cmptitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCmpdesc() {
        return cmpdesc;
    }

    public void setCmpdesc(String cmpdesc) {
        this.cmpdesc = cmpdesc;
    }

    public String getCmpic() {
        return cmpic;
    }

    public void setCmpic(String cmpic) {
        this.cmpic = cmpic;
    }
}
