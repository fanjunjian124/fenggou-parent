package cn.itsource.fenggou.service;

import cn.itsource.fenggou.domain.Brand;
import cn.itsource.fenggou.query.BrandQuery;
import cn.itsource.util.PageList;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 品牌信息 服务类
 * </p>
 *
 * @author fanjunjian
 * @since 2019-05-16
 */
public interface IBrandService extends IService<Brand> {


    PageList<Brand> getByQuery(BrandQuery query);
}
