package com.mmj.data.core.extclient.dto;

public class AirportDto {

    private String code;
    private String displayName;
    private String title;
    private Integer locationId;
    private Integer marketId;
    private String validTo;
    private String validFrom;
    private Boolean mobileCheckin;
    private Boolean mobileBoardingPass;
    private Boolean webCheckin;
    private Boolean webBoardingPass;
    private Boolean kioskCheckin;
    private Boolean kioskBoardingPass;
    private Boolean airportCheckin;
    private Boolean airportBoardingPass;

    /**
     * No args constructor for use in serialization
     */
    public AirportDto() {
    }

    /**
     * @param marketId
     * @param mobileBoardingPass
     * @param locationId
     * @param webBoardingPass
     * @param code
     * @param validTo
     * @param title
     * @param airportCheckin
     * @param kioskCheckin
     * @param validFrom
     * @param airportBoardingPass
     * @param displayName
     * @param webCheckin
     * @param kioskBoardingPass
     * @param mobileCheckin
     */
    public AirportDto(String code, String displayName, String title, Integer locationId, Integer marketId, String validTo, String validFrom, Boolean mobileCheckin, Boolean mobileBoardingPass, Boolean webCheckin, Boolean webBoardingPass, Boolean kioskCheckin, Boolean kioskBoardingPass, Boolean airportCheckin, Boolean airportBoardingPass) {
        this.code = code;
        this.displayName = displayName;
        this.title = title;
        this.locationId = locationId;
        this.marketId = marketId;
        this.validTo = validTo;
        this.validFrom = validFrom;
        this.mobileCheckin = mobileCheckin;
        this.mobileBoardingPass = mobileBoardingPass;
        this.webCheckin = webCheckin;
        this.webBoardingPass = webBoardingPass;
        this.kioskCheckin = kioskCheckin;
        this.kioskBoardingPass = kioskBoardingPass;
        this.airportCheckin = airportCheckin;
        this.airportBoardingPass = airportBoardingPass;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getMarketId() {
        return marketId;
    }

    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public Boolean getMobileCheckin() {
        return mobileCheckin;
    }

    public void setMobileCheckin(Boolean mobileCheckin) {
        this.mobileCheckin = mobileCheckin;
    }

    public Boolean getMobileBoardingPass() {
        return mobileBoardingPass;
    }

    public void setMobileBoardingPass(Boolean mobileBoardingPass) {
        this.mobileBoardingPass = mobileBoardingPass;
    }

    public Boolean getWebCheckin() {
        return webCheckin;
    }

    public void setWebCheckin(Boolean webCheckin) {
        this.webCheckin = webCheckin;
    }

    public Boolean getWebBoardingPass() {
        return webBoardingPass;
    }

    public void setWebBoardingPass(Boolean webBoardingPass) {
        this.webBoardingPass = webBoardingPass;
    }

    public Boolean getKioskCheckin() {
        return kioskCheckin;
    }

    public void setKioskCheckin(Boolean kioskCheckin) {
        this.kioskCheckin = kioskCheckin;
    }

    public Boolean getKioskBoardingPass() {
        return kioskBoardingPass;
    }

    public void setKioskBoardingPass(Boolean kioskBoardingPass) {
        this.kioskBoardingPass = kioskBoardingPass;
    }

    public Boolean getAirportCheckin() {
        return airportCheckin;
    }

    public void setAirportCheckin(Boolean airportCheckin) {
        this.airportCheckin = airportCheckin;
    }

    public Boolean getAirportBoardingPass() {
        return airportBoardingPass;
    }

    public void setAirportBoardingPass(Boolean airportBoardingPass) {
        this.airportBoardingPass = airportBoardingPass;
    }
}
