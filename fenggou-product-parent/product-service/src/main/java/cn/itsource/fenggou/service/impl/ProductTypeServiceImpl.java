package cn.itsource.fenggou.service.impl;

import cn.itsource.fenggou.client.RedisClient;
import cn.itsource.fenggou.client.TemplateClient;
import cn.itsource.fenggou.domain.ProductType;
import cn.itsource.fenggou.mapper.ProductTypeMapper;
import cn.itsource.fenggou.service.IProductTypeService;
import cn.itsource.util.StrUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author fanjunjian
 * @since 2019-05-16
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private TemplateClient templateClient;

    /**
     * 生成静态化首页
     */
    @Override
    public void generateStaticPage() {

        //模板   数据   目标文件的路径
        //路径问题最好不要硬编码，可以写到属性文件或者配置文件中，再读入

        //根据product.type.vm模板生成 product.type.vm.html
        String templatePath = "E:\\JetBrains\\IntelliJ IDEA_workspace\\fenggou-parent\\fenggou-product-parent\\product-service\\src\\main\\resources\\template\\product.type.vm";
        String targetPath = "E:\\JetBrains\\IntelliJ IDEA_workspace\\fenggou-parent\\fenggou-product-parent\\product-service\\src\\main\\resources\\template\\product.type.vm.html";
        List<ProductType> productTypes = loadDataTree();
        Map<String,Object> params = new HashMap<>();
        params.put("model",productTypes);
        params.put("templatePath",templatePath);
        params.put("targetPath",targetPath);
        templateClient.createStaticPage(params);

        //再根据home.vm生成home.html
        templatePath = "E:\\JetBrains\\IntelliJ IDEA_workspace\\fenggou-parent\\fenggou-product-parent\\product-service\\src\\main\\resources\\template\\home.vm";
        targetPath = "E:\\JetBrains\\IntelliJ IDEA_workspace\\ecommerce\\home.html";
        //新new Map 创建home模板的数据
        params = new HashMap<>();

        Map<String,Object> model = new HashMap<>();
        model.put("staticRoot","E:\\JetBrains\\IntelliJ IDEA_workspace\\fenggou-parent\\fenggou-product-parent\\product-service\\src\\main\\resources\\");
        params.put("model",model);
        params.put("templatePath",templatePath);
        params.put("targetPath",targetPath);
        templateClient.createStaticPage(params);

    }

    /**
     * 加载面包屑
     * @param productTypeId
     * @return
     */
    @Override
    public List<Map<String, Object>> loadCrumbs(Long productTypeId) {
        //查询当前类型
        ProductType productType = baseMapper.selectById(productTypeId);
        //获取path路径
        String path = productType.getPath().substring(1);// .1.2.3.
        List<Long> ids = StrUtils.splitStr2LongArr(path, "\\."); // 1,2,30
        List<Map<String,Object>> crumb = new ArrayList<>();//用来存放数据的
        for (Long id : ids) {
            Map<String,Object> map = new HashMap<>();
            //当前类型
            ProductType currentType = baseMapper.selectById(id);
            //当前类型的其他同级别的类型  同pid  排除当前的id
            List<ProductType> otherTypes = baseMapper.selectList(new QueryWrapper<ProductType>().eq("pid", currentType.getPid()).ne("id", currentType.getId()));
            map.put("currentType",currentType);
            map.put("otherTypes",otherTypes);
            crumb.add(map);
        }
        return crumb;
    }

    @Override
    public String getPathById(Long id) {
        ProductType productType = baseMapper.selectById(id);
        return productType.getPath();
    }

    /**
     * 加载树形菜单
     * @return
     */
    @Override
    public List<ProductType> loadTreeData() {
        String productTypesStr = redisClient.get("productTypes");
        if(StringUtils.isEmpty(productTypesStr)){
            //空就去数据查，查了之后往redis里再存
            List<ProductType> productTypes = loadDataTree();
            //redis里存的是字符串，所以list要转换成json
            String s = JSONArray.toJSONString(productTypes);

            String s1 = JSONObject.toJSONString(productTypes);
            System.out.println("s====="+s+"-----s1======"+s1);

            redisClient.set("productTypes",s);

            return productTypes;
        }
        //查出缓存有值就直接返回
        List<ProductType> productTypes = JSONArray.parseArray(productTypesStr, ProductType.class);
        return productTypes;
    }

    /**
     * 递归：根据父id 查询子菜单
     * @param pid
     * @return
     */
    public List<ProductType> loadDataTree(Long pid){
        //根据父id 查询子菜单，有父id 的都是子菜单
        List<ProductType> children = baseMapper.selectList(new QueryWrapper<ProductType>().eq("pid",pid));
        //递归出口如果没有子菜单就停止递归
        if(null==children && 0==children.size()){
            return null;
        }
        //循环children，把子菜单的id作为父id传入loadDataTree 的参数里，看看还有没有子菜单，直到没有为止
        for (ProductType child : children) {
            //查询出自己的子类 然后存入自己的children
            Long id = child.getId();
            List<ProductType> Grandchildren = loadDataTree(id);
            child.setChildren(Grandchildren);
        }
        return children;
    }

    /**
     * 循环方式：只发送一次sql查询，效率高
     * @return
     */
    public List<ProductType> loadDataTree(){
        //查询出所有菜单
        List<ProductType> children = baseMapper.selectList(null);
        //新建一个map 存入children，以便后面方便操作
        Map<Long,ProductType> map =new HashMap<>();
        for (ProductType child : children) {
            map.put(child.getId(),child);
        }
        //放所有的一级类型
        List<ProductType> list = new ArrayList<>();
        for (ProductType child : children) {
            //判断 如果是最定级就直接存入list
            if(child.getPid()==0){
                list.add(child);
            }else {
                //不是顶级的 就一定有父id，然后通过父id找到父级，把自己存入父级的children
                map.get(child.getPid()).getChildren().add(child);
            }
        }
        return list;
    }
//    =================重写增删改方法 每次都要去查询缓存=================

    @Override
    public boolean save(ProductType entity) {
        //先执行保存
        boolean save = super.save(entity);
        sychornizedOperate();
        return save;
    }
    @Override
    public boolean removeById(Serializable id) {
        boolean result = super.removeById(id);
        sychornizedOperate();
        return result;
    }

    @Override
    public boolean updateById(ProductType entity) {
        boolean result = super.updateById(entity);
        sychornizedOperate();
        return result;
    }


//    ============================结束===============================

    /**
     * 更新redis数据
     */
    private void updateRedis(){
        //查出最新的树形菜单
        List<ProductType> productTypes = loadDataTree();
        //转成json字符串缓存到redis中
        String jsonString = JSONArray.toJSONString(productTypes);
        redisClient.set("productTypes",jsonString);
    }

    /**
     * 同步方法，增删改的时候 使用同步方法修改缓存
     */
    private void sychornizedOperate(){
        updateRedis();
        generateStaticPage();
    }
}
