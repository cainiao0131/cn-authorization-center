package org.cainiao.authorizationcenter.entity.acl.technology;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.common.entity.IdBaseEntity;

import java.io.Serial;

/**
 * 表示哪些用户正在使用这个系统<br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Entity(name = "t_system_user")
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemUser extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -4017908273219433506L;

    /**
     * 系统ID
     */
    @Column(name = "syu_system_id", nullable = false)
    private long systemId;

    /**
     * 平台用户ID
     */
    @Column(name = "syu_user_id", nullable = false)
    private long userId;

    /**
     * 用户在这个系统中的唯一标识<br />
     * 系统内的资源应该以这个 ID 来标识资源由哪个用户所有，而不是用平台用户ID<br />
     * 已达到用户ID在系统间隔离的目的（类似飞书的open_id，只是飞书的open_id时应用级别的，而【系统用户 DI】时系统级别的）
     */
    @Column(name = "syu_system_user_id", nullable = false)
    private String systemUserId;
}
