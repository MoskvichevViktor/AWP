package application.controllers;

import application.dao.CreditRequestDAO;
import application.dao.CreditResponseDAO;
import application.models.CreditRequest;
import application.models.CreditResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Random;

@Controller
@RequestMapping("/creditResponse")
public class CreditResponseController {

    private final CreditResponseDAO creditResponseDAO;
    private final CreditRequestDAO creditRequestDAO;

    @Autowired
    public CreditResponseController(CreditResponseDAO creditResponseDAO, CreditRequestDAO creditRequestDAO) {
        this.creditResponseDAO = creditResponseDAO;
        this.creditRequestDAO = creditRequestDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("creditResponses", creditResponseDAO.index());
        return "creditResponse/index";
    }
    //for sort by status
    @GetMapping("/approved")
    public String indexOnlyApproved(Model model) {
        model.addAttribute("creditResponses", creditResponseDAO.indexOnlyApproved());
        return "creditResponse/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("creditResponse", creditResponseDAO.show(id));
        return "creditResponse/show";
    }

    @GetMapping("/new")
    public String newcreditResponse(@ModelAttribute("creditResponse") CreditResponse creditResponse) {
        return "creditResponse/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("creditResponse") @Valid CreditResponse creditResponse,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "creditResponse/new";

        creditResponseDAO.save(creditResponse);
        return "redirect:/creditResponse";
    }

    //ответ на заявку
    @PostMapping("/{id}")
    public String createFromCR(@PathVariable("id") int id) {
        CreditRequest newCreditRequest = creditRequestDAO.show(id);
        CreditResponse creditResponse = new CreditResponse();
        creditResponse.setIdrequest(newCreditRequest.getId());
        creditResponse.setName(newCreditRequest.getName());
        creditResponse.setPasport(newCreditRequest.getPasport());
        Random random = new Random();
        creditResponse.setSum(random.nextInt(newCreditRequest.getCreditsum()));
        creditResponse.setPeriod(random.nextInt(12)*30);
        if(random.nextBoolean()){
            creditResponse.setStatus("approved");
        }else{
            creditResponse.setStatus("not approved");
        }
        creditResponseDAO.save(creditResponse);
        return "redirect:/creditResponse";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("creditResponse", creditResponseDAO.show(id));
        return "creditResponse/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("creditResponse") @Valid CreditResponse creditResponse, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "creditResponse/edit";

        creditResponseDAO.update(id, creditResponse);
        return "redirect:/creditResponse";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        creditResponseDAO.delete(id);
        return "redirect:/creditResponse";
    }
}