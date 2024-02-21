package org.cainiao.authorization.controller.cas;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "CAS", description = "中央认证服务")
@RequestMapping("cas")
public class CasController {

    @GetMapping("test")
    @Operation(summary = "测试")
    public String test() {
        return "test";
    }

}
