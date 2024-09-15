package org.cainiao.authorizationcenter.controller.tenant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.cainiao.api.lark.dto.request.docs.apireference.ObtainAllBlocksOfDocumentRequest;
import org.cainiao.api.lark.dto.request.docs.space.folder.ListItemsInFolderRequest;
import org.cainiao.api.lark.dto.response.LarkDataResponse;
import org.cainiao.api.lark.dto.response.docs.docs.apireference.document.LarkBlockPage;
import org.cainiao.api.lark.dto.response.docs.space.folder.LarkFilePage;
import org.cainiao.api.lark.dto.response.docs.space.folder.LarkFolderMeta;
import org.cainiao.api.lark.imperative.LarkApiWithAccessToken;
import org.springframework.web.bind.annotation.*;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RestController
@RequestMapping("tenant/lark")
@RequiredArgsConstructor
@Tag(name = "Lark", description = "飞书API")
public class LarkController {

    private final LarkApiWithAccessToken larkApiWithAccessToken;

    @GetMapping("/drive/v1/files")
    @Operation(summary = "获取用户云空间中指定文件夹下的文件清单")
    public LarkDataResponse<LarkFilePage> listItemsInFolder(
        @Parameter(description = "文件夹Token") @RequestParam(value = "folder_token") String folderToken,
        @Parameter(description = "用户 ID 类型") @RequestParam(value = "user_id_type", required = false) String userIdType,
        @Parameter(description = "排序规则") @RequestParam(value = "order_by", required = false) String orderBy,
        @Parameter(description = "升序降序") @RequestParam(value = "direction", required = false) String direction,
        @Parameter(description = "分页大小") @RequestParam(value = "page_size", required = false) Integer pageSize,
        @Parameter(description = "分页标记，第一次请求不填，表示从头开始遍历") @RequestParam(value = "page_token", required = false) String pageToken) {

        return larkApiWithAccessToken.docs().space().folder()
            .listItemsInFolder(ListItemsInFolderRequest.builder()
                .folderToken(folderToken)
                .userIdType(userIdType)
                .orderBy(orderBy)
                .direction(direction)
                .pageSize(pageSize)
                .pageToken(pageToken)
                .build()).getBody();
    }

    @GetMapping("/drive/explorer/v2/folder/{folderToken}/meta")
    @Operation(summary = "根据 folderToken 获取该文件夹的元信息")
    public LarkDataResponse<LarkFolderMeta> getFolderMeta(
        @Parameter(description = "文件夹Token") @PathVariable("folderToken") String folderToken) {

        return larkApiWithAccessToken.docs().space().folder().getFolderMeta(folderToken).getBody();
    }

    @GetMapping("/docx/v1/documents/{documentId}/blocks")
    @Operation(summary = "获取文档所有块的富文本内容并分页返回")
    public LarkDataResponse<LarkBlockPage> obtainAllBlocksOfDocument(
        @Parameter(description = "文档的唯一标识") @PathVariable("documentId") String documentId,
        @Parameter(description = "查询的文档版本，-1 表示文档最新版本") @RequestParam(value = "document_revision_id", required = false) Integer documentRevisionId,
        @Parameter(description = "用户 ID 类型") @RequestParam(value = "user_id_type", required = false) String userIdType,
        @Parameter(description = "分页大小") @RequestParam(value = "page_size", required = false) Integer pageSize,
        @Parameter(description = "分页标记，第一次请求不填，表示从头开始遍历") @RequestParam(value = "page_token", required = false) String pageToken) {

        return larkApiWithAccessToken.docs().docsApi().apiReference().document()
            .obtainAllBlocksOfDocument(documentId, ObtainAllBlocksOfDocumentRequest.builder()
                .documentRevisionId(documentRevisionId)
                .userIdType(userIdType)
                .pageSize(pageSize)
                .pageToken(pageToken)
                .build()).getBody();
    }
}
