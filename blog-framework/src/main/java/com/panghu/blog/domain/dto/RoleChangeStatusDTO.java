package com.panghu.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/10/3 18:14
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleChangeStatusDTO {

    Long roleId;

    String status;
}
