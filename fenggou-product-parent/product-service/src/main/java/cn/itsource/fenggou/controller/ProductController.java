package cn.itsource.fenggou.controller;

import cn.itsource.fenggou.domain.Sku;
import cn.itsource.fenggou.domain.Specification;
import cn.itsource.fenggou.service.IProductService;
import cn.itsource.fenggou.domain.Product;
import cn.itsource.fenggou.query.ProductQuery;

import cn.itsource.util.AjaxResult;
import cn.itsource.util.PageList;

import cn.itsource.util.StrUtils;
import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class ProductController {
    @Autowired
    public IProductService productService;

    /**
    * 保存和修改公用的
    * @param product  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/product",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Product product){
        try {
            if(product.getId()!=null){
                productService.updateById(product);
            }else{
                productService.save(product);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param ids
    * @return
    */
    @RequestMapping(value="/product",method=RequestMethod.DELETE)
    public AjaxResult delete(@RequestBody String ids){
        // TODO 同时删除fastdfs的图片
        try {
            System.out.println("ids============"+ids);
            //ids============{"ids":{"ids":"37,38"}} 前端{data: {ids:para}}
            String[] split = ids.split(",");
            Arrays.asList(split);
            for (String id : split) {
//                productService.removeByIds()
                productService.removeById(Long.valueOf(id));
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取
    @RequestMapping(value = "/product/{id}",method = RequestMethod.GET)
    public Product get(@PathVariable("id") Long id)
    {
        return productService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/product/list",method = RequestMethod.GET)
    public List<Product> list(){
        return productService.list();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/product/page",method = RequestMethod.POST)
    public PageList<Product> json(@RequestBody ProductQuery query)
    {
        return productService.selectByQuery(query);
    }
    //viewProperties

    /**
     * 根据商品id查找显示属性
     * @return
     */
    @RequestMapping(value = "/product/viewProperties",method = RequestMethod.GET)
    public List<Specification> viewProperties(@Param("productId") Long productId){
        return productService.getViewPropertiesByProductId(productId);
    }

    /**
     * 保存显示属性
     * @return
     */
    @RequestMapping(value = "/product/viewProperties",method = RequestMethod.POST)
    public AjaxResult viewProperties( @RequestBody Map<String,Object> para){
        try {
            //获取product
            Long productId = ((Integer)para.get("productId")).longValue();
            // service层处理 Product product = productService.getById(productId);
            //获取存储的数据viewProperties
            List<Specification> viewProperties = (List<Specification>) para.get("viewProperties");
            //在service层处理 viewProperties转json
            productService.saveViewProperties(productId,viewProperties);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存失败"+e.getMessage());
        }
    }

    /**
     * 根据商品id查找sku属性
     * @return
     */
    @RequestMapping(value = "/product/skuProperties",method = RequestMethod.GET)
    public List<Specification> skuProperties(@Param("productId") Long productId){
        return productService.getSkuPropertiesByProductId(productId);
    }

    /**
     * 保存sku属性
     * @param para
     * @return
     */
    @RequestMapping(value = "/product/skuProperties",method = RequestMethod.POST)
    public AjaxResult skuProperties(@RequestBody Map<String,Object> para){
        try {
            //获取product
            Long productId = ((Integer)para.get("productId")).longValue();
            //获取存储的数据viewProperties
            List<Specification> skuProperties = (List<Specification>) para.get("skuProperties");
            List<Map<String,String>> skus = (List<Map<String, String>>) para.get("skus");
            //在service层处理 viewProperties转json
            productService.saveSkuProperties(productId,skuProperties,skus);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存失败"+e.getMessage());
        }
    }

    /**
     * 根据productId查询对应的skus
     * @param productId
     * @return
     */
    @RequestMapping(value = "/product/skus",method = RequestMethod.GET)
    public List<Sku> skus(@Param("productId") Long productId){
        return productService.getSkusByProductId(productId);
    }

    /**
     * 商品上架（批量或单个）
     * @param ids
     * @return
     */
    @GetMapping("/product/onSale")
    public AjaxResult onSale( String ids){

        try {
            //用工具转换成long数组
            List<Long> idList = StrUtils.splitStr2LongArr(ids,",");
            productService.onSale(idList);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上架失败!"+e.getMessage());
        }
    }

    /**
     * 商品下架（批量或单个）
     * @param ids
     * @return
     */
    @GetMapping("/product/offSale")
    public AjaxResult offSale( String ids){

        try {
            //用工具转换成long数组
            List<Long> idList = StrUtils.splitStr2LongArr(ids,",");
            productService.offSale(idList);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("下架失败!"+e.getMessage());
        }
    }
    //loadCrumbs



}
