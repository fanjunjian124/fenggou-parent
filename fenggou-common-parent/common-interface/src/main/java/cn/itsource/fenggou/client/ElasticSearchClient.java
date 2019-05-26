package cn.itsource.fenggou.client;


import cn.itsource.fenggou.domain.ProductDoc;
import cn.itsource.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("FENGGOU-COMMON")
public interface ElasticSearchClient {

    /**
     * 保存
     * @param productDoc
     * @return
     */
    @PostMapping("/es/save")
    AjaxResult save(@RequestBody ProductDoc productDoc);

    /**
     * 批量存
     * @param productDocs
     * @return
     */
    @PostMapping("/es/saveBatch")
    AjaxResult saveBatch(@RequestBody List<ProductDoc> productDocs);

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/es/delete")
    AjaxResult delete(@RequestParam("id") Long id);

    /**
     * 批量删除
     * @param productDocs
     * @return
     */
    @PostMapping("/es/deleteBatch")
    AjaxResult deleteBatch(@RequestBody List<ProductDoc> productDocs);

    /**
     * 批量删除byId
     * @param ids
     * @return
     */
    @PostMapping("/es/deleteBatchById")
    AjaxResult deleteBatchById(@RequestBody List<Long> ids);

}
