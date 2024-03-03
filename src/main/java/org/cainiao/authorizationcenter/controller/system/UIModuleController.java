package org.cainiao.authorizationcenter.controller.system;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.repository.ApplicationRepository;
import org.cainiao.authorizationcenter.dao.repository.UIModuleRepository;
import org.cainiao.authorizationcenter.dto.UiModuleUrl;
import org.cainiao.authorizationcenter.entity.acl.Application;
import org.cainiao.authorizationcenter.entity.acl.UIModule;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RestController
@RequestMapping("system")
@RequiredArgsConstructor
@Tag(name = "UIModule", description = "UI模块")
public class UIModuleController {

    private final UIModuleRepository uiModuleRepository;
    private final ApplicationRepository applicationRepository;

    @GetMapping("ui-module/{id}/url")
    @Operation(summary = "文章管理分页查询文章")
    public UiModuleUrl uiModuleUrl(
        @Parameter(description = "UI模块ID", required = true) @PathVariable("id") String id)
    {
        Optional<UIModule> uiModuleOptional = uiModuleRepository.findById(id);
        if (uiModuleOptional.isEmpty()) {
            return null;
        }
        UIModule uiModule = uiModuleOptional.get();
        String applicationId = uiModule.getApplicationId();
        if (!StringUtils.hasText(applicationId)) {
            return null;
        }
        Optional<Application> applicationOptional = applicationRepository.findById(applicationId);
        if (applicationOptional.isEmpty()) {
            return null;
        }
        Application application = applicationOptional.get();
        return UiModuleUrl.builder().serviceName(fixString(application.getServiceName()))
            .uri(String.format("%s%s", fixString(application.getServiceUri()), fixString(uiModule.getUri())))
            .build();
    }

    private String fixString(String origin) {
        return StringUtils.hasText(origin) ? origin.trim() : "";
    }
}
