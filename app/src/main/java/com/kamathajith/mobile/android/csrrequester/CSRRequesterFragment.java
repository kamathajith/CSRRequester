package com.kamathajith.mobile.android.csrrequester;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.util.io.pem.PemObject;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by Ajith Kamath on 3/16/15.
 *
 * This is the meat and potatoes of the project
 *
 * 1. Create a CertificateInfo object
 *      This usually involves info on your company, location and app
 * 2. Generate a Signature
 *      Implement the AlgorithmVendor interface for ECDSA,
 *      Use the ECDSAAlgorithmVendor and sign the CertificateInfo
 * 3. Create a CSR
 *      Implement the CSRVendor interface for PKCS10
 *      (The PKCS#10 standard defines a binary format for encoding CSRs for use with X.509. It is expressed in ASN.1)
 * 4. Share/send the data to the CSR endpoint by generating an Android sharing intent
 *      (Via an IntentChooser)
 *      Alternatively, we could have also used the ShareActionProvider from the ActionBar
 */
public class CSRRequesterFragment extends Fragment {
    private static final String TAG = CSRRequesterFragment.class.getName();

    /**
     * The constants we'll use for our test project to create our CertificationInfo
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String COMMON_NAME = "kamathajith.android.com";
    private static final String ORG_UNIT = "Kamath-Org";
    private static final String ORG_NAME = "Kamath";
    private static final String CERT_REQUESTER_LOCATION = "Ashburn";
    private static final String CERT_REQUESTER_STATE = "GA";
    private static final String CERT_REQUESTER_COUNTRY = "US";


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CSRRequesterFragment newInstance(int sectionNumber) {
        CSRRequesterFragment fragment = new CSRRequesterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public CSRRequesterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_csr_requester, container, false);

        final TextView tvMessage = (TextView) rootView.findViewById(R.id.tvMessage);
        if (tvMessage != null) {
            tvMessage.setText(R.string.press_button_to_start_process);
        }

        Button generateCSRButton = (Button) rootView.findViewById(R.id.btnCSRRequest);
        if (generateCSRButton != null) {
            generateCSRButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvMessage.setText(R.string.csr_generation_in_process);
                    String csrString = CSRRequesterFragment.this.createCSRAndReturnAsString();
                    Log.d(TAG, "CSR: "+ csrString);

                    if (csrString != null) {
                        tvMessage.setText(R.string.share_csr);
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, csrString);
                        sendIntent.setType(getActivity().getResources().getString(R.string.content_type_text_plain));
                        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_csr_to_authority)));
                    }

                }
            });
        }

        return rootView;
    }

    /**
     *
     * @return
     */
    private String createCSRAndReturnAsString() {
        PKCSCSRCreator csr = new PKCSCSRCreator();
            CertificateInfo certificateInfo = new CertificateInfo(COMMON_NAME,
                ORG_UNIT, ORG_NAME, CERT_REQUESTER_LOCATION, CERT_REQUESTER_STATE, CERT_REQUESTER_COUNTRY);

        /*
         * Return the signed certificate as PEM-encoded bytes
        */
        byte[] csrBytes = csr.createCSR(certificateInfo);
        PemObject pemObject = new PemObject("CERTIFICATE REQUEST", csrBytes);
        StringWriter str = new StringWriter();
        PEMWriter pemWriter = new PEMWriter(str);
        try {
            pemWriter.writeObject(pemObject);
            pemWriter.close();
            str.close();
            return str.toString();
        } catch (IOException e) {
            Log.e(TAG, "Cannot create a string out of the CSR raw bytes", e);
        }
        return null;
    }

}
