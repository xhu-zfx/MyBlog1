package com.panghu.blog.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.panghu.blog.constant.Consts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/8 17:27
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExcelCategoryVO {

    @ExcelProperty(Consts.EXPORT_CATEGORY_NAME)
    private String name;

    @ExcelProperty(Consts.EXPORT_CATEGORY_DESCRIPTION)
    private String description;

    @ExcelProperty(Consts.EXPORT_CATEGORY_STATUS)
    private String status;
}
