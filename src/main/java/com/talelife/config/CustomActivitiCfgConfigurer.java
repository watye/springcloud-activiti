package com.talelife.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomActivitiCfgConfigurer implements ProcessEngineConfigurationConfigurer {
	private static String PROCESS_DIAGRAM_FONT = "宋体";
	@Override
	public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
		processEngineConfiguration.setLabelFontName(PROCESS_DIAGRAM_FONT);
		processEngineConfiguration.setActivityFontName(PROCESS_DIAGRAM_FONT);
		processEngineConfiguration.setAnnotationFontName(PROCESS_DIAGRAM_FONT);
	}

}
