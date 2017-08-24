package com.machinser.shihabthangalbloodbankadmin;


/**
 * Created by asnim on 26/05/17.
 */

public class BloodDonor {
   public String blood_group;
   public String full_name;public String age;

public     String address1;
   public String address2;
    public  String region;

    public BloodDonor() {
    }

    public String pincode;
    public String phone_number1;



    public BloodDonor( String region,String blood_group, String full_name, String age, String address1, String address2, String phone_number1

) {

        this.blood_group = blood_group;
        this.full_name = full_name;
        this.age = age;
        this.region = region;
        this.address1 = address1;
        this.address2 = address2;
//        this.pincode = pincode;
        this.phone_number1 = phone_number1;
    }
}
