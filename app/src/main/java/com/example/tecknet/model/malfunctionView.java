package com.example.tecknet.model;

public class malfunctionView {

    String area;
    String device;

    public malfunctionView(String area, String device) {
        this.area = area;
        this.device = device;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }
}
