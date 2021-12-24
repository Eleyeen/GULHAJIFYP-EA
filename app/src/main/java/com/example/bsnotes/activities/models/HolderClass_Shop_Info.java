package com.example.bsnotes.activities.models;

public class HolderClass_Shop_Info {

    HolderClass_Shop_Info() {

    }


    public String hostelcity;
    public String hosteldescription;
    public String hostelimage;
    public String hostelname;
    public String offTimingAdForm;
    public String onTimingAdForm;
    public String shopNumAdForm;
    public String wardenphonenumber;
    public String wardenName;
    public String wardenEmail;
    public String wardenPassword;
    public String wardenUid;

    public HolderClass_Shop_Info(

            String hostelcity,
            String hosteldescription,
            String hostelimage,
            String hostelname,
            String offTimingAdForm,
            String onTimingAdForm,
            String shopNumAdForm,
            String wardenEmail,
            String wardenName,
            String wardenPassword,
            String wardenUid,
            String wardenphonenumber
                                 ) {
        this.shopNumAdForm = shopNumAdForm;
        this.onTimingAdForm = onTimingAdForm;
        this.offTimingAdForm = offTimingAdForm;
        this.hostelname = hostelname;
        this.hostelcity = hostelcity;
        this.hosteldescription = hosteldescription;
        this.wardenphonenumber = wardenphonenumber;
        this.hostelimage = hostelimage;
        this.wardenName = wardenName;
        this.wardenEmail = wardenEmail;
        this.wardenPassword = wardenPassword;
        this.wardenUid = wardenUid;
    }


    public String getHostelcity() {
        return hostelcity;
    }

    public void setHostelcity(String hostelcity) {
        this.hostelcity = hostelcity;
    }




    public String getHosteldescription() {
        return hosteldescription;
    }

    public void setHosteldescription(String hosteldescription) {
        this.hosteldescription = hosteldescription;
    }




    public String getHostelimage() {
        return hostelimage;
    }

    public void setHostelimage(String hostelimage) {
        this.hostelimage = hostelimage;
    }


    public String getHostelname() {
        return hostelname;
    }

    public void setHostelname(String hostelname) {
        this.hostelname = hostelname;
    }


    public String getOffTimingAdForm() {
        return offTimingAdForm;
    }

    public void setOffTimingAdForm(String offTimingAdForm) {
        this.offTimingAdForm = offTimingAdForm;
    }



    public String getOnTimingAdForm() {
        return onTimingAdForm;
    }

    public void setOnTimingAdForm(String onTimingAdForm) {
        this.onTimingAdForm = onTimingAdForm;
    }




    public String getShopNumAdForm() {
        return shopNumAdForm;
    }

    public void setShopNumAdForm(String shopNumAdForm) {
        this.shopNumAdForm = shopNumAdForm;
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
