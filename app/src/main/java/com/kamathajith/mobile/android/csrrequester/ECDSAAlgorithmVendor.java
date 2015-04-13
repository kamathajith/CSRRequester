package com.kamathajith.mobile.android.csrrequester;

/**
 * Created by Ajith Kamath on 3/16/15.
 */
public class ECDSAAlgorithmVendor implements AlgorithmVendor {
    public static final String KEY_PAIR_GENERATOR_ALGORITHM = "EC";
    private static final String SECURE_RANDOM_ALGORITHM = "SHA256PRNG";
    private static final String SIGNING_ALGORITHM = "SHA256withECDSA";
    private static final String SECURITY_PROVIDER_BOUNCY_CASTLE = "BC";

    @Override
    public String getKeyPairGeneratorAlgorithm() {
        return KEY_PAIR_GENERATOR_ALGORITHM;
    }

    @Override
    public String getSecureRandomAlgorithm() {
        return SECURE_RANDOM_ALGORITHM;
    }

    @Override
    public String getSigningAlgorithm() {
        return SIGNING_ALGORITHM;
    }

    @Override
    public String geSecurityProviderName() {
        return SECURITY_PROVIDER_BOUNCY_CASTLE;
    }
}
