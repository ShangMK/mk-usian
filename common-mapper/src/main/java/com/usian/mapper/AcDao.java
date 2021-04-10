package com.usian.mapper;

import com.usian.pojo.ItemCatId;
import com.usian.pojo.TbItemCat;
import org.apache.ibatis.annotations.Param;

public interface AcDao {
    ItemCatId findbyid(@Param("id") Integer id);

    TbItemCat findbyparentid(@Param("id")String parseInt);
}
