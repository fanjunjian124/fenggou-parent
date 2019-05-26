package cn.itsource.fenggou.service.impl;


import cn.itsource.fenggou.client.ElasticSearchClient;
import cn.itsource.fenggou.client.FileClient;
import cn.itsource.fenggou.domain.*;
import cn.itsource.fenggou.mapper.*;
import cn.itsource.fenggou.query.ProductQuery;
import cn.itsource.fenggou.service.IProductService;
import cn.itsource.util.PageList;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSONArray.*;


/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author fanjunjian
 * @since 2019-05-20
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private FileClient fileClient;
    @Autowired
    private ProductExtMapper productExtMapper;
    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private ElasticSearchClient elasticSearchClient;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Override
    public PageList<Product> selectByQuery(ProductQuery query) {
        Page<Product> page = new Page<>(query.getPage(),query.getSize());
        //mybatis-plus
        IPage<Product> iPage = baseMapper.selectByQuery(page,query);
        //封装PageList
        return new PageList<Product>(iPage.getTotal(),iPage.getRecords());
    }

    /**
     * 获取商品的显示属性
     * @param productId
     * @return
     */
    @Override
    public List<Specification> getViewPropertiesByProductId(Long productId) {
        //获取商品
        Product product = baseMapper.selectById(productId);
        //判断商品是否有ViewProperties
        if(StringUtils.isEmpty(product.getViewProperties())){
            //根据product 的typeId 获取Specification
            List<Specification> specifications = specificationMapper.selectList(new QueryWrapper<Specification>().eq("productTypeId", product.getProductType()).eq("isSku",0));
            return specifications;
        }
        //如果有就 直接获取
        String viewProperties = product.getViewProperties();
        List<Specification> specifications = parseArray(viewProperties, Specification.class);
        //测试
        System.out.println(specifications);
        return specifications;
    }

    /**
     * 保存显示属性
     * @param productId
     * @param viewProperties
     */
    @Override
    public void saveViewProperties(Long productId, List<Specification> viewProperties) {
        Product product = baseMapper.selectById(productId);
        //转换json
        String viewPropertiesStr = toJSONString(viewProperties);
        product.setViewProperties(viewPropertiesStr);
        baseMapper.updateById(product);
    }

    /**
     * 查询sku属性
     * @param productId
     * @return
     */
    @Override
    public List<Specification> getSkuPropertiesByProductId(Long productId) {
        Product product = baseMapper.selectById(productId);

        if(StringUtils.isEmpty(product.getSkuProperties())){
            //根据product 的typeId 获取sku Specification
            List<Specification> specifications = specificationMapper.selectList(new QueryWrapper<Specification>().eq("productTypeId", product.getProductType()).eq("isSku",1));
            return specifications;
        }
        //如果有就 直接获取
        String skuProperties = product.getSkuProperties();
        System.out.println(skuProperties);

        List<Specification> specifications = parseArray(skuProperties, Specification.class);
        System.out.println(specifications);
        return specifications;

    }

    /**
     * 存一个sku
     * @param productId
     * @param skuProperties
     * @param skus
     */
    @Override
    public void saveSkuProperties(Long productId, List<Specification> skuProperties, List<Map<String, String>> skus) {
        //先根据商品id查询出商品
        Product product = baseMapper.selectById(productId);

        String jsonString = JSONArray.toJSONString(skuProperties);
        System.out.println("jsonString==="+jsonString);
        //测试字符串转换成List todo
//        List<Specification> specifications = JSONArray.parseArray(jsonString, Specification.class);
//        System.out.println("specifications===="+specifications);


        product.setSkuProperties(jsonString);
        baseMapper.updateById(product);
        //添加或者修改sku表
        //先删除该商品之前的sku
        skuMapper.delete(new QueryWrapper<Sku>().eq("productId",productId));
        //再重新添加
        List<Sku> skuList = new ArrayList<>();
        for (Map<String, String> skuMap : skus) {
            Sku sku = new Sku();
            sku.setProductId(productId);
            sku.setAvailableStock(Integer.parseInt(skuMap.get("availableStock")));
            sku.setCreateTime(new Date().getTime());
            sku.setPrice(Integer.parseInt(skuMap.get("price")));
            sku.setSkuIndex(skuMap.get("sku_index"));

            //获取除了sku_index price,availableStock之外的所有属性
            //name    sku_properties
            String name = "";
            Map<String,String> sku_properties = new LinkedHashMap<>();
            for (Map.Entry<String, String> entry : skuMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                //排除sku_index,price,availableStock
                if(key.equals("price")||key.equals("sku_index")||key.equals("availableStock")){
                    continue;
                }
                name += value;
                sku_properties.put(key,value);
            }
            sku.setSkuName(name);
            sku.setSkuProperties(JSONObject.toJSONString(sku_properties));
            //保存
            skuMapper.insert(sku);
        }
    }

    /**
     * 根据productId查询skus
     * @param productId
     * @return
     */
    @Override
    public List<Sku> getSkusByProductId(Long productId) {
        List<Sku> skus = skuMapper.selectList(new QueryWrapper<Sku>().eq("productId", productId));
        return skus;
    }

    /**
     * 商品批量上架
     * @param ids
     */
    @Override
    @Transactional
    public void onSale(List<Long> ids) {
        //修改上架时间和状态，保存到数据库
        baseMapper.onSale(ids,new Date().getTime());
        //查询数据库
        List<Product> products = baseMapper.selectBatchIds(ids);
        //保存到es中
        List<ProductDoc> productDocList = products2productDocs(products);
        elasticSearchClient.saveBatch(productDocList);
    }

    /**
     * 下架
     * @param ids
     */
    @Override
    public void offSale(List<Long> ids) {
        //修改下架时间和状态，保存到数据库
        baseMapper.offSale(ids,new Date().getTime());
        //删除es索引库的数据
        elasticSearchClient.deleteBatchById(ids);

    }

    /**
     * 类型转换 List<Product> 转成List<ProductDoc>
     * @param products
     * @return
     */
    private List<ProductDoc> products2productDocs(List<Product> products) {
        List<ProductDoc> productDocList = new ArrayList<>();
        for (Product product : products) {
            ProductDoc productDoc = product2ProductDoc(product);
            productDocList.add(productDoc);
        }
        return productDocList;
    }

    /**
     * 类型转换 Product转ProductDoc
     * @param product
     * @return
     */
    private ProductDoc product2ProductDoc(Product product) {
        ProductDoc productDoc = new ProductDoc();
        productDoc.setId(product.getId());
        productDoc.setProductTypeId(product.getProductType());
        productDoc.setBrandId(product.getBrandId());
        productDoc.setSaleCount(product.getSaleCount());
        productDoc.setOnSaleTime(product.getOnSaleTime());
        productDoc.setCommontCount(product.getCommentCount());
        productDoc.setViewCount(product.getViewCount());
        productDoc.setName(product.getName());
        productDoc.setSubName(product.getSubName());
        productDoc.setViewProperties(product.getViewProperties());
        productDoc.setSkuProperties(product.getSkuProperties());
        productDoc.setMedias(product.getMedias());

        //all字段
        Brand brand = brandMapper.selectById(product.getBrandId());
        String brandName = brand.getName();
        ProductType productType = productTypeMapper.selectById(product.getProductType());
        String productTypeName = productType.getName();
        String all = product.getName()+" "+product.getSubName()+" "+brandName+" "+productTypeName;
        productDoc.setAll(all);// 标题  副标题  品牌名称  类型名称 中间以空格拼接

        //最大价格和最小价格
        List<Sku> skus = skuMapper.selectList(new QueryWrapper<Sku>().eq("productId", product.getId()));
        Integer maxPrice=0;
        Integer minPrice=0;
        if(skus.size()>0){
             maxPrice = skus.get(0).getPrice();
             minPrice = skus.get(0).getPrice();
        }

        for (Sku sku : skus) {
            if(sku.getPrice()>maxPrice) maxPrice = sku.getPrice();
            if(sku.getPrice()<minPrice) minPrice = sku.getPrice();
        }
        productDoc.setMinPrice(minPrice);//每个sku都有对应的价格，从所有的价格中取到最小价格
        productDoc.setMaxPrice(maxPrice);//从所有的sku中取到最大价格

        return productDoc;
    }


    /**
     * 存入分表的数据
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public boolean save(Product entity) {
        try {
            super.save(entity);//保存product   是否获取id -- mybatisplus默认是自动获取id的
            //保存商品详情
            ProductExt productExt = new ProductExt();
            productExt.setDescription(entity.getDescription());
            productExt.setRichContent(entity.getContent());
            productExt.setProductId(entity.getId());
            productExtMapper.insert(productExt);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 重写修改，修改的同时判断是否上架，修改es数据
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public boolean updateById(Product entity) {
         super.updateById(entity);
        Product product = baseMapper.selectById(entity.getId());
        //判断是否已经上架
        if(product.getState()==1){
            //已经上架，则要同步到es中
            ProductDoc productDoc = product2ProductDoc(product);
            elasticSearchClient.save(productDoc);
        }
        return true;
    }

    /**
     * 删除  同步删除es
     * @param id
     * @return
     */
    @Override
    @Transactional
    public boolean removeById(Serializable id) {
        //查询出来，判断是否已经上架
        Product product = baseMapper.selectById(id);
        super.removeById(id);
        if(product.getState()==1){
            //删除es
            elasticSearchClient.delete((Long) id);
        }
        //fastdfs删除........
        String medias = product.getMedias();
        String[] mediasArr = medias.split(",");
        for (String s : mediasArr) {
            fileClient.deleteFile(s);
        }
        return true;
    }
}
