package cn.itsource.fenggou.service.impl;

import cn.itsource.fenggou.domain.Product;
import cn.itsource.fenggou.domain.ProductExt;
import cn.itsource.fenggou.mapper.ProductExtMapper;
import cn.itsource.fenggou.mapper.ProductMapper;
import cn.itsource.fenggou.query.ProductQuery;
import cn.itsource.fenggou.service.IProductService;
import cn.itsource.util.PageList;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ProductExtMapper productExtMapper;

    @Override
    public PageList<Product> selectByQuery(ProductQuery query) {
        Page<Product> page = new Page<>(query.getPage(),query.getSize());
        //mybatis-plus
        IPage<Product> iPage = baseMapper.selectByQuery(page,query);
        //封装PageList
        return new PageList<Product>(iPage.getTotal(),iPage.getRecords());
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
}
