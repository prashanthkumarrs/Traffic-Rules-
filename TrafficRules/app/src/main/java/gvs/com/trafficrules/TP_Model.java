package gvs.com.trafficrules;

public class TP_Model {
    String servicenum;
    String id;
    String name;
    String email;
    String phone;
    String address;
    String password;
    String gender;
    String stationname;

    public TP_Model() {
    }

    public TP_Model(String servicenum, String id, String name, String email, String phone, String address, String password, String gender, String stationname) {
        this.servicenum = servicenum;
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.password = password;
        this.gender = gender;
        this.stationname = stationname;
    }

    public String getServicenum() {
        return servicenum;
    }

    public void setServicenum(String servicenum) {
        this.servicenum = servicenum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }
}

