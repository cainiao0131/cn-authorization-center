package org.cainiao.authorizationcenter.entity.acl.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cainiao.common.entity.IdBaseEntity;

import java.io.Serial;

/**
 * <br />
 * <p>
 * Author: Cai Niao(wdhlzd@163.com)<br />
 */
@Entity(name = "t_lark_user")
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class LarkUser extends IdBaseEntity {

    @Serial
    private static final long serialVersionUID = -9157104353548130250L;

    /**
     * 与这个飞书用户关联的平台用户ID
     */
    @Column(name = "lau_user_id", nullable = false)
    private long userId;

    /**
     * 飞书的 User ID<br />
     * 是用户在租户内的身份标识，用于与租户对应的企业的用户标识进行兼容<br />
     * 例如某企业内的通用用户标识是工号，则可将飞书的 User ID 设置为编号<br />
     * 同一个用户在不同租户下 User ID 不同，可以在员工入职时人工设置<br />
     * 如果没有人工设置，飞书会自动生成
     */
    @Column(name = "lau_lark_user_id")
    private String larkUserId;

    /**
     * 用户在应用内的身份<br />
     * 同一个 user_id 在不同应用中的 open_id 不同<br />
     * open_id 是用户在特定应用下才存在的身份标识，所以其生成是由用户在第一次启用该应用时由系统赋值生成的<br />
     * 对开发者来说这都是「只读」字段<br />
     * <p>
     * 这里记录的是用户在【密林平台】对应的飞书应用中的 open_id
     */
    @Column(name = "lau_open_id")
    private String openId;

    /**
     * 用户在同一应用服务商提供的多个应用间的统一身份<br />
     * 让应用开发商可以把同个用户(以 user_id 为标识）在多个应用中的身份关联起来<br />
     * 在需要跨应用做用户 ID 关联的场景中，开发者可以使用飞书开放平台提供的 union_id<br />
     * union_id 都是用户在特定应用下才存在的身份标识，所以其生成是由用户在第一次启用该应用时由系统赋值生成的<br />
     * 对开发者来说这都是「只读」字段<br />
     * <p>
     * 这里记录的是用户在【密林平台】对应的飞书租户中的 union_id
     */
    @Column(name = "lau_union_id")
    private String unionId;
}
