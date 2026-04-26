package com.mylhyl.zxing.scanner.result;

import com.google.zxing.client.result.URIParsedResult;

/* JADX INFO: loaded from: classes.dex */
public class URIResult extends Result {
    private final String title;
    private final String uri;

    public URIResult(URIParsedResult uRIParsedResult) {
        this.uri = uRIParsedResult.getURI();
        this.title = uRIParsedResult.getTitle();
    }

    public String getUri() {
        return this.uri;
    }

    public String getTitle() {
        return this.title;
    }
}
