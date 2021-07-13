package com.example.demo.DTO;

import java.util.List;

public class SigningKeyMetadata {
    private String token_endpoint;
    private List<String> token_endpoint_auth_methods_supported;
    private String jwks_uri;
    private List<String> response_modes_supported;
    private List<String> subject_types_supported;
    private List<String> id_token_signing_alg_values_supported;
    private List<String> response_types_supported;
    private List<String> scopes_supported;
    private String issuer;
    private boolean microsoft_multi_refresh_token;
    private String authorization_endpoint;
    private String device_authorization_endpoint;
    private boolean http_logout_supported;
    private boolean frontchannel_logout_supported;
    private String end_session_endpoint;
    private List<String> claims_supported;
    private String check_session_iframe;
    private String userinfo_endpoint;
    private String tenant_region_scope;
    private String cloud_instance_name;
    private String cloud_graph_host_name;
    private String msgraph_host;
    private String rbac_url;

    public String getToken_endpoint() {
        return token_endpoint;
    }

    public void setToken_endpoint(String token_endpoint) {
        this.token_endpoint = token_endpoint;
    }

    public List<String> getToken_endpoint_auth_methods_supported() {
        return token_endpoint_auth_methods_supported;
    }

    public void setToken_endpoint_auth_methods_supported(List<String> token_endpoint_auth_methods_supported) {
        this.token_endpoint_auth_methods_supported = token_endpoint_auth_methods_supported;
    }

    public String getJwks_uri() {
        return jwks_uri;
    }

    public void setJwks_uri(String jwks_uri) {
        this.jwks_uri = jwks_uri;
    }

    public List<String> getResponse_modes_supported() {
        return response_modes_supported;
    }

    public void setResponse_modes_supported(List<String> response_modes_supported) {
        this.response_modes_supported = response_modes_supported;
    }

    public List<String> getSubject_types_supported() {
        return subject_types_supported;
    }

    public void setSubject_types_supported(List<String> subject_types_supported) {
        this.subject_types_supported = subject_types_supported;
    }

    public List<String> getId_token_signing_alg_values_supported() {
        return id_token_signing_alg_values_supported;
    }

    public void setId_token_signing_alg_values_supported(List<String> id_token_signing_alg_values_supported) {
        this.id_token_signing_alg_values_supported = id_token_signing_alg_values_supported;
    }

    public List<String> getResponse_types_supported() {
        return response_types_supported;
    }

    public void setResponse_types_supported(List<String> response_types_supported) {
        this.response_types_supported = response_types_supported;
    }

    public List<String> getScopes_supported() {
        return scopes_supported;
    }

    public void setScopes_supported(List<String> scopes_supported) {
        this.scopes_supported = scopes_supported;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public boolean isMicrosoft_multi_refresh_token() {
        return microsoft_multi_refresh_token;
    }

    public void setMicrosoft_multi_refresh_token(boolean microsoft_multi_refresh_token) {
        this.microsoft_multi_refresh_token = microsoft_multi_refresh_token;
    }

    public String getAuthorization_endpoint() {
        return authorization_endpoint;
    }

    public void setAuthorization_endpoint(String authorization_endpoint) {
        this.authorization_endpoint = authorization_endpoint;
    }

    public String getDevice_authorization_endpoint() {
        return device_authorization_endpoint;
    }

    public void setDevice_authorization_endpoint(String device_authorization_endpoint) {
        this.device_authorization_endpoint = device_authorization_endpoint;
    }

    public boolean isHttp_logout_supported() {
        return http_logout_supported;
    }

    public void setHttp_logout_supported(boolean http_logout_supported) {
        this.http_logout_supported = http_logout_supported;
    }

    public boolean isFrontchannel_logout_supported() {
        return frontchannel_logout_supported;
    }

    public void setFrontchannel_logout_supported(boolean frontchannel_logout_supported) {
        this.frontchannel_logout_supported = frontchannel_logout_supported;
    }

    public String getEnd_session_endpoint() {
        return end_session_endpoint;
    }

    public void setEnd_session_endpoint(String end_session_endpoint) {
        this.end_session_endpoint = end_session_endpoint;
    }

    public List<String> getClaims_supported() {
        return claims_supported;
    }

    public void setClaims_supported(List<String> claims_supported) {
        this.claims_supported = claims_supported;
    }

    public String getCheck_session_iframe() {
        return check_session_iframe;
    }

    public void setCheck_session_iframe(String check_session_iframe) {
        this.check_session_iframe = check_session_iframe;
    }

    public String getUserinfo_endpoint() {
        return userinfo_endpoint;
    }

    public void setUserinfo_endpoint(String userinfo_endpoint) {
        this.userinfo_endpoint = userinfo_endpoint;
    }

    public String getTenant_region_scope() {
        return tenant_region_scope;
    }

    public void setTenant_region_scope(String tenant_region_scope) {
        this.tenant_region_scope = tenant_region_scope;
    }

    public String getCloud_instance_name() {
        return cloud_instance_name;
    }

    public void setCloud_instance_name(String cloud_instance_name) {
        this.cloud_instance_name = cloud_instance_name;
    }

    public String getCloud_graph_host_name() {
        return cloud_graph_host_name;
    }

    public void setCloud_graph_host_name(String cloud_graph_host_name) {
        this.cloud_graph_host_name = cloud_graph_host_name;
    }

    public String getMsgraph_host() {
        return msgraph_host;
    }

    public void setMsgraph_host(String msgraph_host) {
        this.msgraph_host = msgraph_host;
    }

    public String getRbac_url() {
        return rbac_url;
    }

    public void setRbac_url(String rbac_url) {
        this.rbac_url = rbac_url;
    }
}
