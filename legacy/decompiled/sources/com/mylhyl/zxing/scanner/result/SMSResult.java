package com.mylhyl.zxing.scanner.result;

import com.google.zxing.client.result.SMSParsedResult;

/* JADX INFO: loaded from: classes.dex */
public class SMSResult extends Result {
    private final String body;
    private final String[] numbers;
    private final String subject;
    private final String[] vias;

    public SMSResult(SMSParsedResult sMSParsedResult) {
        this.numbers = sMSParsedResult.getNumbers();
        this.vias = sMSParsedResult.getVias();
        this.subject = sMSParsedResult.getSubject();
        this.body = sMSParsedResult.getBody();
    }

    public String[] getNumbers() {
        return this.numbers;
    }

    public String[] getVias() {
        return this.vias;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getBody() {
        return this.body;
    }
}
