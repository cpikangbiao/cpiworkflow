package com.cpi.workflow.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import io.github.jhipster.web.util.HeaderUtil;
/**
 * Utility class for HTTP headers creation.
 * @author admin
 */
public final class CpiHeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    private static final String APPLICATION_NAME = "cpiworkflowApp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    public static HttpHeaders createAlert(String applicationName, String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-" + applicationName + "-alert", message);
        headers.add("X-" + applicationName + "-params", param);
        return headers;
    }

    public static HttpHeaders createEntityDeployAlert(String applicationName, boolean enableTranslation, String entityName, String param) {
        String message = enableTranslation ? applicationName + "." + entityName + ".deployed" : "A new " + entityName + " is deployed with identifier " + param;
        return createAlert(applicationName, message, param);
    }
}
