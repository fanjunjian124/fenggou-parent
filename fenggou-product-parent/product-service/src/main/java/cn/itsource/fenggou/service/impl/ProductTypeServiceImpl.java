package cn.itsource.fenggou.service.impl;

import cn.itsource.fenggou.domain.ProductType;
import cn.itsource.fenggou.mapper.ProductTypeMapper;
import cn.itsource.fenggou.service.IProductTypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品目录 服务实现类
 * </p>
 *
 * @author fanjunjian
 * @since 2019-05-16
 */
@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {
    /**
     * 加载树形菜单
     * @return
     */
    @Override
    public List<ProductType> loadTreeData() {
        return loadDataTree();
    }

    /**
     * 递归：根据父id 查询子菜单
     * @param pid
     * @return
     */
    public List<ProductType> loadDataTree(Long pid){
        //根据父id 查询子菜单，有父id 的都是子菜单
        List<ProductType> children = baseMapper.selectList(new QueryWrapper<ProductType>().eq("pid",pid));
        //递归出口如果没有子菜单就停止递归
        if(null==children && 0==children.size()){
            return null;
        }
        //循环children，把子菜单的id作为父id传入loadDataTree 的参数里，看看还有没有子菜单，直到没有为止
        for (ProductType child : children) {
            //查询出自己的子类 然后存入自己的children
            Long id = child.getId();
            List<ProductType> Grandchildren = loadDataTree(id);
            child.setChildren(Grandchildren);
        }
        return children;
    }

    /**
     * 循环方式：只发送一次sql查询，效率高
     * @return
     */
    public List<ProductType> loadDataTree(){
        //查询出所有菜单
        List<ProductType> children = baseMapper.selectList(null);
        //新建一个map 存入children，以便后面方便操作
        Map<Long,ProductType> map =new HashMap<>();
        for (ProductType child : children) {
            map.put(child.getId(),child);
        }
        //放所有的一级类型
        List<ProductType> list = new ArrayList<>();
        for (ProductType child : children) {
            //判断 如果是最定级就直接存入list
            if(child.getPid()==0){
                list.add(child);
            }else {
                //不是顶级的 就一定有父id，然后通过父id找到父级，把自己存入父级的children
                map.get(child.getPid()).getChildren().add(child);
            }
        }
        return list;
    }
}
