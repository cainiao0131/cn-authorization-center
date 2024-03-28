package org.cainiao.authorizationcenter.controller.system.lark;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.cainiao.acl.core.annotation.HasScope;
import org.cainiao.api.lark.dto.request.docs.space.folder.ListItemsInFolderRequest;
import org.cainiao.api.lark.dto.response.LarkDataResponse;
import org.cainiao.api.lark.dto.response.LarkFile;
import org.cainiao.api.lark.dto.response.LarkPage;
import org.cainiao.api.lark.imperative.LarkApi;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RestController
@RequestMapping("system/lark")
@RequiredArgsConstructor
@Tag(name = "Lark", description = "飞书API")
public class LarkController {

    private final LarkApi larkApi;

    @GetMapping("/drive/v1/files")
    @Operation(summary = "查询飞书文件夹下的列表")
    @HasScope({"lark"})
    public ResponseEntity<LarkDataResponse<LarkPage<LarkFile>>> listItemsInFolder(
        @Parameter(description = "文件夹Token") @RequestParam(value = "folder_token") String folderToken,
        @Parameter(description = "用户 ID 类型") @RequestParam(value = "user_id_type", required = false, defaultValue = "open_id") String userIdType,
        @Parameter(description = "排序规则") @RequestParam(value = "order_by", required = false, defaultValue = "EditedTime") String orderBy,
        @Parameter(description = "升序降序") @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
        @Parameter(description = "分页大小") @RequestParam(value = "page_size", required = false) Integer pageSize,
        @Parameter(description = "分页标记，第一次请求不填，表示从头开始遍历") @RequestParam(value = "page_token", required = false) String pageToken) {

        ResponseEntity<LarkDataResponse<LarkPage<LarkFile>>> larkResponse = larkApi.docs().space().folder().listItemsInFolder(ListItemsInFolderRequest.builder()
            .folderToken(folderToken)
            .userIdType(userIdType)
            .orderBy(orderBy)
            .direction(direction)
            .pageSize(pageSize)
            .pageToken(pageToken)
            .build());

        /*
         * 请求飞书 API 的响应体长度，与授权中心响应 OAuth2 客户端的响应体长度是不同的
         * 飞书的响应已经被转换为了实体对象，这个转换过程中长度是会变化的，例如字段由下划线变为了驼峰
         * 因此不能直接将飞书 API 的响应 ResponseEntity 直接作为 Controller 方法的返回值
         * 需要移除 ContentLength，让 Spring MVC 根据响应体长度自动设置（见：AbstractMessageConverterMethodProcessor.writeWithMessageConverters())
         * 不能直接修改 HttpHeaders，因为其内部是不可变的 MultiValueMap
         */
        HttpHeaders newHeaders = new HttpHeaders();
        for (Map.Entry<String, List<String>> entry : larkResponse.getHeaders().entrySet()) {
            String headerName = entry.getKey();
            if (!HttpHeaders.CONTENT_LENGTH.equals(headerName)) {
                newHeaders.put(headerName, entry.getValue());
            }
        }

        return ResponseEntity
            .status(larkResponse.getStatusCode())
            .headers(newHeaders)
            .body(larkResponse.getBody());
    }
}
