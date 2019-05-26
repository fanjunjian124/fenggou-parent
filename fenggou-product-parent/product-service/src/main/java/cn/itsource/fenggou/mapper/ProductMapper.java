package cn.itsource.fenggou.mapper;

import cn.itsource.basic.query.BaseQuery;
import cn.itsource.fenggou.domain.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    void onSale(@Param("ids") List<Long> ids,@Param("onSaleTime") long onSaleTime);

    /**
     *
     * 下架修改数据库状态和下架时间
     * @param ids
     * @param time
     */
    void offSale(@Param("ids") List<Long> ids, @Param("offSaleTime")long time);
}
