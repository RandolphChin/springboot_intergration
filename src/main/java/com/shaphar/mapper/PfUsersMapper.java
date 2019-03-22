package com.shaphar.mapper;

import com.shaphar.domain.PfUsers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface PfUsersMapper {


    List<PfUsers> listUser();

    PfUsers selectByPrimaryKey(BigDecimal userId);

}