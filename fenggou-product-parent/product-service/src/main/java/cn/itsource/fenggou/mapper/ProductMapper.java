package cn.itsource.fenggou.mapper;

import cn.itsource.basic.query.BaseQuery;
import cn.itsource.fenggou.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author fanjunjian
 * @since 2019-05-20
 */
public interface ProductMapper extends BaseMapper<Product> {

    IPage<Product> selectByQuery(Page<Product> page,@Param("query") BaseQuery query);

}
