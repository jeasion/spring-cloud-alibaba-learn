package com.jeasion.provider.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jeasion.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author liushanping
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@TableName("user")
public class UserDO extends BaseDO<UserDO> {



    @TableField("sex'")
    private Boolean sex;

    @TableField("money")
    private BigDecimal money;

    @TableField("name")
    private String name;

}
