package com.tc.dto.pg;

import java.io.Serializable;

/**
 * @author zhenghongwei943
 * @date 2018/12/11
 * @description：
 **/
public class MedicineInCabinetReqDto implements Serializable {
    private String prescriptionCode;
    private String cabinetId;
    private String cabinetGridId;
    private String cabinetAddress;

    public MedicineInCabinetReqDto() {
    }

    public MedicineInCabinetReqDto(String prescriptionCode, String cabinetId, String cabinetGridId, String cabinetAddress) {
        this.prescriptionCode = prescriptionCode;
        this.cabinetId = cabinetId;
        this.cabinetGridId = cabinetGridId;
        this.cabinetAddress = cabinetAddress;
    }

    public String getPrescriptionCode() {
        return prescriptionCode;
    }

    public void setPrescriptionCode(String prescriptionCode) {
        this.prescriptionCode = prescriptionCode;
    }

    public String getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }

    public String getCabinetGridId() {
        return cabinetGridId;
    }

    public void setCabinetGridId(String cabinetGridId) {
        this.cabinetGridId = cabinetGridId;
    }

    public String getCabinetAddress() {
        return cabinetAddress;
    }

    public void setCabinetAddress(String cabinetAddress) {
        this.cabinetAddress = cabinetAddress;
    }

}
