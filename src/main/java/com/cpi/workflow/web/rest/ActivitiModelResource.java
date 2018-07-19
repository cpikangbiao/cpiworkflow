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
import com.cpi.workflow.service.activiti.ActivitiDeploymentService;
import com.cpi.workflow.service.activiti.ActivitiModelService;
import com.cpi.workflow.service.dto.ActivitiWorkflowFileDTO;
import com.cpi.workflow.web.rest.errors.BadRequestAlertException;
import com.cpi.workflow.web.rest.util.HeaderUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
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
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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
                                                @RequestParam(value = "key") String key) throws URISyntaxException {
        log.debug("REST request to create new model :name : {} description : {} key : {}", name, description, key);

        Model model = activitiModelService.createNewModel(name, description, key);
        ObjectNode editorNode = activitiModelService.createEditorNode();

        try {
            repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return ResponseEntity.created(new URI("/modeler.html?modelId=" + model.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, model.getId().toString()))
            .body(model);
    }

//    @RequestMapping("/create")
//    public void create(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            String description = "descriptiontest";
//            String name = "nametest";
//            String key  = "keytest";
//
//            Model model = activitiModelService.createNewModel(name, description, key);
//            ObjectNode editorNode = activitiModelService.createEditorNode();
//
//            ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
//            RepositoryService repositoryService = processEngine.getRepositoryService();
//
//            repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
//            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + model.getId());
//
//        } catch (Exception e) {
//            System.out.println("创建模型失败：");
//        }
//    }


    /**
     * 发布模型为流程定义
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("{id}/deployment")
    public ResponseEntity deploy(@PathVariable("id")String id) throws Exception {

        //获取模型
        Model modelData = repositoryService.getModel(id);
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
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeployAlert(ENTITY_NAME, id.toString())).build();
    }




}
