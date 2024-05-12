package me.t3sl4.gelkurye.Util.Util.Req;

import org.json.JSONObject;

public class HTTP {
    private String reqURL;
    private String reqType;
    private String reqHeader;
    private JSONObject reqBody;

    public HTTP(String reqURL, String reqType, String reqHeader, JSONObject reqBody) {
        this.reqURL = reqURL;
        this.reqType = reqType;
        this.reqHeader = reqHeader;
        this.reqBody = reqBody;
    }

    public String getReqURL() {
        return reqURL;
    }

    public void setReqURL(String reqURL) {
        this.reqURL = reqURL;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getReqHeader() {
        return reqHeader;
    }

    public void setReqHeader(String reqHeader) {
        this.reqHeader = reqHeader;
    }

    public JSONObject getReqBody() {
        return reqBody;
    }

    public void setReqBody(JSONObject reqBody) {
        this.reqBody = reqBody;
    }
}
