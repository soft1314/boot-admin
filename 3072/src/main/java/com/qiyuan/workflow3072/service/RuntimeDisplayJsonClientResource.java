package com.qiyuan.workflow3072.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.bind.annotation.PathVariable;

public interface RuntimeDisplayJsonClientResource {
    JsonNode getModelJSON(@PathVariable String processInstanceId) throws Exception;
}
