package com.the_itach1.web.admin;

import com.the_itach1.po.Type;
import com.the_itach1.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;


    //分页展示，每页3条，按id排序，向前台发送page消息。
    @GetMapping("/types")
    public String types(@PageableDefault(size = 3,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    //新建一个类型，传一个空的Type对象过去，让前端编辑
    @GetMapping("/types/input-add")
    public String addinput(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    //根据要修改的类型的id，得到其相应类型，返回给前端

    @GetMapping("/types/edit")
    public String editInput(@RequestParam("id") Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    //根据id来删除某个标签
    @GetMapping("/types/delete")
    public String delete(@RequestParam("id") Long id,RedirectAttributes attributes) {
        typeService.deletType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }


    //前端编辑好我们类型后，返回给后端，创建一个类型
    @PostMapping("/types")
    public String addpost(Type type, BindingResult result, RedirectAttributes attributes) {

        //判断数据是否重复和不为空，并在前端生成相应提示。
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (type.getName().isEmpty()) {
            result.rejectValue("name","nameError","输入类型不能为空");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }

        //保存类型，并判断是否成功，并在前端生成相应提示
        Type t = typeService.saveType(type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(Type type, BindingResult result,@PathVariable Long id ,RedirectAttributes attributes) {
        //判断数据是否重复和不为空，并在前端生成相应提示。
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (type.getName().isEmpty()) {
            result.rejectValue("name","nameError","输入类型不能为空");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }

        //更新类型，并判断是否成功，并在前端生成相应提示
        Type t = typeService.updateType(id,type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }


}
