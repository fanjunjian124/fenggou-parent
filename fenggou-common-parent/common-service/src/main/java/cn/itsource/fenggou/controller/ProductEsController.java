package cn.itsource.fenggou.controller;

import cn.itsource.fenggou.domain.ProductDoc;
import cn.itsource.fenggou.repository.ProductDocRepository;
import cn.itsource.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author solargen
 * @version V1.0
 * @className ProductEsController
 * @description TODO
 * @date 2019/5/23
 */
@RestController
public class ProductEsController {

    @Autowired
    private ProductDocRepository repository;

    /**
     * 保存
     * @param productDoc
     * @return
     */
    @PostMapping("/es/save")
    public AjaxResult save(@RequestBody ProductDoc productDoc){
        try {
            repository.save(productDoc);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存失败!"+e.getMessage());
        }
    }

    /**
     * 批量存
     * @param productDocs
     * @return
     */
    @PostMapping("/es/saveBatch")
    public AjaxResult saveBatch(@RequestBody List<ProductDoc> productDocs){
        try {
            repository.saveAll(productDocs);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存失败!"+e.getMessage());
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/es/delete")
    public AjaxResult delete(@RequestParam("id") Long id){
        try {
            repository.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败!"+e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param productDocs
     * @return
     */
    @PostMapping("/es/deleteBatch")
    public AjaxResult deleteBatch(@RequestBody List<ProductDoc> productDocs){
        try {
            repository.deleteAll(productDocs);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败!"+e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/es/deleteBatchById")
    public AjaxResult deleteBatchById(@RequestBody List<Long> ids){
        try {
            for (Long id : ids) {
                //判断下
                //如果es中有数据，再删除，否则会抛出异常
                if(repository.existsById(id))
                    repository.deleteById(id);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败!"+e.getMessage());
        }
    }




}
