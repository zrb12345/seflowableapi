package com.se.web.controller;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.se.system.domain.Se;
import com.se.system.service.SeService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SeListener implements ReadListener<Se> {
    /**
     * 设置多少条数据入库
     */
    private static final int BATCH_COUNT = 500;

    /**
     * 缓存的数据
     */
    private List<Se> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * SeListener不能被spring管理，要每次读取excel都要new
     * 需要使用构造方法注入方式将SeService注入进来
     */
    private SeService seService;

    public SeListener(SeService SeService) {
        this.seService = SeService;
    }

    /**
     * 这个每一条数据解析都会来调用
     */
    @Override
    public void invoke(Se data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理，实际就是新建一个空集合
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 插入数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        seService.save(cachedDataList);
        log.info("存储数据库成功！");
    }
}
