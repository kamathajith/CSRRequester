package com.kamathajith.mobile.android.csrrequester;

/**
 * Created by kamathab on 3/16/15.
 */
/**
 *
 * @param commonName
 *            Common Name, X.509 lingo for Organization
 * @param organizationalUnit
 *            Organizational unit
 * @param organizationName
 *            Organization NAME
 * @param location
 *            Location
 * @param state
 *            State
 * @param country
 *            Country
 * @return
 * @throws Exception
 */
public class CertificateInfo {
    private String commonName; // Common Name for the Organization,
    private String organizationalUnit; // Organizational unit
    private String organizationName; // Organization name
    private String location; // location
    private String state; // state
    private String country; // country

    public CertificateInfo() {

    }

    /**
     *
     * @param commonName
     * @param organizationalUnit
     * @param organizationName
     * @param location
     * @param state
     * @param country
     */
    public CertificateInfo(String commonName,
                           String organizationalUnit,
                           String organizationName,
                           String location,
                           String state,
                           String country) {
        this.commonName = commonName;
        this.organizationalUnit = organizationalUnit;
        this.organizationName = organizationName;
        this.location = location;
        this.state = state;
        this.country = country;
    }

    /**
     *
     * @return
     */
    public String getCommonName() {
        return commonName;
    }

    /**
     *
     * @param commonName
     */
    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    /**
     *
     * @return
     */
    public String getOrganizationalUnit() {
        return organizationalUnit;
    }

    /**
     *
     * @param organizationalUnit
     */
    public void setOrganizationalUnit(String organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    /**
     *
     * @return
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     *
     * @param organizationName
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     *
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
