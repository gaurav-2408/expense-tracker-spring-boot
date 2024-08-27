package com.example.expensetracker.controller;

import com.example.expensetracker.entity.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/")
    public String viewHomePage(Model model){
        List<Expense> expenses =  expenseService.getAllExpenses();
        BigDecimal totalAmount = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalAmount", totalAmount);
        return "index";
    }

    @GetMapping("/addExpense")
    public String showAddExpensePage(Model model){
        Expense expense = new Expense();
        model.addAttribute("expense", expense);
        return "add-expense";
    }

    @PostMapping("/saveExpense")
    public String saveExpense(@ModelAttribute("expense") Expense expense,Model model){
        expenseService.saveExpense(expense);
        return "redirect:/";
    }

    @GetMapping("/editExpense/{id}")
    public String showEditExpensePage(@PathVariable("id")long id, Model model){
         Expense expense = expenseService.getExpenseById(id);
         model.addAttribute("expense", expense);
         return "edit-expense";
    }

    @PostMapping("/updateExpense/{id}")
    public String updateExpense(@PathVariable("id") long id, @ModelAttribute("expense") Expense expense,Model model){
        Expense existingExpense = expenseService.getExpenseById(id);
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setDescription(expense.getDescription());
        expenseService.saveExpense(existingExpense);
        return "redirect:/";
    }

    @DeleteMapping("/deleteExpense/{id}")
    public String deleteExpense(@PathVariable("id")long id){
        expenseService.deleteExpenseById(id);
        return "redirect:/";
    }
}
