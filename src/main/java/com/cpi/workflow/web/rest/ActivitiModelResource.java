/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: ActivitiDeploymentResource
 * Author:   admin
 * Date:     2018/7/19 11:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.cpi.workflow.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.cpi.workflow.service.activiti.manage.ActivitiModelService;
import com.cpi.workflow.web.rest.util.HeaderUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author admin
 * @create 2018/7/19
 * @since 1.0.0
 */

@RestController
@RequestMapping("/api/model")
public class ActivitiModelResource {

    private final Logger log = LoggerFactory.getLogger(ActivitiModelResource.class);

    private static final String ENTITY_NAME = "ActivitiDeploymentResource";

    @Autowired
    private ActivitiModelService activitiModelService;

    @Autowired
    private RepositoryService repositoryService;

    @PostMapping("/create")
    @Timed
    public ResponseEntity<Model> createNewModel(@RequestParam(value = "name") String name,
                                                @RequestParam(value = "description") String description,
                                                @RequestParam(value = "key") String key,
                                                HttpServletRequest request) throws URISyntaxException {
        log.debug("REST request to create new model :name : {} description : {} key : {}", name, description, key);

        Model model = activitiModelService.createNewModel(name, description, key);
        ObjectNode editorNode = activitiModelService.createEditorNode();

        try {
            repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.created(new URI(request.getContextPath() + "/modeler.html?modelId=" + model.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, model.getId().toString()))
            .body(model);
    }

    @GetMapping("/edit/{modelId}")
    @Timed
    public void editModel(@PathVariable String modelId,
                          HttpServletRequest request,
                          HttpServletResponse response) { //throws URISyntaxException {
        log.debug("REST request to edit model :modelId : {} ", modelId);

        ObjectNode editorNode = activitiModelService.createEditorNode();

        Model model = repositoryService.getModel(modelId);
        try {
            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + model.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            repositoryService.addModelEditorSource(modelId, editorNode.toString().getBytes("utf-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        return ResponseEntity.created(new URI(request.getContextPath() + "/modeler.html?modelId=" + modelId))
//            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, modelId))
//            .body(model);
    }

    @DeleteMapping("/delete/{modelId}")
    @Timed
    public ResponseEntity<Void> deleteModel(@PathVariable String modelId) {
        log.debug("REST request to delete Model : {}", modelId);
        repositoryService.deleteModel(modelId);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, modelId.toString())).build();
    }

    @PostMapping("/deployment/{modelId}")
    public ResponseEntity deploy(@PathVariable("modelId")String modelId) throws Exception {

        //获取模型
        Model modelData = repositoryService.getModel(modelId);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());

        if (bytes.length > 0) {
            JsonNode modelNode = new ObjectMapper().readTree(bytes);

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);

            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            //发布流程
            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
            modelData.setDeploymentId(deployment.getId());
            repositoryService.saveModel(modelData);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeployAlert(ENTITY_NAME, modelId.toString())).build();
    }




}
