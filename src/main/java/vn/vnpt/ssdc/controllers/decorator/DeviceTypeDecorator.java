package vn.vnpt.ssdc.controllers.decorator;

import vn.vnpt.ssdc.api.model.DeviceType;

/**
 * Created by vietnq on 11/3/16.
 */
public class DeviceTypeDecorator {
    private String manufacturer;
    private String oui;
    private String productClass;
    private String firmwareVersion;

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getOui() {
        return oui;
    }

    public void setOui(String oui) {
        this.oui = oui;
    }

    public String getProductClass() {
        return productClass;
    }

    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public DeviceType toDeviceType() {
        DeviceType deviceType = new DeviceType();
        deviceType.manufacturer = this.manufacturer;
        deviceType.oui = this.oui;
        deviceType.productClass = this.productClass;
        deviceType.firmwareVersion = this.firmwareVersion;
        return deviceType;
    }
}
