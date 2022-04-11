package application.controllers;

import application.dao.ContractDAO;
import application.dao.CreditResponseDAO;
import application.models.Contract;
import application.models.CreditResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/allContract")
public class AllContractController {

    private final ContractDAO contractDAO;
    private final CreditResponseDAO creditResponseDAO;

    @Autowired
    public AllContractController(ContractDAO contractDAO, CreditResponseDAO creditResponseDAO) {
        this.contractDAO = contractDAO;
        this.creditResponseDAO = creditResponseDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("allContract", contractDAO.index());
        return "allContract/index";
    }

    //for sort by status
    @GetMapping("/signed")
    public String indexOnlySigned(Model model) {
        model.addAttribute("allContract", contractDAO.indexOnlySigned());
        return "allContract/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("contract", contractDAO.show(id));
        return "allContract/show";
    }

    @GetMapping("/new")
    public String newContract(@ModelAttribute("contract") Contract contract) {
        return "allContract/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("contract") @Valid Contract contract,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "allContract/new";

        contractDAO.save(contract);
        return "redirect:/allContract";
    }

    //go to the Contract
    @PostMapping("/{id}")
    public String createContract(@PathVariable("id") int id) {
        //переносим в контракт значения из ответа на заявку
        CreditResponse newCreditResponse = creditResponseDAO.show(id);
        Contract contract = new Contract();
        contract.setName(newCreditResponse.getName());
        contract.setPasport(newCreditResponse.getPasport());
        contract.setPeriod(newCreditResponse.getPeriod());
        contract.setSum(newCreditResponse.getSum());
        contract.setStatus("not signed");//defolt
        contractDAO.save(contract);
        return "redirect:/allContract";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("contract", contractDAO.show(id));
        return "allContract/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("contract") @Valid Contract contract, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "allContract/edit";

        contractDAO.update(id, contract);
        return "redirect:/allContract";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        contractDAO.delete(id);
        return "redirect:/allContract";
    }
}