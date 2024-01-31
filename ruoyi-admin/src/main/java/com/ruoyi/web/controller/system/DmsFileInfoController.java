package com.ruoyi.web.controller.system;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.DmsFileInfo;
import com.ruoyi.system.service.IDmsFileInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 文件信息Controller
 * 
 * @author HYZ
 * @date 2024-01-31
 */
@RestController
@RequestMapping("/system/dmsfileupload")
public class DmsFileInfoController extends BaseController
{
    @Autowired
    private IDmsFileInfoService dmsFileInfoService;

    /**
     * 查询文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:list')")
    @GetMapping("/list")
    public TableDataInfo list(DmsFileInfo dmsFileInfo)
    {
        startPage();
        List<DmsFileInfo> list = dmsFileInfoService.selectDmsFileInfoList(dmsFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:export')")
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DmsFileInfo dmsFileInfo)
    {
        List<DmsFileInfo> list = dmsFileInfoService.selectDmsFileInfoList(dmsFileInfo);
        ExcelUtil<DmsFileInfo> util = new ExcelUtil<DmsFileInfo>(DmsFileInfo.class);
        util.exportExcel(response, list, "文件信息数据");
    }

    /**
     * 获取文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") String fileId)
    {
        return success(dmsFileInfoService.selectDmsFileInfoByFileId(fileId));
    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DmsFileInfo dmsFileInfo)
    {
        return toAjax(dmsFileInfoService.insertDmsFileInfo(dmsFileInfo));
    }

    /**
     * 修改文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DmsFileInfo dmsFileInfo)
    {
        return toAjax(dmsFileInfoService.updateDmsFileInfo(dmsFileInfo));
    }

    /**
     * 删除文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:dmsfileupload:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable String[] fileIds)
    {
        return toAjax(dmsFileInfoService.deleteDmsFileInfoByFileIds(fileIds));
    }
}
