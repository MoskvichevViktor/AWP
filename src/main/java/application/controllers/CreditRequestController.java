package application.controllers;


import application.dao.CreditRequestDAO;
import application.models.CreditRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/creditRequest")
public class CreditRequestController {

    private final CreditRequestDAO creditRequestDAO;

    @Autowired
    public CreditRequestController(CreditRequestDAO creditRequestDAO) {
        this.creditRequestDAO = creditRequestDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("creditRequests", creditRequestDAO.index());
        return "creditRequest/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("creditRequest", creditRequestDAO.show(id));
        return "creditRequest/show";
    }

    @GetMapping("/new")
    public String newcreditRequest(@ModelAttribute("creditRequest") CreditRequest creditRequest) {
        return "creditRequest/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("creditRequest") @Valid CreditRequest creditRequest,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "creditRequest/new";

        creditRequestDAO.save(creditRequest);
        return "redirect:/creditRequest";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("creditRequest", creditRequestDAO.show(id));
        return "creditRequest/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("creditRequest") @Valid CreditRequest creditRequest, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "creditRequest/edit";

        creditRequestDAO.update(id, creditRequest);
        return "redirect:/creditRequest";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        creditRequestDAO.delete(id);
        return "redirect:/creditRequest";
    }
}