package cn.itsource.fenggou.service;

import cn.itsource.fenggou.domain.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IEmployeeService extends IService<Employee> {
    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    Employee login(String username,String password);
}
