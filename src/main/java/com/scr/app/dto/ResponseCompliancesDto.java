package com.scr.app.dto;

import java.util.List;

public class ResponseCompliancesDto {


    List<CompliancesDto> compliancesDtos;
    private Integer count;

    public List<CompliancesDto> getCompliancesDtos() {
        return compliancesDtos;
    }
    public void setCompliancesDtos(List<CompliancesDto> compliancesDtos) {
        this.compliancesDtos = compliancesDtos;
    }

    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
}