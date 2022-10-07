package com.panghu.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/9 15:27
 * @description  分页查询的数据返回,List集合row存储数据,total存储总条数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO {
    private List rows;
    private Long total;
}
