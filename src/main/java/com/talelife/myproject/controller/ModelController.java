package com.talelife.myproject.controller;

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
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.swagger.annotations.Api;

/**
 * 模型控制器
 * @author lwy
 *
 */
@Api(tags="流程模型接口")
@RestController
@RequestMapping("/model")
public class ModelController extends BaseController {
	
	@Autowired
    RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 创建模型
     * @param request
     * @return
     * @throws IOException 
     */
    @GetMapping(value = "/add_model")
    public void addModel(HttpServletRequest request, HttpServletResponse response) throws IOException{

        /*//初始化一个空模型
        Model model = repositoryService.newModel();
        //设置一些默认信息
        String name = "新流程";
        String description = "";
        int revision = 1;
        String key = "process";

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME,name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);

        response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + id);*/
        
        
        

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);
        Model modelData = repositoryService.newModel();

        ObjectNode modelObjectNode = objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, "hello1111");
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        String description = "hello1111";
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName("hello1111");
        modelData.setKey("12313123");

        //保存模型
        repositoryService.saveModel(modelData);
        repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));
        response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
    }

    /**
     * 模型列表
     * @return
     */
    @GetMapping(value = "/model_list")
    public List<Model> modelList(){
        List<Model> flowList = repositoryService.createModelQuery().list();
        return flowList;
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

    @GetMapping(value = "/deploy")
    public Object deploy(String id) throws Exception{
        //获取模型
        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
        if (null == bytes){
            return "模型数据为空，请先设计流程并成功保存，再进行发布。";
        }
        JsonNode modelNode = new ObjectMapper().readTree(bytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0){
            return "数据模型不符合要求，请至少设计一条主线程流。";
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);
        return "success";
    }


    @GetMapping(value = "/delete_deploy")
    public void deleteDeploy(@RequestParam(name = "id") String id){
        repositoryService.deleteDeployment(id);
    }

}
