package cn.itsource.fenggou.service;

import cn.itsource.fenggou.domain.Product;
import cn.itsource.fenggou.query.ProductQuery;
import cn.itsource.util.PageList;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
