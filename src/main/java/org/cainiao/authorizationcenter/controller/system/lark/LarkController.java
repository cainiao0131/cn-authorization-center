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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public LarkDataResponse<LarkPage<LarkFile>> listItemsInFolder(
        @Parameter(description = "文件夹Token") @RequestParam(value = "folder_token") String folderToken,
        @Parameter(description = "用户 ID 类型") @RequestParam(value = "user_id_type", required = false, defaultValue = "open_id") String userIdType,
        @Parameter(description = "排序规则") @RequestParam(value = "order_by", required = false, defaultValue = "EditedTime") String orderBy,
        @Parameter(description = "升序降序") @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
        @Parameter(description = "分页大小") @RequestParam(value = "page_size", required = false) Integer pageSize,
        @Parameter(description = "分页标记，第一次请求不填，表示从头开始遍历") @RequestParam(value = "page_token", required = false) String pageToken) {

        return larkApi.docs().space().folder()
            .listItemsInFolder(ListItemsInFolderRequest.builder()
                .folderToken(folderToken)
                .userIdType(userIdType)
                .orderBy(orderBy)
                .direction(direction)
                .pageSize(pageSize)
                .pageToken(pageToken)
                .build()).getBody();
    }
}