package com.example.upc.controller.searchParam;

public class HIKSearchParam {
    String version;
    String cameraIndexCode;
    Integer streamType;
    String protocol;
    Integer transmode;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCameraIndexCode() {
        return cameraIndexCode;
    }

    public void setCameraIndexCode(String cameraIndexCode) {
        this.cameraIndexCode = cameraIndexCode;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getStreamType() {
        return streamType;
    }

    public void setStreamType(Integer streamType) {
        this.streamType = streamType;
    }

    public Integer getTransmode() {
        return transmode;
    }

    public void setTransmode(Integer transmode) {
        this.transmode = transmode;
    }
}
