package com.se.system.service.impl;

import com.se.system.domain.Ex;
import com.se.system.domain.ExStone;
import com.se.system.domain.Stone;
import com.se.system.mapper.ExMapper;
import com.se.system.mapper.ExStoneMapper;
import com.se.system.mapper.StoneMapper;
import com.se.system.service.IExStoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 岩石炸药能量利用率Service业务层处理
 *
 * @author se
 * @date 2024-11-11
 */
@Service
public class ExStoneServiceImpl implements IExStoneService
{
    @Autowired
    private ExStoneMapper exStoneMapper;

    @Autowired
    private ExMapper exMapper;

    @Autowired
    private StoneMapper stoneMapper;

    /**
     * 查询岩石炸药能量利用率
     *
     * @param id 岩石炸药能量利用率主键
     * @return 岩石炸药能量利用率
     */
    @Override
    public ExStone selectExStoneById(Long id)
    {
        return exStoneMapper.selectExStoneById(id);
    }

    /**
     * 查询岩石炸药能量利用率列表
     *
     * @param exStone 岩石炸药能量利用率
     * @return 岩石炸药能量利用率
     */
    @Override
    public List<ExStone> selectExStoneList(ExStone exStone)
    {
        List<ExStone> res =  exStoneMapper.selectExStoneList(exStone);
//        for (int i = 0; i < res.size(); i++) {
//            ExStone item = res.get(i);
//            item.setExName();
//        }
        return  res;
    }

    /**
     * 新增岩石炸药能量利用率
     *
     * @param exStone 岩石炸药能量利用率
     * @return 结果
     */
    @Override
    public int insertExStone(ExStone exStone)
    {
        return exStoneMapper.insertExStone(exStone);
    }

    /**
     * 修改岩石炸药能量利用率
     *
     * @param exStone 岩石炸药能量利用率
     * @return 结果
     */
    @Override
    public int updateExStone(ExStone exStone)
    {
        return exStoneMapper.updateExStone(exStone);
    }

    /**
     * 批量删除岩石炸药能量利用率
     *
     * @param ids 需要删除的岩石炸药能量利用率主键
     * @return 结果
     */
    @Override
    public int deleteExStoneByIds(Long[] ids)
    {
        return exStoneMapper.deleteExStoneByIds(ids);
    }

    /**
     * 删除岩石炸药能量利用率信息
     *
     * @param id 岩石炸药能量利用率主键
     * @return 结果
     */
    @Override
    public int deleteExStoneById(Long id)
    {
        return exStoneMapper.deleteExStoneById(id);
    }

    /**
     * @param params
     * @return
     */
    @Override
    public String getConsumeOfTop5(List<Integer> params) {
        //岩石的能量
        for (int i = 0; i < params.size(); i++) {
            System.out.println("============"+params.get(i));
        }
        Integer type = params.get(params.size()-1);
        Double energy = (double) 1500;
        try {
            Stone stone = stoneMapper.selectStoneById(type);
            energy = stone.getEnergy();
        }catch(Exception e)
        {
            System.out.println(e);
        }
        Double[] comsumers = new Double[5];
        Map map = new HashMap();
        //前五中炸药的消耗量排序
        for (int i = 0; i < params.size()-1; i++) {
            Ex ex = exMapper.selectExById(params.get(i));
            Double power = ex.getPower();
            ExStone exStone = exStoneMapper.selectExStoneByStoneId(type,params.get(i));
            Double percent = exStone.getUserPer();
            Double comsumer = energy/percent/power;
            comsumers[i]=comsumer;
            map.put(comsumer,ex.getName());
        }
        Arrays.sort(comsumers, Collections.reverseOrder());
        String str = "根据能量匹配原理, 按照用量少排序后的到的结果是:\n";
        for (int i = 0; i < 5; i++) {
//            str+="第 "+i+" 接近的类型是 "+extypes.get(j)+" 概率是: "+f2+"\r";
            double one = comsumers[i];
            String  strFormat = String.format("%.2f",one);
            str+=  "第"+(i+1)+"名 :"+map.get(comsumers[i])+" 用量是: "+strFormat+"\r";
        }
        return str;
    }
}
