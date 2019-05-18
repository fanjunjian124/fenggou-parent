package cn.itsource.fenggou.service;

import cn.itsource.fenggou.domain.ProductType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品目录 服务类
 * </p>
 *
 * @author fanjunjian
 * @since 2019-05-16
 */
public interface IProductTypeService extends IService<ProductType> {
    List<ProductType> loadTreeData();
    public void generateStaticPage();

}
