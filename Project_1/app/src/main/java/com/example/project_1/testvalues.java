package com.example.project_1;

public class testvalues {

    private String  email;
    private int sex, age, current_smoker, BP_meds, Pre_stroke, Pre_hype, cigs_per_day, diabetes, tot_chol, sys_BP, dia_BP, BMI, heartrate, glucose;
    private float result;

    public testvalues(String email, int sex, int age, int current_smoker, int BP_meds, int pre_stroke, int pre_hype, int diabetes,int cigs_per_day, int tot_chol, int sys_BP, int dia_BP, int BMI, int heartrate, int glucose, float result) {
        this.email = email;
        this.sex = sex;
        this.age = age;
        this.current_smoker = current_smoker;
        this.BP_meds = BP_meds;
        this.Pre_stroke = pre_stroke;
        this.Pre_hype = pre_hype;
        this.diabetes = diabetes;
        this.cigs_per_day = cigs_per_day;
        this.tot_chol = tot_chol;
        this.sys_BP = sys_BP;
        this.dia_BP = dia_BP;
        this.BMI = BMI;
        this.heartrate = heartrate;
        this.glucose = glucose;
        this.result = result;
    }

    public String isemail(){
        return email;
    }

    public int isSex() {
        return sex;
    }

    public int isAge() {
        return age;
    }

    public int isCurrent_smoker() {
        return current_smoker;
    }

    public int isBP_meds() {
        return BP_meds;
    }

    public int isPre_stroke() {
        return Pre_stroke;
    }

    public int isPre_hype() {
        return Pre_hype;
    }

    public int isDiabetes(){
        return diabetes;
    }

    public int getCigs_per_day() {
        return cigs_per_day;
    }

    public int getTot_chol() {
        return tot_chol;
    }

    public int getSys_BP() {
        return sys_BP;
    }

    public int getDia_BP() {
        return dia_BP;
    }

    public int getBMI() {
        return BMI;
    }

    public int getHeartrate() {
        return heartrate;
    }

    public int getGlucose() {
        return glucose;
    }

    public float getResult() {
        return result;
    }
}
