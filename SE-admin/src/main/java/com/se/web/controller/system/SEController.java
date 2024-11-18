package com.se.web.controller.system;

import cn.hutool.core.date.DateTime;
import com.se.common.annotation.Log;
import com.se.common.core.controller.BaseController;
import com.se.common.core.domain.AjaxResult;
import com.se.common.core.page.TableDataInfo;
import com.se.common.enums.BusinessType;
import com.se.common.utils.StringUtils;
import com.se.system.domain.Se;
import com.se.system.domain.SeEsayAI;
import com.se.system.mapper.SeMapper;
import com.se.system.mapper.SysUserMapper;
import com.se.system.service.SeService;
import com.se.system.service.dto.SeQueryCriteria;
import com.se.system.service.impl.GlobalObject;
import com.se.web.controller.tool.ExType;
import org.apache.commons.collections.map.HashedMap;
import org.apache.hadoop.shaded.org.apache.http.client.methods.HttpGet;
import org.apache.hadoop.shaded.org.apache.http.impl.client.CloseableHttpClient;
import org.apache.hadoop.shaded.org.apache.http.impl.client.HttpClients;
import org.apache.hadoop.shaded.org.apache.http.util.EntityUtils;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.regression.RandomForestRegressor;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.wlld.randomForest.RandomForest;
import spire.math.Integral;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * 部门信息
 *
 * @author se
 */
@RestController
@RequestMapping("/system/se")
public class SEController extends BaseController
{
    @Autowired
    private SeService seService;

    @Autowired
    private SeMapper seMapper;
    @Autowired
    private SysUserMapper userMapper;


    /**
     * 获取数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeQueryCriteria Se)
    {
        startPage();
        List<Se> list = seService.selectSeList(Se);

        return getDataTable(list);
//        return success(depts);
    }
    /**
     * 获取数据列表
     */
    @PreAuthorize("@ss.hasPermi('system:dept:list')")
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody Se se)
    {
        int res  = seService.updateSe(se);
        return success(res);
//        return success(depts);
    }
//    /**
//     * 查询数据列表（排除节点）
//     */
//    @PreAuthorize("@ss.hasPermi('system:dept:list')")
//    @GetMapping("/list/exclude/{deptId}")
//    public AjaxResult excludeChild(@PathVariable(value = "deptId", required = false) Long deptId)
//    {
//        List<Se> depts = SeService.selectSeList(new Se());
//        depts.removeIf(d -> d.getId().intValue() == deptId || ArrayUtils.contains(StringUtils.split(d.getStatus(), ","), deptId + ""));
//        return success(depts);
//    }

    /**
     * 根据数据编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:dept:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable Integer id)
    {
//        SeService.checkSeDataScope(deptId);
        return success(seService.selectSeById(id));
    }

    /**
     * 新增数据
     */
    @PreAuthorize("@ss.hasPermi('system:dept:add')")
    @Log(title = "数据管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody List<Se> Se)
    {
//        if (!seService.checkSeNameUnique(Se))
//        {
//            return error("新增数据'" + Se.getType() + "'失败，类型已存在");
//        }
        for (int i = 0; i < Se.size(); i++) {
            Se item = Se.get(i);
            item.setCreateBy(getUsername());
            item.setCreateTime(DateTime.now());
            item.setType(1);
            item.setIsDel(0);
            if(item.getMd()==null)
            {
                item.setMd((double) 0);
            }
            if(item.getQd()==null)item.setQd((double) 0);
            if(item.getDkl()==null)item.setDkl((double) 0);
            if(item.getBsb()==null)item.setBsb((double) 0);
            if(item.getTxml()==null)item.setTxml((double) 0);
            if(item.getKju()==null)item.setKju((double) 0);
            if(item.getKd()==null)item.setKd((double) 0);
            if(item.getZydh()==null)item.setZydh((double) 0);
            if(item.getKjin()==null)item.setKjin((double) 0);


        }
        return success(seService.addSe(Se));
    }
    /**
     * 修改数据
     */
    @PreAuthorize("@ss.hasPermi('system:dept:edit')")
    @Log(title = "数据管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Se dept)
    {
//        Long deptId = dept.getId();
//        seService.checkSeDataScope(deptId);
//        if (!seService.checkSeNameUnique(dept))
//        {
//            return error("修改数据'" + dept.getSeName() + "'失败，数据名称已存在");
//        }
//        else if (dept.getParentId().equals(deptId))
//        {
//            return error("修改数据'" + dept.getSeName() + "'失败，上级数据不能是自己");
//        }
//        else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.getStatus()) && seService.selectNormalChildrenSeById(deptId) > 0)
//        {
//            return error("该数据包含未停用的子数据！");
//        }
        dept.setUpdateBy(getUsername());
        return toAjax(seService.updateById(dept));
    }


    /**
     * 删除数据
     */
    @PreAuthorize("@ss.hasPermi('system:dept:remove')")
    @Log(title = "数据管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(seService.deleteSeById(id));
    }

    @Log(title = " ", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult importData(MultipartFile file, boolean updateSupport)
    {
//        Se student = new Se();
//        student.setId(1000);
//        SysUser user = new SysUser();
//        user.setNickName("zz");
//        user.setUserName("alili");
//        user.setDeptId(1L);
//        userMapper.insertUser(user);
//        int num = 0;
//        System.out.println(1 / num);@Transactional(rollbackFor = Exception.class)因为事务的存在要么都成功要么都失败 这里有个异常导致都没有成功
//        seMapper.insert(student);
        //输入数据
        String operName = getUsername();
        try {
            String message = seService.importSe(file, updateSupport, operName);
            InputStream inputStream=file.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            //判断文件的保存路径后面是否以/结尾
            File filealready = new File("C:/"+getUsername()+"/rawData");
            if  (!filealready.exists()  && !filealready.isDirectory())
            {
                System.out.println("//不存在");
                filealready.mkdir();
            } else
            {
                System.out.println("//目录存在");
            }
            //写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            FileOutputStream fileOut = new FileOutputStream(filealready+"/rawData.csv");
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);
            byte[] buf = new byte[4096];
            int length = bis.read(buf);
            //保存文件
            while(length != -1)
            {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();

            return success(message);
        } catch (IOException e) {

            throw new RuntimeException(e);
        }

    }
    //数据的获取已经搞定，需要把模型训练出来
    @Log(title = "模型训练", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/trainAndForecast")
    public AjaxResult  trainAndForecast( )
    {
        GlobalObject globalObject = GlobalObject.getInstance();
        SeQueryCriteria criteria = new SeQueryCriteria();
        criteria.setIsDel(0);
        List<Se> datas = seService.selectSeList(criteria);
        return globalObject.trainModel(datas);
    }



    //模型训练出来后，根据模型生成判断结果
    @Log(title = "模型使用", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/onlyForecastDkl")
    public AjaxResult  onlyForecastDkl(@RequestBody Se se ) throws IOException {
        SparkSession spark = SparkSession.builder().appName("RandomForestRegression").master("local").getOrCreate();
        String modelPath = "C:/"+getUsername()+"/model/dkl";
        File file = new File(modelPath);
        List<Se> seList =  new ArrayList<>();
        seList.add(se);
        Encoder encoder = Encoders.bean(Se.class);
        Dataset<Row> dataset = spark.createDataset(seList, encoder);
        if(!file.exists()) {
            Dataset<Row> data = spark.read().format("csv")
                    .option("header","true")
                    .option("inferSchema","true")
                    .load("data.csv");
//        md	qd	kd	dkl	bsb	txml	kjin	kju	zydh	type
            String [] features = {"md", "qd","kd","bsb","txml","kjin","kju","zydh","type"};
            VectorAssembler featureIndexer = new VectorAssembler()
                    .setInputCols(features)
                    .setOutputCol("rawdata");
            Dataset<Row>[] splits = data.randomSplit(new double[] {0.9, 0.1});
            Dataset<Row> trainingData = splits[0];
            Dataset<Row> testData = splits[1];
            RandomForestRegressor rf = new RandomForestRegressor()
                    .setLabelCol("dkl")
                    .setFeaturesCol("rawdata")
                    .setNumTrees(10); // 设置树的数量，默认是10;
            Pipeline pipeline = new Pipeline()
                    .setStages(new PipelineStage[]{featureIndexer, rf});
            PipelineModel model = pipeline.fit(trainingData);
            model.save(modelPath);
            return success("大块率预测结果是");
        }else{
            // 模型存储地址
            PipelineModel pipelineModel = PipelineModel.load(modelPath);// 加载存储的模型
            List<Student> datas = new ArrayList<>();
            Encoder encoder2 = Encoders.bean(Se.class);
            Dataset<Row> dataset2 = spark.createDataset(seList, encoder);
            Dataset<Row> predictionDF2 = pipelineModel.transform(dataset2);      // 对用户数据进行预测
            List<Row> result = predictionDF2.limit(1).collectAsList();
            Row row = result.get(0);
            //bsb creatBy createTime dkl id isDel
            //kd kjin kju md qd remark
            //status txml type updateBy updateTime zydh
            double  prediction = (double)row.get(3);
            return success("大块率的预测结果是: "+prediction);
        }
    }
    //模型训练出来后，根据模型生成判断结果
    @Log(title = "模型使用", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/onlyForecastKd")
    public AjaxResult  onlyForecastKd(@RequestBody Se se ) throws IOException {
        SparkSession spark = SparkSession.builder().appName("RandomForestRegression").master("local").getOrCreate();
        String modelPath = "C:/"+getUsername()+"/model/kd";
        File file = new File(modelPath);
        List<Se> seList =  new ArrayList<>();
        seList.add(se);
        Encoder encoder = Encoders.bean(Se.class);
        Dataset<Row> dataset = spark.createDataset(seList, encoder);
        if(!file.exists()) {
            Dataset<Row> data = spark.read().format("csv")
                    .option("header","true")
                    .option("inferSchema","true")
                    .load("data.csv");
//        md	qd	kd	dkl	bsb	txml	kjin	kju	zydh	type
            String [] features = {"md", "qd","dkl","bsb","txml","kjin","kju","zydh","type"};
            VectorAssembler featureIndexer = new VectorAssembler()
                    .setInputCols(features)
                    .setOutputCol("rawdata");
            Dataset<Row>[] splits = data.randomSplit(new double[] {0.9, 0.1});
            Dataset<Row> trainingData = splits[0];
            Dataset<Row> testData = splits[1];
            RandomForestRegressor rf = new RandomForestRegressor()
                    .setLabelCol("kd")
                    .setFeaturesCol("rawdata")
                    .setNumTrees(10); // 设置树的数量，默认是10;
            Pipeline pipeline = new Pipeline()
                    .setStages(new PipelineStage[]{featureIndexer, rf});
            PipelineModel model = pipeline.fit(trainingData);
            model.save(modelPath);
            return success("块度预测结果是");
        }else{
            // 模型存储地址
            PipelineModel pipelineModel = PipelineModel.load(modelPath);// 加载存储的模型
            List<Student> datas = new ArrayList<>();
            Encoder encoder2 = Encoders.bean(Se.class);
            Dataset<Row> dataset2 = spark.createDataset(seList, encoder);
            Dataset<Row> predictionDF2 = pipelineModel.transform(dataset2);      // 对用户数据进行预测
            List<Row> result = predictionDF2.limit(1).collectAsList();
            Row row = result.get(0);
            //bsb creatBy createTime dkl id isDel
            //kd kjin kju md qd remark
            //status txml type updateBy updateTime zydh
            double  prediction = (double)row.get(6);
            return success("块度预测结果是: "+prediction);
        }
    }

    //模型训练出来后，根据模型生成判断结果
    @Log(title = "模型使用", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/onlyForecastEasyAIType")
    public AjaxResult  onlyForecastEasyAIType(@RequestBody Se se ){
        String str = "预测结果是 : ";
        GlobalObject globalObject = GlobalObject.getInstance();
        RandomForest[] rfArray = globalObject.getRfList();
        if(globalObject.getState()!=1){
            return error("模型还没有训练完成,请先训练模型");
        }
        try {
            SeEsayAI item = new SeEsayAI(se);
            Map<String ,Integer>map = new HashedMap();
            for (int i = 0; i< 5;i++){
                RandomForest rf =  rfArray[i];
                int type = rf.forest(item);
                String name = "";
                switch (type){
                    case 1: name="二级岩石乳化炸药";break;
                    case 2: name="岩石膨化硝铵炸药";break;
                    case 3: name="铵梯炸药";break;
                    case 4: name="浆状炸药";break;
                    case 5: name="粉状铵油炸药";break;
                    case 6: name="多孔粒状铵油炸药";break;
                    case 7: name="重铵油炸药";break;
                    case 8: name="1#混装乳化炸药";break;
                    case 9: name="2#混装乳化炸药";break;
                    case 10: name="3#混装乳化炸药";break;
                    case 11: name="4#混装乳化炸药";break;
                    case 12: name="5#混装乳化炸药";break;
                    case 13: name="6#混装乳化炸药";break;
                }
                map.put(name,i);
            }

            return success(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //如果java-spark不能满足需求,可以考虑调用python接口实现
    @PostMapping("/usePython")
    public AjaxResult getResFromPython(@RequestBody Se se)
    {
        StringBuilder params = new StringBuilder();
        SeEsayAI data = new SeEsayAI(se);
        params.append("md=").append(data.getMd());
        params.append("&qd=").append(data.getQd());
        params.append("&kd=").append(data.getKd());
        params.append("&dkl=").append(data.getDkl());
        params.append("&bsb=").append(data.getBsb());
        params.append("&txml=").append(data.getTxml());
        params.append("&kjin=").append(data.getKjin());
        params.append("&kju=").append(data.getKju());
        params.append("&zydh=").append(data.getZydh());
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:5000/python/RandomForestRegressor?" +
                params.toString()
        );
        String response = null;
        try {
            response = EntityUtils.toString(httpClient.execute(httpGet).getEntity());
            httpClient.close();
            response=response.substring(2,response.length()-2);
            String[] resList = StringUtils.split(response," ");

            StringBuilder strs = new StringBuilder();
            ExType  exType = new ExType();
            List<String> extypes = exType.getExTypes();
            Float[] toInt = new Float[resList.length];
            Float[] toIntraw = new Float[resList.length];
            //取出所有的概率并排序
            Map<Integer, Float> map = new HashMap<>();
//            Map<Integer,Float>  map = new HashedMap();
            for (int i = 0; i < resList.length; i++) {
                toInt[i]= Float.valueOf(resList[i]) ;
                toIntraw[i]= Float.valueOf(resList[i]) ;
            }


            Arrays.sort(toInt,Collections.reverseOrder());
            Float[] top5 = new Float[5];
            for (int i = 0; i < 5; i++) {
                top5[i] = toInt[i];
            }
            Map<Integer,Integer> res = new HashedMap();
            List<Integer> res2 = new ArrayList<>();
            String str = "";
            for (int i = 1; i < 6; i++) {
                for (int j = 0; j < toIntraw.length; j++) {
                    float f1 = toIntraw[j];
                    float f2 = top5[i-1];
                    if(f1==f2)
                    {
//                        str+="第 "+i+" 接近的类型是 "+extypes.get(j)+" 概率是: "+f2+"\r";
                        res.put(j+1,i);
                       if(!res2.contains(j+1)&&res2.size()<5)
                       {
                           res2.add(j+1);
                       }
//                        continue;
                    }
                }
            }
            return   success(res2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//    //模型训练出来后，根据模型生成判断结果
//    @Log(title = "模型使用", businessType = BusinessType.IMPORT)
//    @PreAuthorize("@ss.hasPermi('system:user:import')")
//    @PostMapping("/onlyForecastEasyAIRegression")
//    public AjaxResult  onlyForecastEasyAIRegression(@RequestBody Se Se ){
//        String str = "预测结果是 : ";
//        GlobalObject globalObject = GlobalObject.getInstance();
//        RegressionForest gf = globalObject.getGf();
//        if(globalObject.getState()!=1){
//            return error("模型还没有训练完成,请先训练模型");tool/gen
//        }
//        try {
//            int type =  gf.forest(Se);
//            switch (type){
//                case 1: str+="setosa";break;
//                case 2: str+="versicolor";break;
//                case 3: str+="virginica";break;
//            }
//            return success(str);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:post:export')")
    @PostMapping("/export")
    public AjaxResult export(Se post) throws IOException {
        return success();
    }
}
