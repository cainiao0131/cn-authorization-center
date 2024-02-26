package org.cainiao.authorizationcenter.dto;

import lombok.Builder;
import lombok.Data;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Data
@Builder
public class UiModuleUrl {
    private String serviceName;
    private String uri;
}
