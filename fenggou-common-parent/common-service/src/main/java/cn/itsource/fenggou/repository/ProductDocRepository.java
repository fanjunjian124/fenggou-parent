package cn.itsource.fenggou.repository;


import cn.itsource.fenggou.domain.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author solargen
 * @version V1.0
 * @className ProductRepository
 * @description TODO
 * @date 2019/5/23
 */
public interface ProductDocRepository extends ElasticsearchRepository<ProductDoc,Long>{
}
