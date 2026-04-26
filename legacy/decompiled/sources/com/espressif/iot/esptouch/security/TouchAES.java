package com.espressif.iot.esptouch.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class TouchAES implements ITouchEncryptor {
    private static final String TRANSFORMATION_DEFAULT = "AES/ECB/PKCS5Padding";
    private Cipher mDecryptCipher;
    private Cipher mEncryptCipher;
    private final byte[] mIV;
    private final byte[] mKey;
    private final String mTransformation;

    public TouchAES(byte[] key) {
        this(key, null, TRANSFORMATION_DEFAULT);
    }

    public TouchAES(byte[] key, String transformation) {
        this(key, null, transformation);
    }

    public TouchAES(byte[] key, byte[] iv) {
        this(key, iv, TRANSFORMATION_DEFAULT);
    }

    public TouchAES(byte[] key, byte[] iv, String transformation) {
        this.mKey = key;
        this.mIV = iv;
        this.mTransformation = transformation;
        this.mEncryptCipher = createEncryptCipher();
        this.mDecryptCipher = createDecryptCipher();
    }

    private Cipher createEncryptCipher() {
        try {
            Cipher cipher = Cipher.getInstance(this.mTransformation);
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.mKey, "AES");
            if (this.mIV == null) {
                cipher.init(1, secretKeySpec);
            } else {
                cipher.init(1, secretKeySpec, new IvParameterSpec(this.mIV));
            }
            return cipher;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Cipher createDecryptCipher() {
        try {
            Cipher cipher = Cipher.getInstance(this.mTransformation);
            SecretKeySpec secretKeySpec = new SecretKeySpec(this.mKey, "AES");
            if (this.mIV == null) {
                cipher.init(2, secretKeySpec);
            } else {
                cipher.init(2, secretKeySpec, new IvParameterSpec(this.mIV));
            }
            return cipher;
        } catch (InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override // com.espressif.iot.esptouch.security.ITouchEncryptor
    public byte[] encrypt(byte[] content) {
        try {
            return this.mEncryptCipher.doFinal(content);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decrypt(byte[] content) {
        try {
            return this.mDecryptCipher.doFinal(content);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
