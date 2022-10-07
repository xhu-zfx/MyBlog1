package com.panghu.blog.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.panghu.blog.constant.Consts;
import com.panghu.blog.domain.dto.CategoryInsertDTO;
import com.panghu.blog.domain.dto.CategoryUpdateDTO;
import com.panghu.blog.domain.entity.Category;
import com.panghu.blog.domain.entity.ResponseResult;
import com.panghu.blog.domain.vo.CategoryListVO;
import com.panghu.blog.domain.vo.ExcelCategoryVO;
import com.panghu.blog.domain.vo.PageVO;
import com.panghu.blog.enums.AppHttpCodeEnum;
import com.panghu.blog.service.CategoryService;
import com.panghu.blog.util.BeanCopyUtils;
import com.panghu.blog.util.WebUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.panghu.blog.enums.AppHttpCodeEnum.SYSTEM_ERROR;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/29 15:15
 * @description
 */
@RestController
@RequestMapping("/content/category")
public class CategoryController {

    @Resource
    CategoryService categoryService;

    @GetMapping("/listAllCategory")
    ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    void export(HttpServletResponse response){
        try {
            // 设置下载文件的请求头
            WebUtils.setDownLoadHeader(Consts.EXPORT_CATEGORY,response);
            // 获取数据
            List<Category> categoryList = categoryService.list();
            List<ExcelCategoryVO> excelCategoryVOList = BeanCopyUtils.copyBeanList(categoryList, ExcelCategoryVO.class);
            // 写入到excel文件中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVO.class).autoCloseStream(Boolean.FALSE).sheet(Consts.EXPORT_CATEGORY_SHEET)
                    .doWrite(excelCategoryVOList);
        } catch (Exception e) {
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response,JSONUtil.toJsonStr(result));
        }
    }

    @GetMapping("/list")
    ResponseResult listCategory(String name, String status, Integer pageNum , Integer pageSize){
        PageVO pageVO  = categoryService.listPageCategory(name,status,pageNum,pageSize);
        return ResponseResult.okResult(pageVO);
    }

    @PostMapping
    ResponseResult insertCategory(@RequestBody CategoryInsertDTO categoryInsertDTO){
        Category category = BeanCopyUtils.copyBeanSingle(categoryInsertDTO, Category.class);
        return categoryService.save(category) ?  ResponseResult.okResult(): ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @GetMapping("/{id}")
    ResponseResult getCategoryById(@PathVariable Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBeanSingle(category, CategoryListVO.class));
    }

    @PutMapping
    ResponseResult updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO){
        Category category = BeanCopyUtils.copyBeanSingle(categoryUpdateDTO, Category.class);
        return categoryService.updateById(category) ? ResponseResult.okResult(): ResponseResult.errorResult(SYSTEM_ERROR);
    }

    @DeleteMapping("/{id}")
    ResponseResult deleteCategoryByIds(@PathVariable("id") Long[] ids){
        return categoryService.deleteCategoryByIds(ids) >= 1 ? ResponseResult.okResult() : ResponseResult.errorResult(SYSTEM_ERROR);
    }
}
