package com.the_itach1.service;

import com.the_itach1.po.Tag;
import com.the_itach1.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TypeService {

    //保存标签
    Type saveType(Type type);
    //获取标签
    Type getType(Long id);
    //分页查询 列表的方式
    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);
    //修改标签
    Type updateType(Long id,Type type);
    //删除标签
    void deletType(Long id);
    //通过名称来获取类型
    Type getTypeByName(String name);


}
