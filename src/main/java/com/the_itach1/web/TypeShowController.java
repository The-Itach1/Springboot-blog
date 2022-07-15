package com.the_itach1.web;

import com.the_itach1.po.Type;
import com.the_itach1.service.BlogService;
import com.the_itach1.service.TypeService;
import com.the_itach1.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        @PathVariable Long id, Model model) {

        //得到某一数量的Type类型，默认为10000
        List<Type> types = typeService.listTypeTop(10000);

        //默认初始展示，遍历直到有具体的id
        if (id == -1) {
           id = types.get(0).getId();
        }

        //指定id展示
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);

        //展示查到的所有类型
        model.addAttribute("types", types);
        //根据当前选中的或者默认的一个Typeid，根据其查询对应blog，返回给前端
        model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
        //返回当前TypeId，供下一页或上一页使用。
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
