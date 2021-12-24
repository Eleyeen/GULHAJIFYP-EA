package com.example.bsnotes.activities.models;

public class HolderClass_Device_Info {
    HolderClass_Device_Info() {

    }
    public String devicedescription;
    public String deviceimage;
    public String devicename;
    public String offTimingDevice;
    public String onTimingDevice;


    public  String shopNumDevice;
    public String shopcity;
    public String wardenEmail;

    public String wardenName;
    public String wardenPassword;

    public String wardenUid;
    public String wardenphonenumber;



    public HolderClass_Device_Info(
            String devicedescription,
            String deviceimage,
            String devicename,
            String offTimingDevice,

            String onTimingDevice,
                                   String shopNumDevice,
                                   String shopcity,
                                   String wardenEmail,
            String wardenName,

            String wardenPassword,
                                   String wardenUid,
                                  String wardenphonenumber

            ) {
        this.onTimingDevice = onTimingDevice;
        this.offTimingDevice = offTimingDevice;
        this.shopNumDevice = shopNumDevice;
        this.devicename = devicename;
        this.shopcity = shopcity;
        this.devicedescription = devicedescription;
        this.wardenphonenumber = wardenphonenumber;
        this.deviceimage = deviceimage;
        this.wardenName = wardenName;
        this.wardenEmail = wardenEmail;
        this.wardenPassword = wardenPassword;
        this.wardenUid = wardenUid;
    }

    public String getDevicedescription() {
        return devicedescription;
    }

    public void setDevicedescription(String devicedescription) {
        this.devicedescription = devicedescription;
    }


    public String getDeviceimage() {
        return deviceimage;
    }

    public void setDeviceimage(String deviceimage) {
        this.deviceimage = deviceimage;
    }



    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }


    public String getOffTimingDevice() {
        return offTimingDevice;
    }

    public void setOffTimingDevice(String offTimingDevice) {
        this.offTimingDevice = offTimingDevice;
    }

    public String getOnTimingDevice() {
        return onTimingDevice;
    }

    public void setOnTimingDevice(String onTimingDevice) {
        this.onTimingDevice = onTimingDevice;
    }



    public String getShopNumDevice() {
        return shopNumDevice;
    }

    public void setShopNumDevice(String shopNumDevice) {
        this.shopNumDevice = shopNumDevice;
    }


    public String getShopcity() {
        return shopcity;
    }

    public void setShopcity(String shopcity) {
        this.shopcity = shopcity;
    }






    public String getWardenEmail() {
        return wardenEmail;
    }

    public void setWardenEmail(String wardenEmail) {
        this.wardenEmail = wardenEmail;
    }



    public String getWardenName() {
        return wardenName;
    }

    public void setWardenName(String wardenName) {
        this.wardenName = wardenName;
    }



    public String getWardenPassword() {
        return wardenPassword;
    }

    public void setWardenPassword(String wardenPassword) {
        this.wardenPassword = wardenPassword;
    }

    public String getWardenUid() {
        return wardenUid;
    }

    public void setWardenUid(String wardenUid) {
        this.wardenUid = wardenUid;
    }

    public String getWardenphonenumber() {
        return wardenphonenumber;
    }

    public void setWardenphonenumber(String wardenphonenumber) {
        this.wardenphonenumber = wardenphonenumber;
    }

}
