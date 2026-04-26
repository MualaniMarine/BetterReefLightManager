package com.mylhyl.zxing.scanner.result;

import com.google.zxing.client.result.GeoParsedResult;

/* JADX INFO: loaded from: classes.dex */
public class GeoResult extends Result {
    private final double altitude;
    private final double latitude;
    private final double longitude;
    private final String query;

    public GeoResult(GeoParsedResult geoParsedResult) {
        this.latitude = geoParsedResult.getLatitude();
        this.longitude = geoParsedResult.getLongitude();
        this.altitude = geoParsedResult.getAltitude();
        this.query = geoParsedResult.getQuery();
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public String getQuery() {
        return this.query;
    }
}
