package gvs.com.trafficrules;

public class Vehicle_Model {
    String vehiclenum;
    String id;
    String owner_name;
    String phone;

    public Vehicle_Model() {
    }

    public Vehicle_Model(String vehiclenum, String id, String owner_name, String phone) {
        this.vehiclenum = vehiclenum;
        this.id = id;
        this.owner_name = owner_name;
        this.phone = phone;
    }

    public String getVehiclenum() {
        return vehiclenum;
    }

    public void setVehiclenum(String vehiclenum) {
        this.vehiclenum = vehiclenum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
