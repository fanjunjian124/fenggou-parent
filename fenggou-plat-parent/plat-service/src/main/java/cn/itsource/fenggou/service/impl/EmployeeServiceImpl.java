package cn.itsource.fenggou.service.impl;

import cn.itsource.fenggou.domain.Employee;
import cn.itsource.fenggou.mapper.EmployeeMapper;
import cn.itsource.fenggou.service.IEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version: V1.0
 * @author: fan
 * @className: EmployeeServiceImpl
 * @description: todo
 * @date: 2019/5/14
 **/
@Service
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements IEmployeeService {
    /**
     * 没用到自己写的mapper 可以直接写baseMapper 不用注入暂时
     * @param username
     * @param password
     * @return
     */
    @Override
    public Employee login(String username, String password) {
        return baseMapper.selectOne(new QueryWrapper<Employee>()
                .eq("username",username)
                .eq("password",password));
    }
}
