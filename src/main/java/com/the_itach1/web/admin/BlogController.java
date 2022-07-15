package com.the_itach1.web.admin;

import com.the_itach1.po.Blog;
import com.the_itach1.po.User;
import com.the_itach1.service.BlogService;
import com.the_itach1.service.TagService;
import com.the_itach1.service.TypeService;
import com.the_itach1.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable , BlogQuery blog, Model model)
    {
        //将所有的Type传到前端，用menu菜单进行展示，传回来的是typeId。
        model.addAttribute("types",typeService.listType());

        //向前端展示所有的博客信息。
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs";
    }

    //Post传参发送搜索，并且只使前端的局部改变,向前端发送搜索后的博客。
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 2,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable , BlogQuery blog, Model model)
    {
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        return "admin/blogs :: blogList";
    }

    //添加一个博客，转到添加博客界面
    @GetMapping("/blogs/input-add")
    public String addinput(Model model)
    {
        //传一个空的blog对象，让前端进行编辑
        model.addAttribute("blog",new Blog());
        //将所有的Type传到前端，用menu菜单进行展示。
        model.addAttribute("types",typeService.listType());
        //将所有的Tag传到前端，用menu菜单进行展示。
        model.addAttribute("tags",tagService.listTag());
        return "admin/blogs-input";
    }

    @GetMapping("/blogs/edit")
    public String editInput(@RequestParam("id") Long id, Model model) {

        Blog b;
        b=blogService.getBlog(id);
        //需要注意的是，我们想要展示原来的tags，但是由于tagIds变量并没有存到数据库，所以取出来时是没有赋值的，这里我们定义一个标签得到tagIds的方法。
        b.inittagIds();
        //根据get传参的id，来判断哪一个博客需要重新编辑。
        model.addAttribute("blog", b);
        //将所有的Type传到前端，用menu菜单进行展示。
        model.addAttribute("types",typeService.listType());
        //将所有的Tag传到前端，用menu菜单进行展示。
        model.addAttribute("tags",tagService.listTag());
        return "admin/blogs-input";
    }

    //根据id来删除某个博客
    @GetMapping("/blogs/delete")
    public String delete(@RequestParam("id") Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/blogs";
    }

    //将编辑好的博客,添加或重新更新到数据库
    @PostMapping("/blogs")
    public String post(RedirectAttributes attributes ,Blog blog, HttpSession session)
    {
        //给blog设置user对象，这篇博客是哪个user的
        blog.setUser((User) session.getAttribute("user"));

        //给blog设置type
        blog.setType(typeService.getType(blog.getType().getId()));
        //给blog设置tags,需要注意的是有多个tags
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b;

        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }


        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }

        return "redirect:/admin/blogs";
    }
}
