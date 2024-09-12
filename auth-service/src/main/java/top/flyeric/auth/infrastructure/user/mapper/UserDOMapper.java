package top.flyeric.auth.infrastructure.user.mapper;

import top.flyeric.auth.infrastructure.user.dataobject.UserDO;
import top.flyeric.auth.infrastructure.user.dataobject.UserDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDOMapper {
    long countByExample(UserDOExample example);

    int deleteByExample(UserDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserDO row);

    int insertSelective(UserDO row);

    UserDO selectOneByExample(UserDOExample example);

    List<UserDO> selectByExample(UserDOExample example);

    UserDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") UserDO row, @Param("example") UserDOExample example);

    int updateByExample(@Param("row") UserDO row, @Param("example") UserDOExample example);

    int updateByPrimaryKeySelective(UserDO row);

    int updateByPrimaryKey(UserDO row);

    int batchInsert(@Param("list") List<UserDO> list);
}