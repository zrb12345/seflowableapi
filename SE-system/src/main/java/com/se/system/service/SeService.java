package com.se.system.service;

import com.se.common.service.BaseService;
import com.se.system.domain.Se;
import com.se.system.service.dto.SeQueryCriteria;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 参数配置 服务层
 *
 * @author se
 */




public interface SeService extends BaseService<Se>
{


    /**
     * 查询参数 列表
     *
     * @param criteria 参数 信息
     * @return 参数 集合
     */
    List<Se> selectSeList(SeQueryCriteria criteria);

    int save(List<Se> cachedDataList);

    int addSe(List<Se> cachedDataList);
    Se selectSeById(Integer deptId);

    int updateSe(Se dept);

    int deleteSeById(Integer id);
    /**
     * 导入 数据
     *
     * @param file 数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    String importSe(MultipartFile file, boolean updateSupport, String operName) throws IOException;
}
