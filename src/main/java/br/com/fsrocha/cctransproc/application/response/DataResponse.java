package br.com.fsrocha.cctransproc.application.response;

import lombok.Data;

import java.util.List;

@Data
public class DataResponse<T> {

    private List<T> items;
    private ListInformation listInformation;

    public DataResponse(List<T> items, ListInformation listInformation) {
        this.items = items;
        this.listInformation = listInformation;
    }
}
