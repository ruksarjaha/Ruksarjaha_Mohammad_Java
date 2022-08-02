package com.trilogyed.gamestoreinvoicing.controllers;

import com.trilogyed.gamestoreinvoicing.viewmodel.GameViewModel;
import com.trilogyed.gamestoreinvoicing.service.GameStoreInvoicingServiceLayer;
import com.trilogyed.gamestoreinvoicing.viewmodel.ConsoleViewModel;
import com.trilogyed.gamestoreinvoicing.viewmodel.InvoiceViewModel;
import com.trilogyed.gamestoreinvoicing.viewmodel.TShirtViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/invoice")
@CrossOrigin(origins = {"http://localhost:3000"})
public class InvoiceController {

    @Autowired
    GameStoreInvoicingServiceLayer service;

    // Invoice controllers

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel purchaseItem(@RequestBody @Valid InvoiceViewModel invoiceViewModel) {
        invoiceViewModel = service.createInvoice(invoiceViewModel);
        return invoiceViewModel;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InvoiceViewModel findInvoice(@PathVariable("id") long invoiceId) {
        InvoiceViewModel invoiceViewModel = service.getInvoice(invoiceId);
        if (invoiceViewModel == null) {
            throw new IllegalArgumentException("Invoice could not be retrieved for id " + invoiceId);
        } else {
            return invoiceViewModel;
        }
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> findAllInvoices() {
        List<InvoiceViewModel> invoiceViewModelList = service.getAllInvoices();

        if (invoiceViewModelList == null || invoiceViewModelList.isEmpty()) {
            throw new IllegalArgumentException("No invoices were found.");
        } else {
            return invoiceViewModelList;
        }
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<InvoiceViewModel> findInvoicesByCustomerName(@PathVariable String name) {
        List<InvoiceViewModel> invoiceViewModelList = service.getInvoicesByCustomerName(name);

        if (invoiceViewModelList == null || invoiceViewModelList.isEmpty()) {
            throw new IllegalArgumentException("No invoices were found for: "+name);
        } else {
            return invoiceViewModelList;
        }
    }
    // game controllers
    @GetMapping("/game")
    public List<GameViewModel> getAllGames() {
        return service.getGames();
    }

    @GetMapping("/game/{id}")
    public GameViewModel getGameInfo(@PathVariable("id") long gameId) {
        return service.getGameById(gameId);
    }

    @GetMapping("game/title/{title}")
    public List<GameViewModel> getGamesByTitle(@PathVariable("title") String title){
        return service.getGameTitle(title);
    }

    @GetMapping("game/esrbrating/{esrb}")
    public List<GameViewModel> getGamesByEsrbRating(@PathVariable("esrb") String esrb){
        return service.getGameRating(esrb);
    }

    @GetMapping("game/studio/{studio}")
    public List<GameViewModel> getGamesByStudio(@PathVariable("studio") String studio){
        return service.getGameStudio(studio);
    }

    // Console controllers

    @GetMapping("/console/{id}")
    public ConsoleViewModel getConsole(@PathVariable("id") long consoleId){
        return service.getConsoleById(consoleId);
    }
    @GetMapping("/console/manufacturer/{manufacturer}")
    public List<ConsoleViewModel> getConsoleByManufacturer(@PathVariable("manufacturer") String manu){
        return service.getConsoleManufacturer(manu);
    }
    @GetMapping("/console")
    public List<ConsoleViewModel> getAllConsoles(){
        return service.getConsoles();
    }

    // tshirt controllers
    @GetMapping("/tshirt/{id}")
    public TShirtViewModel getTShirt(@PathVariable("id") long tShirtId){
        return service.getTShirtById(tShirtId);
    }

    @GetMapping("/tshirt/size/{size}")
    public List<TShirtViewModel> getTShirtsBySize(@PathVariable("size") String size){
        return service.getTShirtSize(size);
    }
    @GetMapping("/tshirt/color/{color}")
    public List<TShirtViewModel> getTShirtsByColor(@PathVariable("color") String color){
        return service.getTShirtColor(color);
    }

    @GetMapping("/tshirt")
    public List<TShirtViewModel> getAllTShirts(){
        return service.getTShirts();
    }

    //this is for invoicing tests only
    @PostMapping("/tshirt")
    @ResponseStatus(HttpStatus.CREATED)
    TShirtViewModel createTShirt(@RequestBody @Valid TShirtViewModel tShirtViewModel){
        return service.createTShirt(tShirtViewModel);
    }


}
