package bi.baiqiu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import bi.baiqiu.pojo.User;
import bi.baiqiu.pojo.UserRole;
import bi.baiqiu.pojo.UserRoleFunction;

import com.github.abel533.mapper.Mapper;

public interface UserMapper extends Mapper<User> {

	User login(String username);

	List<UserRole> getRoles(String roleIds);

	List<UserRoleFunction> getUserRoleFunctions(String functionIds);
}
