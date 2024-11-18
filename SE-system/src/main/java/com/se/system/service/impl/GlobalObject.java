package com.se.system.service.impl;

import com.se.common.core.domain.AjaxResult;
import com.se.system.domain.Se;
import com.se.system.domain.SeEsayAI;
import lombok.Data;
import org.wlld.randomForest.DataTable;
import org.wlld.randomForest.RandomForest;
import org.wlld.regressionForest.RegressionForest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.se.common.core.domain.AjaxResult.success;
@Data
public class GlobalObject {

    private static GlobalObject instance;
    private DataObject dataObject;
    //模型初始化是 0 已经训练过了是 1
    private int state;
    //随机森林,用于分类,只能是已经存在的类型 还要搞一个回归森林 用于预测没有的数据
    private RandomForest[] rfList={null,null,null,null,null};
    private RegressionForest gf;
    private GlobalObject() {
        this.dataObject = new DataObject();
        //column代表属性名
        Set<String> column = new HashSet<>();
        column.add("md");
        column.add("qd");
        column.add("kd");
        column.add("dkl");
        column.add("bsb");
        column.add("txml");
        column.add("kjin");
        column.add("kju");
        column.add("zydh");
        column.add("type");
        DataTable dataTable = null;
        try {
            dataTable = new DataTable(column);
            //setKey 代表用那个属性作为要分类的类型
            dataTable.setKey("type");
            //初始化随机森林 设置树的个数,最好是奇数,偶数可能会出现投票结果等票的情况
            //树的个数不宜太多 也不宜太少 最好接近属性的数量
            RandomForest rf1 = new RandomForest(11);
            rf1.init(dataTable);//唤醒随机森林的树
            RandomForest rf2 = new RandomForest(9);
            rf2.init(dataTable);//唤醒随机森林的树
            RandomForest rf3 = new RandomForest(7);
            rf3.init(dataTable);//唤醒随机森林的树
            RandomForest rf4 = new RandomForest(5);
            rf4.init(dataTable);//唤醒随机森林的树
            RandomForest rf5 = new RandomForest(3);
            rf5.init(dataTable);//唤醒随机森林的树
//            gf = new RegressionForest(5);
            rfList[0]=rf1;
            rfList[1]=rf2;
            rfList[2]=rf3;
            rfList[3]=rf4;
            rfList[4]=rf5;
            state = 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static GlobalObject getInstance() {
        if (instance == null) {
            synchronized (GlobalObject.class) {
                if (instance == null) {
                    instance = new GlobalObject();
                }
            }
        }
        return instance;
    }
    public AjaxResult trainModel(List<Se> datas){
        try {
            //创建实体类,输入数据
            Se query = new Se();
            query.setIsDel(0);
//            List<Se> datas = seService.selectSeList(query);
            for (int i = 0 ;i <5;  i++){
                int limit = (int) (datas.size()*(i+1)*0.2);
                for (int j =0;j<limit ; j++) {
                    Se se = datas.get(j);
                    SeEsayAI item = new SeEsayAI(se);
                    rfList[i].insert(item);
                }
            }
            for (RandomForest rf : rfList){
                rf.study();
            }

//            //以上是学习过程
//            StringBuilder str = new StringBuilder("错误信息 : ");
//            int errorCount = 0;
//            for (int i = 0; i < datas.size(); i++) {
//                Se se = datas.get(i);
//                SeEsayAI item = new SeEsayAI(se);
//                int point = rf.forest(item);
//                if(point!=se.getType())
//                {
//                    errorCount++;
//                    //预测出这个是什么花
//                    System.out.println("预测错了 "+se.getId()+" 应该是 "+ se.getType()+" 预测成了 "+ point);
//                    str.append("预测错了 "+se.getId()+" 应该是 "+ se.getType()+" 预测成了 "+ point);
//                }
//            }
//            float errorpercent = (float)errorCount/datas.size();
//            System.out.println(" 对于"+datas.size()+"个数据,预测错误率是 "+errorpercent);
            state=1;
            return success(String.valueOf("训练完成"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}