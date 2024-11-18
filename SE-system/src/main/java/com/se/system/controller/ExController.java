package com.se.system.controller;

import com.se.common.annotation.Log;
import com.se.common.core.controller.BaseController;
import com.se.common.core.domain.AjaxResult;
import com.se.common.core.page.TableDataInfo;
import com.se.common.enums.BusinessType;
import com.se.common.utils.poi.ExcelUtil;
import com.se.system.domain.Ex;
import com.se.system.service.IExService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 炸药Controller
 * 
 * @author se
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/system/ex")
public class ExController extends BaseController
{
    @Autowired
    private IExService exService;

    /**
     * 查询炸药列表
     */
    @PreAuthorize("@ss.hasPermi('system:ex:list')")
    @GetMapping("/list")
    public TableDataInfo list(Ex ex)
    {
        startPage();
        List<Ex> list = exService.selectExList(ex);
        return getDataTable(list);
    }

    /**
     * 导出炸药列表
     */
    @PreAuthorize("@ss.hasPermi('system:ex:export')")
    @Log(title = "炸药", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Ex ex)
    {
        List<Ex> list = exService.selectExList(ex);
        ExcelUtil<Ex> util = new ExcelUtil<Ex>(Ex.class);
        util.exportExcel(response, list, "炸药数据");
    }

    /**
     * 获取炸药详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:ex:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(exService.selectExById(id));
    }

    /**
     * 新增炸药
     */
    @PreAuthorize("@ss.hasPermi('system:ex:add')")
    @Log(title = "炸药", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Ex ex)
    {
        return toAjax(exService.insertEx(ex));
    }

    /**
     * 修改炸药
     */
    @PreAuthorize("@ss.hasPermi('system:ex:edit')")
    @Log(title = "炸药", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Ex ex)
    {
        return toAjax(exService.updateEx(ex));
    }

    /**
     * 删除炸药
     */
    @PreAuthorize("@ss.hasPermi('system:ex:remove')")
    @Log(title = "炸药", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(exService.deleteExByIds(ids));
    }
}
