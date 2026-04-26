package com.mylhyl.zxing.scanner.result;

import com.google.zxing.client.result.AddressBookParsedResult;

/* JADX INFO: loaded from: classes.dex */
public class AddressBookResult extends Result {
    private final String[] addressTypes;
    private final String[] addresses;
    private final String birthday;
    private final String[] emailTypes;
    private final String[] emails;
    private final String[] geo;
    private final String instantMessenger;
    private final String[] names;
    private final String[] nicknames;
    private final String note;

    /* JADX INFO: renamed from: org, reason: collision with root package name */
    private final String f907org;
    private final String[] phoneNumbers;
    private final String[] phoneTypes;
    private final String pronunciation;
    private final String title;
    private final String[] urls;

    public AddressBookResult(AddressBookParsedResult addressBookParsedResult) {
        this.names = addressBookParsedResult.getNames();
        this.nicknames = addressBookParsedResult.getNicknames();
        this.pronunciation = addressBookParsedResult.getPronunciation();
        this.phoneNumbers = addressBookParsedResult.getPhoneNumbers();
        this.phoneTypes = addressBookParsedResult.getPhoneTypes();
        this.emails = addressBookParsedResult.getEmails();
        this.emailTypes = addressBookParsedResult.getEmailTypes();
        this.instantMessenger = addressBookParsedResult.getInstantMessenger();
        this.note = addressBookParsedResult.getNote();
        this.addresses = addressBookParsedResult.getAddresses();
        this.addressTypes = addressBookParsedResult.getAddressTypes();
        this.f907org = addressBookParsedResult.getOrg();
        this.birthday = addressBookParsedResult.getBirthday();
        this.title = addressBookParsedResult.getTitle();
        this.urls = addressBookParsedResult.getURLs();
        this.geo = addressBookParsedResult.getGeo();
    }

    public String[] getNames() {
        return this.names;
    }

    public String[] getNicknames() {
        return this.nicknames;
    }

    public String getPronunciation() {
        return this.pronunciation;
    }

    public String[] getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public String[] getPhoneTypes() {
        return this.phoneTypes;
    }

    public String[] getEmails() {
        return this.emails;
    }

    public String[] getEmailTypes() {
        return this.emailTypes;
    }

    public String getInstantMessenger() {
        return this.instantMessenger;
    }

    public String getNote() {
        return this.note;
    }

    public String[] getAddresses() {
        return this.addresses;
    }

    public String[] getAddressTypes() {
        return this.addressTypes;
    }

    public String getOrg() {
        return this.f907org;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getTitle() {
        return this.title;
    }

    public String[] getUrls() {
        return this.urls;
    }

    public String[] getGeo() {
        return this.geo;
    }
}
