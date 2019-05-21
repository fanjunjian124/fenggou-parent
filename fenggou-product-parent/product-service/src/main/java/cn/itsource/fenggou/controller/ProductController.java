package cn.itsource.fenggou.controller;

import cn.itsource.fenggou.service.IProductService;
import cn.itsource.fenggou.domain.Product;
import cn.itsource.fenggou.query.ProductQuery;

import cn.itsource.util.AjaxResult;
import cn.itsource.util.PageList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        // TODO 同时删除fastfds的图片
        try {
            System.out.println("ids============"+ids);
            //ids============{"ids":{"ids":"37,38"}} 前端{data: {ids:para}}
            String[] split = ids.split(",");
            for (String id : split) {
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
}
