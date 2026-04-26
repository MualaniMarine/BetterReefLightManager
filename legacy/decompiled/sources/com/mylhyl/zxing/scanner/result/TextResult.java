package com.mylhyl.zxing.scanner.result;

import com.google.zxing.client.result.TextParsedResult;

/* JADX INFO: loaded from: classes.dex */
public class TextResult extends Result {
    private final String language;
    private final String text;

    public TextResult(TextParsedResult textParsedResult) {
        this.text = textParsedResult.getText();
        this.language = textParsedResult.getLanguage();
    }

    public String getText() {
        return this.text;
    }

    public String getLanguage() {
        return this.language;
    }
}
