package cn.itsource.fenggou.controller;

import cn.itsource.fenggou.query.BrandQuery;
import cn.itsource.fenggou.service.IBrandService;
import cn.itsource.fenggou.domain.Brand;

import cn.itsource.util.AjaxResult;
import cn.itsource.util.PageList;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BrandController {
    @Autowired
    public IBrandService brandService;

    /**
    * 保存和修改公用的
    * @param brand  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/brand",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Brand brand){
        try {
            System.out.println("brand====="+brand);
            if(brand.getId()!=null){
                brandService.updateById(brand);
            }else{
                brandService.save(brand);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
//    @RequestMapping(value="/brand/{id}",method=RequestMethod.DELETE)
//    public AjaxResult delete(@PathVariable("id") String id){
//        try {
//
//            brandService.removeById(Long.valueOf(id));
//            return AjaxResult.me();
//        } catch (Exception e) {
//        e.printStackTrace();
//            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
//        }
//    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @RequestMapping(value="/brand",method=RequestMethod.DELETE)
    public AjaxResult delete(@RequestBody String ids){
        try {
            System.out.println("ids============"+ids);
            //ids============{"ids":{"ids":"37,38"}} 前端{data: {ids:para}}
            String[] split = ids.split(",");
            for (String id : split) {
                brandService.removeById(Long.valueOf(id));
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    //获取
    @RequestMapping(value = "/brand/{id}",method = RequestMethod.GET)
    public Brand get(@PathVariable("id") Long id)
    {
        return brandService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/brand/list",method = RequestMethod.GET)
    public List<Brand> list(){
        return brandService.list();
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/brand/page",method = RequestMethod.POST)
    public PageList<Brand> json(@RequestBody BrandQuery query)
    {
        return brandService.getByQuery(query);
    }
}
