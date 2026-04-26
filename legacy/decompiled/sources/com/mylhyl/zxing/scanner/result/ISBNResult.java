package com.mylhyl.zxing.scanner.result;

import com.google.zxing.client.result.ISBNParsedResult;

/* JADX INFO: loaded from: classes.dex */
public class ISBNResult extends Result {
    private final String isbn;

    public ISBNResult(ISBNParsedResult iSBNParsedResult) {
        this.isbn = iSBNParsedResult.getISBN();
    }

    public String getISBN() {
        return this.isbn;
    }
}
