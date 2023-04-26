package com.bnd.SalesManager;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AppController {

    private final SalesDAO dao;

    public AppController(SalesDAO dao) {
        this.dao = dao;
    }

    @RequestMapping("/")
    public String viewHomePage(Model mode) {
        List<Sale> listSale = dao.list();
        mode.addAttribute("listSale", listSale);
        return "index";
    }

    @RequestMapping("/new")
    public String showNewForm(Model model){
        Sale sale = new Sale();
        model.addAttribute("sale",sale);
        return "new_form";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute("sale") Sale sale){
        dao.save(sale);
        return "redirect:/";
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id){
        ModelAndView modelAndView = new ModelAndView("edit_form");
        Sale sale = dao.get(id);
        modelAndView.addObject("sale",sale);
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@ModelAttribute("sale")Sale sale){
        dao.update(sale);
        return "redirect:/";
    }

    @RequestMapping(value = "/delete/{id}")
    public String delete(@PathVariable(name = "id") int id){
        dao.delete(id);
        return "redirect:/";
    }
}
