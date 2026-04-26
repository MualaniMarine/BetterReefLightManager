package com.mylhyl.zxing.scanner.result;

import com.google.zxing.client.result.TelParsedResult;

/* JADX INFO: loaded from: classes.dex */
public class TelResult extends Result {
    private final String number;
    private final String telURI;
    private final String title;

    public TelResult(TelParsedResult telParsedResult) {
        this.number = telParsedResult.getNumber();
        this.telURI = telParsedResult.getTelURI();
        this.title = telParsedResult.getTitle();
    }

    public String getNumber() {
        return this.number;
    }

    public String getTelURI() {
        return this.telURI;
    }

    public String getTitle() {
        return this.title;
    }
}
