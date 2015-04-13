package com.kamathajith.mobile.android.csrrequester;

/**
 * Created by Ajith Kamath on 3/16/15.
 */
public interface AlgorithmVendor {
    public String getKeyPairGeneratorAlgorithm();
    public String getSecureRandomAlgorithm();
    public String getSigningAlgorithm();
    public String geSecurityProviderName();
}
