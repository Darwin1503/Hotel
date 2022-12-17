package com.example.aplicacion;

public class habitaciones {
    String id, des,costo;

    public habitaciones(String id, String des, String costo){
        this.id = id;
        this.des = des;
        this.costo = costo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }
}
