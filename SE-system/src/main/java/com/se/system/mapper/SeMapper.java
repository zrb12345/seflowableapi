package com.se.system.mapper;

import com.se.common.mapper.CoreMapper;
import com.se.system.domain.Se;
import org.apache.ibatis.annotations.Delete;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门管理 数据层
 *
 * @author se
 */
@Repository
public interface SeMapper extends CoreMapper<Se>
{

    /**
     * 新增  信息
     *
     *  @Param(sysUserList) List<SysUser> sysUserList
     * @return 结果
     */
    public int insertList(List<Se> list );

    public List<Se> selectSeList(Se Se);

    void insertSe(Se Se);
    @Delete("delete from se")
    void deleteAll();
}
