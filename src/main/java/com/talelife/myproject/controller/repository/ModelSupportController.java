package com.talelife.myproject.controller.repository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.rest.service.api.repository.ModelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.talelife.myproject.controller.BaseController;
import com.talelife.util.Result;

import io.swagger.annotations.Api;

/**
 * 模型控制器
 * @author lwy
 *
 */
@Api(tags="流程模型接口")
@RestController
@RequestMapping("/repository/model-support")
public class ModelSupportController extends BaseController {
	
	@Autowired
	private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 创建模型
     * @param request
     * @return
     * @throws IOException 
     */
    @PostMapping(value = "/model")
    public Result<Object> addModel(@RequestBody ModelRequest modelRequest) throws IOException{

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        Model modelData = repositoryService.newModel();

        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, modelRequest.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        //modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(modelRequest.getName());
        //modelData.setKey(modelEntity.getKey());

        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        return Result.success();
    }

    
    @GetMapping(value = "/deploy_list")
    public List<Deployment> deployList(){
        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
        return deployments;
    }


    @GetMapping(value = "/delete_model")
    public void deleteModel(String id){
        repositoryService.deleteModel(id);
    }

    @GetMapping(value = "/deploy_model")
    public Result<Object> deployModel(String id) throws Exception{

        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
        if (null == bytes){
            return Result.fail("500", "模型数据为空，请先设计流程并成功保存，再进行发布。");
        }
        JsonNode modelNode = new ObjectMapper().readTree(bytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0){
            return Result.fail("500", "数据模型不符合要求，请至少设计一条主线程流。");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);
        return Result.success();
    }


    @GetMapping(value = "/delete_deploy")
    public void deleteDeploy(@RequestParam(name = "id") String id){
        repositoryService.deleteDeployment(id);
    }

}
