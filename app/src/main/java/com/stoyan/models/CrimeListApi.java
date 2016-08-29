package com.stoyan.models;

import java.util.List;

/**
 * Created by stoyan.dimitrov on 8/29/16.
 */

public class CrimeListApi {
    private   List<CrimeApi> crimeApiList;

    public List<CrimeApi> getCrimeApiList() {
        return crimeApiList;
    }

    public void setCrimeApiList(List<CrimeApi> crimeApiList) {
        this.crimeApiList = crimeApiList;
    }
}
