package com.kamathajith.mobile.android.csrrequester;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Ajith Kamath on 3/16/15.
 *
 * This class defines the Signature generator.
 * Currently supports the ECDSA signature algorithm
 *
 */
public class SignatureGenerator {

    private static final String TAG = SignatureGenerator.class.getName();
    private static final String UTF8_CHARSET = "UTF-8";

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private KeyPair keyPair;

    private AlgorithmVendor algorithmVendor;

    /**
     * This sets up the algorithm vendor for the
     * keypair,secureRandom and signing algorithms to use
     *
     * @param algorithmVendor
     */
    public SignatureGenerator(AlgorithmVendor algorithmVendor) {
        this.algorithmVendor = algorithmVendor;
    }

    /**
     * Generate an ECDSA signature
     *
     * @param certificateInfo
     * @return
     */
    public Signature generateSignatureForCertificateInfo(CertificateInfo certificateInfo) {

        if (this.algorithmVendor == null) {
            Log.e(TAG, "Canoot generate a signature without an alogithm vendor");
            return null;
        }

        KeyPairGenerator keyGen = null;
        SecureRandom random = null;

        ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime256v1");

        // create the KeyPairGenerator
        try {
            keyGen = KeyPairGenerator.getInstance(this.algorithmVendor.getKeyPairGeneratorAlgorithm(), this.algorithmVendor.geSecurityProviderName());
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Canoot create a KeyPairGenerator instance: ", e);
        } catch (NoSuchProviderException e) {
            Log.e(TAG, "Canoot create BouncyCastle provider: ", e);
        }

        // initialize the KeyPairGenerator
        try {
            keyGen.initialize(ecSpec, new SecureRandom());
        } catch (InvalidAlgorithmParameterException e) {
            Log.e(TAG, "Canoot initialize keygen: ", e);
        }
        this.keyPair = keyGen.generateKeyPair();

        // initialize the KeyFactory, to be able to pull out the public and private keys
        KeyFactory fact = null;
        try {
            fact = KeyFactory.getInstance(this.algorithmVendor.getKeyPairGeneratorAlgorithm(), this.algorithmVendor.geSecurityProviderName());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        // grab the keys
        try {
            this.publicKey = fact.generatePublic(new X509EncodedKeySpec(this.keyPair.getPublic().getEncoded()));
            this.privateKey = fact.generatePrivate(new PKCS8EncodedKeySpec(this.keyPair.getPrivate().getEncoded()));
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        /*
         * Create a Signature object and initialize it with the private key
         */
        Signature ecdsaSignature = null;
        try {
            ecdsaSignature = Signature.getInstance(this.algorithmVendor.getSigningAlgorithm());
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Canoot create a Signature instance: ", e);
        }

        try {
            ecdsaSignature.initSign(this.privateKey);
        } catch (InvalidKeyException e) {
            Log.e(TAG, "Canoot initialize the Signature, invalid key: ", e);
        }

        byte[] strByte = new byte[0];
        try {
            strByte = certificateInfo.toString().getBytes(UTF8_CHARSET);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "Canoot encode certificate info in utf-8: ", e);
        }

        try {
            ecdsaSignature.update(strByte);
        } catch (SignatureException e) {
            Log.e(TAG, "Canoot create signature with certificate info: ", e);
        }
        return ecdsaSignature;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public String getSigningAlgorithm() {
        return this.algorithmVendor.getSigningAlgorithm();
    }
}
