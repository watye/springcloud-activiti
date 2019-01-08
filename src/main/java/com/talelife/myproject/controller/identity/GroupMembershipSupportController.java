package com.talelife.myproject.controller.identity;

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
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.rest.exception.ActivitiConflictException;
import org.activiti.rest.service.api.identity.MembershipRequest;
import org.activiti.rest.service.api.repository.ModelRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@Api(tags="流程组用户关系接口")
@RestController
@RequestMapping("/identity/groups-support")
public class GroupMembershipSupportController extends BaseController {
	
	@Autowired
	private IdentityService identityService;
    
    @RequestMapping(value="/{groupId}/members", method = RequestMethod.POST)
    public Result<?> createMemberships(@PathVariable String groupId, @RequestBody List<MembershipRequest> memberShips){
    	Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group == null) {
          throw new ActivitiObjectNotFoundException("Could not find a group with id '" + groupId + "'.", User.class);
        }
        for (MembershipRequest memberShip : memberShips) {
        	 if (identityService.createUserQuery()
		        .memberOfGroup(group.getId())
		        .userId(memberShip.getUserId())
		        .count() == 0) {
		        identityService.createMembership(memberShip.getUserId(), group.getId());
		    }
		}
        return Result.success();
    }

    @RequestMapping(value="/{groupId}/members", method = RequestMethod.DELETE)
    public Result<?> deleteMemberships(@PathVariable String groupId, @RequestBody List<MembershipRequest> memberShips){
    	Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
        if (group == null) {
          throw new ActivitiObjectNotFoundException("Could not find a group with id '" + groupId + "'.", User.class);
        }
        for (MembershipRequest memberShip : memberShips) {
        	if (identityService.createUserQuery()
    	        .memberOfGroup(group.getId())
    	        .userId(memberShip.getUserId())
    	        .count() > 0) {
        		identityService.deleteMembership(memberShip.getUserId(), group.getId());
    	    }
		}
        return Result.success();
    }

}
