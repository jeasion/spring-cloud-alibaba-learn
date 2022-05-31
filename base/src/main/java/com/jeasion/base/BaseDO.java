package com.jeasion.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.Date;

/**
 * DO基础表.
 *
 * @author liushanping
 */
public abstract class BaseDO<T> {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建人id
     */
    @TableField(value = "creator_id", fill = FieldFill.INSERT)
    private Long creatorId;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改人id
     */
    @TableField(value = "regenerator_id", fill = FieldFill.INSERT_UPDATE)
    private Long regeneratorId;

    /**
     * 修改时间
     */
    @TableField(value = "regenerator_time", fill = FieldFill.INSERT_UPDATE)
    private Date regeneratorTime;

    /**
     * 是否逻辑删除.
     * <ul>
     *     <li>如果为true
     *      <ul>
     *          <li>在删除时不会进行物理删除，只会将del_flag = 1.</li>
     *          <li>查询时将del_flag = 0.</li>
     *       </ul>
     *     <li>如果为false，则对应表会直接物理删除.</li>
     * </ul>
     */
    @TableField(value = "logic_del_flag", exist = false)
    private Boolean logicDelFlag;
}
