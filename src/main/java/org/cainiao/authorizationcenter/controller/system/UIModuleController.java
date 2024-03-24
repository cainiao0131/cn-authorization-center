package org.cainiao.authorizationcenter.controller.system;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.cainiao.authorizationcenter.dao.repository.EnvironmentApplicationRepository;
import org.cainiao.authorizationcenter.dao.repository.UIModuleRepository;
import org.cainiao.authorizationcenter.dto.UiModuleUrl;
import org.cainiao.authorizationcenter.entity.acl.EnvironmentApplication;
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
    private final EnvironmentApplicationRepository environmentApplicationRepository;

    @GetMapping("{envAppId}/ui-module/{key}/url")
    @Operation(summary = "特定环境的特定应用的UI模块对应的前端URL")
    public UiModuleUrl uiModuleUrl(
        @Parameter(description = "UI模块所属的环境应用ID", required = true) @PathVariable("envAppId") long envAppId,
        @Parameter(description = "UI模块Key", required = true) @PathVariable("key") String key) {
        Optional<UIModule> uiModuleOptional = uiModuleRepository.findByEnvironmentApplicationIdAndKey(envAppId, key);
        if (uiModuleOptional.isEmpty()) {
            return null;
        }
        UIModule uiModule = uiModuleOptional.get();
        Optional<EnvironmentApplication> environmentApplicationOptional = environmentApplicationRepository
            .findById(uiModule.getEnvironmentApplicationId());
        if (environmentApplicationOptional.isEmpty()) {
            return null;
        }
        EnvironmentApplication environmentApplication = environmentApplicationOptional.get();
        return UiModuleUrl.builder().serviceName(fixString(environmentApplication.getServiceName()))
            .uri(String.format("%s%s", fixString(environmentApplication.getServiceUri()), fixString(uiModule.getUri())))
            .build();
    }

    private String fixString(String origin) {
        return StringUtils.hasText(origin) ? origin.trim() : "";
    }
}
