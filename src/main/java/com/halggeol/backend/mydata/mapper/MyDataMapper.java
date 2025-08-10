package com.halggeol.backend.mydata.mapper;

import com.halggeol.backend.domain.MyProduct;
import com.halggeol.backend.domain.Mydata;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyDataMapper {
    void insertMydata(Mydata mydata);

    void insertMyProduct(MyProduct myProduct);
}
