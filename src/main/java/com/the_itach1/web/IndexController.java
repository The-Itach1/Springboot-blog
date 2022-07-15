package com.the_itach1.web;

import com.the_itach1.MyNotFoundException;
import com.the_itach1.service.BlogService;
import com.the_itach1.service.TagService;
import com.the_itach1.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//添加一个Controller代表这是控制器
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    //GetMapping，以get方式访问指定路径时就返回到index.html界面
    @GetMapping("/")
    public String index(@PageableDefault(size = 6,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable , Model model){

        //将所有博客展示出来
        model.addAttribute("page",blogService.listBlog(pageable));
        //展示前排名前6的类型
        model.addAttribute("types", typeService.listTypeTop(2));
        //展示前10的标签
        model.addAttribute("tags", tagService.listTagTop(3));
        //展示前8更新时间的博客进行推荐
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));

        //故意触发错误，会转到error.html
        //int i=9/0;

        //测试资源找不到异常
//        String blog=null;
//        if(blog==null)
//        {
//            throw  new MyNotFoundException("博客不存在");
//        }

        //System.out.println("---------index---------");


        return "index";
    }


    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog")
    public String blog(@RequestParam("id") Long id,Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }
}
