package cn.itsource.fenggou.service.impl;

import cn.itsource.fenggou.domain.Product;
import cn.itsource.fenggou.mapper.ProductMapper;
import cn.itsource.fenggou.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author fanjunjian
 * @since 2019-05-16
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
