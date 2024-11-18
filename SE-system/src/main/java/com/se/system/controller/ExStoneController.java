package com.se.system.controller;

import com.se.common.annotation.Log;
import com.se.common.core.controller.BaseController;
import com.se.common.core.domain.AjaxResult;
import com.se.common.core.page.TableDataInfo;
import com.se.common.enums.BusinessType;
import com.se.common.utils.poi.ExcelUtil;
import com.se.system.domain.ExStone;
import com.se.system.service.IExStoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 岩石炸药能量利用率Controller
 * 
 * @author se
 * @date 2024-11-11
 */
@RestController
@RequestMapping("/system/exStone")
public class ExStoneController extends BaseController
{
    @Autowired
    private IExStoneService exStoneService;

    /**
     * 查询岩石炸药能量利用率列表
     */
    @PreAuthorize("@ss.hasPermi('system:stone:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExStone exStone)
    {
        startPage();
        List<ExStone> list = exStoneService.selectExStoneList(exStone);
        return getDataTable(list);
    }

    /**
     * 查询岩石炸药能量利用率列表
     */
    @PreAuthorize("@ss.hasPermi('system:stone:list')")
    @PostMapping("/getConsumeOfTop5")
    public AjaxResult getConsumeOfTop5(@RequestBody List<Integer> params)
    {
        String str = exStoneService.getConsumeOfTop5(params);
        return success(str);
    }
    /**
     * 导出岩石炸药能量利用率列表
     */
    @PreAuthorize("@ss.hasPermi('system:stone:export')")
    @Log(title = "岩石炸药能量利用率", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExStone exStone)
    {
        List<ExStone> list = exStoneService.selectExStoneList(exStone);
        ExcelUtil<ExStone> util = new ExcelUtil<ExStone>(ExStone.class);
        util.exportExcel(response, list, "岩石炸药能量利用率数据");
    }

    /**
     * 获取岩石炸药能量利用率详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:stone:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(exStoneService.selectExStoneById(id));
    }

    /**
     * 新增岩石炸药能量利用率
     */
    @PreAuthorize("@ss.hasPermi('system:stone:add')")
    @Log(title = "岩石炸药能量利用率", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ExStone exStone)
    {
        return toAjax(exStoneService.insertExStone(exStone));
    }

    /**
     * 修改岩石炸药能量利用率
     */
    @PreAuthorize("@ss.hasPermi('system:stone:edit')")
    @Log(title = "岩石炸药能量利用率", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ExStone exStone)
    {
        return toAjax(exStoneService.updateExStone(exStone));
    }

    /**
     * 删除岩石炸药能量利用率
     */
    @PreAuthorize("@ss.hasPermi('system:stone:remove')")
    @Log(title = "岩石炸药能量利用率", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(exStoneService.deleteExStoneByIds(ids));
    }
}
