package com.scr.app.dto;

import java.util.List;

public class ResponseObservationsDto {

    private List<ObservationsDto> observationsDtos;
    private int count;


    public List<ObservationsDto> getObservationsDtos() {
        return observationsDtos;
    }
    public void setObservationsDtos(List<ObservationsDto> observationsDtos) {
        this.observationsDtos = observationsDtos;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

}
