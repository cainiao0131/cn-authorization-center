package org.cainiao.authorizationcenter.controller.system.lark;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.cainiao.acl.core.annotation.HasScope;
import org.cainiao.api.lark.api.LarkApi;
import org.cainiao.api.lark.api.docs.space.folder.dto.request.ListItemsInFolderRequest;
import org.cainiao.api.lark.dto.response.LarkDataResponse;
import org.cainiao.api.lark.dto.response.LarkFile;
import org.cainiao.api.lark.dto.response.LarkPage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        @Parameter(description = "飞书文件夹Token与分页信息", required = true)
        @RequestBody ListItemsInFolderRequest listItemsInFolderRequest) {

        return larkApi.docs().space().folder().listItemsInFolder(listItemsInFolderRequest).block();
    }
}
