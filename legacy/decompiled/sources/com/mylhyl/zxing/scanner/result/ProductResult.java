package com.mylhyl.zxing.scanner.result;

import com.google.zxing.client.result.ProductParsedResult;

/* JADX INFO: loaded from: classes.dex */
public class ProductResult extends Result {
    private final String normalizedProductID;
    private final String productID;

    public ProductResult(ProductParsedResult productParsedResult) {
        this.productID = productParsedResult.getProductID();
        this.normalizedProductID = productParsedResult.getNormalizedProductID();
    }

    public String getProductID() {
        return this.productID;
    }

    public String getNormalizedProductID() {
        return this.normalizedProductID;
    }
}
