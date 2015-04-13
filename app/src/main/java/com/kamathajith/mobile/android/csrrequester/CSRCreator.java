package com.kamathajith.mobile.android.csrrequester;

/**
 * Created by kamathab on 3/16/15.
 */
public interface CSRCreator {
    byte[] createCSR(CertificateInfo certificateInfo);
}
