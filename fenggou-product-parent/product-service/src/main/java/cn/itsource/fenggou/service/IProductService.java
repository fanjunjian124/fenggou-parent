package cn.itsource.fenggou.service;

import cn.itsource.fenggou.domain.Product;
import cn.itsource.fenggou.domain.Sku;
import cn.itsource.fenggou.domain.Specification;
import cn.itsource.fenggou.query.ProductQuery;
import cn.itsource.util.PageList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author fanjunjian
 * @since 2019-05-20
 */
public interface IProductService extends IService<Product> {

    PageList<Product> selectByQuery(ProductQuery query);

    List<Specification> getViewPropertiesByProductId(Long productId);

    void saveViewProperties(Long productId, List<Specification> viewProperties);

    List<Specification> getSkuPropertiesByProductId(Long productId);

    void saveSkuProperties(Long productId, List<Specification> skuProperties, List<Map<String,String>> skus);

    List<Sku> getSkusByProductId(Long productId);

    void onSale(List<Long> ids);

    void offSale(List<Long> ids);
}
