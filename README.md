# CSRRequester
Android app to generate a CSR - Uses the ECDSA algorithm on a P256 curve

/** *

This is a sample application that does the following
1. Generates an ECDSA signed certificate on a p256 curve, creates a certificate signing request and
sends the .csr file via a share utility (email, Bluetooth, etc) *
Supports Android JellyBean and up (API level 16 and up)
Tested on a Samsung Galaxy Nexus phone (Should work on a tablet, but not tested) *
Developed using Android Studio AI-140.1782451 *
Author: Ajith Kamath * */
/**

General description of the project *
Core of the project:
1. Create a CertificateInfo object
This usually involves info on your company, location and app
2. Generate a Signature
Implement the AlgorithmVendor interface for ECDSA,
Use the ECDSAAlgorithmVendor and sign the CertificateInfo
3. Create a CSR
Implement the CSRVendor interface for PKCS10
(The PKCS#10 standard defines a binary format for encoding CSRs for use with X.509. It is expressed in ASN.1)
4. Share/send the data to the CSR endpoint by generating an Android sharing intent
(Via an IntentChooser)
Alternatively, we could have also used the ShareActionProvider from the ActionBar *
Note: The private key is not persisted onto a keystore as in a real life scenario.
This is obviously just a test project and does not include that feature * */
/**

The design incorporates a tabbed UI pattern, each tab loading a separate fragment *
Tab 1: Where we do all the CSR work
Tab 2: Loads a wikipedia page that explains about CSR
Tab 3: A YouTube video of a Google Talk on Encryption infrastructure *
The setting screen just has a short description of the objective of the project */
