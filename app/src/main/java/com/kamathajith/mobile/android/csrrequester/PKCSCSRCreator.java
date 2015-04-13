package com.kamathajith.mobile.android.csrrequester;

import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;

import java.io.IOException;
import java.security.KeyPair;
import java.security.Signature;

import javax.security.auth.x500.X500Principal;

/**
 * Created by kamathab on 3/16/15.
 */
public class PKCSCSRCreator implements CSRCreator {

    @Override
    public byte[] createCSR(CertificateInfo certificateInfo) {

        SignatureGenerator signatureGenerator = new SignatureGenerator(new ECDSAAlgorithmVendor());
        Signature signature = signatureGenerator.generateSignatureForCertificateInfo(certificateInfo);
        KeyPair pair = signatureGenerator.getKeyPair();

        StringBuilder x500PrincipalBuilder = new StringBuilder("CN=");
        x500PrincipalBuilder.append(certificateInfo.getCommonName());
        x500PrincipalBuilder.append(", ");
        x500PrincipalBuilder.append("L=");
        x500PrincipalBuilder.append(certificateInfo.getLocation());
        x500PrincipalBuilder.append(", ");
        x500PrincipalBuilder.append("ST=");
        x500PrincipalBuilder.append(certificateInfo.getState());
        x500PrincipalBuilder.append(", ");
        x500PrincipalBuilder.append("O=");
        x500PrincipalBuilder.append(certificateInfo.getOrganizationName());
        x500PrincipalBuilder.append(", ");
        x500PrincipalBuilder.append("OU=");
        x500PrincipalBuilder.append(certificateInfo.getOrganizationalUnit());
        x500PrincipalBuilder.append(", ");
        x500PrincipalBuilder.append("C=");
        x500PrincipalBuilder.append(certificateInfo.getCountry());

        X500Principal x500Principal = new X500Principal(x500PrincipalBuilder.toString());
        PKCS10CertificationRequestBuilder p10Builder = new JcaPKCS10CertificationRequestBuilder(
                x500Principal, pair.getPublic());

        JcaContentSignerBuilder csBuilder = new JcaContentSignerBuilder(signatureGenerator.getSigningAlgorithm());
        ContentSigner signer = null;
        try {
            signer = csBuilder.build(pair.getPrivate());
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        }
        org.bouncycastle.pkcs.PKCS10CertificationRequest csr = p10Builder.build(signer);
        if (csr != null) {
            try {
                return csr.getEncoded();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
