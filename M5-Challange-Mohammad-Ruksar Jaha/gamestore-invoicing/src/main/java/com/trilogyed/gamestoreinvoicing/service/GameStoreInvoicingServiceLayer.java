package com.trilogyed.gamestoreinvoicing.service;

import com.trilogyed.gamestoreinvoicing.viewmodel.GameViewModel;
import com.trilogyed.gamestoreinvoicing.model.Invoice;
import com.trilogyed.gamestoreinvoicing.model.ProcessingFee;
import com.trilogyed.gamestoreinvoicing.model.Tax;
import com.trilogyed.gamestoreinvoicing.repository.InvoiceRepository;
import com.trilogyed.gamestoreinvoicing.repository.ProcessingFeeRepository;
import com.trilogyed.gamestoreinvoicing.repository.TaxRepository;
import com.trilogyed.gamestoreinvoicing.util.feign.GameStoreInvoicingClient;
import com.trilogyed.gamestoreinvoicing.viewmodel.ConsoleViewModel;
import com.trilogyed.gamestoreinvoicing.viewmodel.InvoiceViewModel;
import com.trilogyed.gamestoreinvoicing.viewmodel.TShirtViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GameStoreInvoicingServiceLayer {

    private final BigDecimal PROCESSING_FEE = new BigDecimal("15.49");
    private final BigDecimal MAX_INVOICE_TOTAL = new BigDecimal("999.99");
    private final String GAME_ITEM_TYPE = "Game";
    private final String CONSOLE_ITEM_TYPE = "Console";
    private final String TSHIRT_ITEM_TYPE = "T-Shirt";

    InvoiceRepository invoiceRepo;
    TaxRepository taxRepo;
    ProcessingFeeRepository processingFeeRepo;

    GameStoreInvoicingClient client;
    @Autowired
    public GameStoreInvoicingServiceLayer(InvoiceRepository invoiceRepo, TaxRepository taxRepo, ProcessingFeeRepository processingFeeRepo, GameStoreInvoicingClient client) {
        this.invoiceRepo = invoiceRepo;
        this.taxRepo = taxRepo;
        this.processingFeeRepo = processingFeeRepo;
        this.client = client;
    }


    // Game Feign methods
    public List<GameViewModel> getGames() {
        List<GameViewModel> gameList = this.client.getAllGames();
        return gameList;
    }

    public GameViewModel getGameById(@PathVariable("id") long gameId){
        return client.getGameInfo(gameId);
    }

    public List<GameViewModel> getGameTitle(@PathVariable("title") String title){
        return client.getGamesByTitle(title);
    }
    public List<GameViewModel> getGameRating(@PathVariable("esrb") String esrb){
        return client.getGamesByEsrbRating(esrb);
    }

    public List<GameViewModel> getGameStudio(@PathVariable("studio") String studio){
        return client.getGamesByStudio(studio);
    }

    public void updateGame(@RequestBody @Valid GameViewModel gameViewModel) {
        client.updateGame(gameViewModel);
    }

    // Console methods

    public ConsoleViewModel getConsoleById(@PathVariable("id") long consoleId) {
        return client.getConsole(consoleId);
    }

    public List<ConsoleViewModel> getConsoleManufacturer(@PathVariable("manufacturer") String manu){
        return client.getConsoleByManufacturer(manu);
    }

    public List<ConsoleViewModel> getConsoles(){
        return client.getAllConsoles();
    }

    public void updateConsole(@RequestBody @Valid ConsoleViewModel consoleViewModel){
        client.updateConsole(consoleViewModel);
    }


    // tshirt methods

    public TShirtViewModel createTShirt(@RequestBody @Valid TShirtViewModel tShirtViewModel){
        return client.createTShirt(tShirtViewModel);
    }
    public TShirtViewModel getTShirtById(@PathVariable("id") long tShirtId){
        return client.getTShirt(tShirtId);
    }

    public List<TShirtViewModel> getTShirtSize(@PathVariable("size") String size){
        return client.getTShirtsBySize(size);
    }

    public List<TShirtViewModel> getTShirtColor(@PathVariable("color") String color){
        return client.getTShirtsByColor(color);
    }

    @PutMapping("/tshirt")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTShirt(@RequestBody @Valid TShirtViewModel tShirtViewModel) {
        client.updateTShirt(tShirtViewModel);
    }

    public List<TShirtViewModel> getTShirts(){
        return client.getAllTShirts();
    }

    // Invoice methods
    public InvoiceViewModel createInvoice(InvoiceViewModel invoiceViewModel) {

        //validation...
        if (invoiceViewModel==null)
            throw new NullPointerException("Create invoice failed. no invoice data.");

        if(invoiceViewModel.getItemType()==null)
            throw new IllegalArgumentException("Unrecognized Item type. Valid ones: Console or Game");

        //Check Quantity is > 0...
        if(invoiceViewModel.getQuantity()<=0){
            throw new IllegalArgumentException(invoiceViewModel.getQuantity() +
                    ": Unrecognized Quantity. Must be > 0.");
        }

        //start building invoice...
        Invoice invoice = new Invoice();
        invoice.setName(invoiceViewModel.getName());
        invoice.setStreet(invoiceViewModel.getStreet());
        invoice.setCity(invoiceViewModel.getCity());
        invoice.setState(invoiceViewModel.getState());
        invoice.setZipcode(invoiceViewModel.getZipcode());
        invoice.setItemType(invoiceViewModel.getItemType());
        invoice.setItemId(invoiceViewModel.getItemId());

        //Checks the item type and get the correct unit price
        //Check if we have enough quantity
        if (invoiceViewModel.getItemType().equals(CONSOLE_ITEM_TYPE)) {
            ConsoleViewModel tempCon = null;
            ConsoleViewModel returnVal = getConsoleById(invoiceViewModel.getItemId());

            if (returnVal == null) {
                throw new IllegalArgumentException("Requested item is unavailable.");
            } else {
                tempCon = returnVal;
            }

            if (invoiceViewModel.getQuantity()> tempCon.getQuantity()){
                throw new IllegalArgumentException("Requested quantity is unavailable.");
            }

            // update quantity in catalog
            tempCon.setQuantity(tempCon.getQuantity() - invoiceViewModel.getQuantity());
            client.updateConsole(tempCon);

            // set unit price
            invoice.setUnitPrice(tempCon.getPrice());

        } else if (invoiceViewModel.getItemType().equals(GAME_ITEM_TYPE)) {
            GameViewModel tempGame = null;
            GameViewModel returnVal = getGameById(invoiceViewModel.getItemId());

            if (returnVal == null) {
                throw new IllegalArgumentException("Requested item is unavailable.");
            } else {
                tempGame = returnVal;
            }

            if(invoiceViewModel.getQuantity() >  tempGame.getQuantity()){
                throw new IllegalArgumentException("Requested quantity is unavailable.");
            }

            // update quantity in catalog
            tempGame.setQuantity(tempGame.getQuantity() - invoiceViewModel.getQuantity());
            client.updateGame(tempGame);

            // set unit price
            invoice.setUnitPrice(tempGame.getPrice());

        } else if (invoiceViewModel.getItemType().equals(TSHIRT_ITEM_TYPE)) {
            TShirtViewModel tempTShirt = null;
            TShirtViewModel returnVal = getTShirtById(invoiceViewModel.getItemId());

            if (returnVal == null) {
                throw new IllegalArgumentException("Requested item is unavailable.");
            } else {
                tempTShirt = returnVal;
            }

            if(invoiceViewModel.getQuantity() >  tempTShirt.getQuantity()){
                throw new IllegalArgumentException("Requested quantity is unavailable.");
            }

            // update quantity in catalog
            tempTShirt.setQuantity(tempTShirt.getQuantity() - invoiceViewModel.getQuantity());
            client.updateTShirt(tempTShirt);

            // set unit price
            invoice.setUnitPrice(tempTShirt.getPrice());

        } else {
            throw new IllegalArgumentException(invoiceViewModel.getItemType()+
                    ": Unrecognized Item type. Valid ones: T-Shirt, Console, or Game");
        }

        invoice.setQuantity(invoiceViewModel.getQuantity());

        invoice.setSubtotal(
                invoice.getUnitPrice().multiply(
                        new BigDecimal(invoiceViewModel.getQuantity())).setScale(2, RoundingMode.HALF_UP));

        //Throw Exception if subtotal is greater than 999.99
        if ((invoice.getSubtotal().compareTo(new BigDecimal(999.99)) > 0)) {
            throw new IllegalArgumentException("Subtotal exceeds maximum purchase price of $999.99");
        }

        //Validate State and Calc tax...
        BigDecimal tempTaxRate;
        Optional<Tax> returnVal = taxRepo.findById(invoice.getState());

        if (returnVal.isPresent()) {
            tempTaxRate = returnVal.get().getRate();
        } else {
            throw new IllegalArgumentException(invoice.getState() + ": Invalid State code.");
        }

        if (!tempTaxRate.equals(BigDecimal.ZERO))
            invoice.setTax(tempTaxRate.multiply(invoice.getSubtotal()));
        else
            throw new IllegalArgumentException( invoice.getState() + ": Invalid State code.");

        BigDecimal processingFee;
        Optional<ProcessingFee> returnVal2 = processingFeeRepo.findById(invoiceViewModel.getItemType());

        if (returnVal2.isPresent()) {
            processingFee = returnVal2.get().getFee();
        } else {
            throw new IllegalArgumentException("Requested item is unavailable.");
        }

        invoice.setProcessingFee(processingFee);

        //Checks if quantity of items if greater than 10 and adds additional processing fee
        if (invoiceViewModel.getQuantity() > 10) {
            invoice.setProcessingFee(invoice.getProcessingFee().add(PROCESSING_FEE));
        }

        invoice.setTotal(invoice.getSubtotal().add(invoice.getProcessingFee()).add(invoice.getTax()));

        //checks total for validation
        if ((invoice.getTotal().compareTo(MAX_INVOICE_TOTAL) > 0)) {
            throw new IllegalArgumentException("Subtotal exceeds maximum purchase price of $999.99");
        }

        invoice = invoiceRepo.save(invoice);

        return buildInvoiceViewModel(invoice);
    }

    public InvoiceViewModel getInvoice(long id) {
        Optional<Invoice> invoice = invoiceRepo.findById(id);
        if (invoice == null)
            return null;
        else
            return buildInvoiceViewModel(invoice.get());
    }

    public List<InvoiceViewModel> getAllInvoices() {
        List<Invoice> invoiceList = invoiceRepo.findAll();
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        List<InvoiceViewModel> exceptionList = null;

        if (invoiceList == null) {
            return exceptionList;
        } else {
            invoiceList.stream().forEach(i -> {
                ivmList.add(buildInvoiceViewModel(i));
            });
        }
        return ivmList;
    }


    public void updateInvoice(InvoiceViewModel ivm){

        Invoice invoice1 = new Invoice();
        invoice1.setId(ivm.getId());
        invoice1.setName(ivm.getName());
        invoice1.setStreet(ivm.getStreet());
        invoice1.setCity(ivm.getCity());
        invoice1.setState(ivm.getState());
        invoice1.setZipcode(ivm.getZipcode());
        invoice1.setItemType(ivm.getItemType());
        invoice1.setQuantity(ivm.getQuantity());
        invoice1.setUnitPrice(ivm.getUnitPrice());
        invoice1.setItemId(ivm.getItemId());
        invoice1.setSubtotal(ivm.getSubtotal());
        invoice1.setTax(ivm.getTax());
        invoice1.setProcessingFee(ivm.getProcessingFee());
        invoice1.setTotal(ivm.getTotal());

        invoiceRepo.save(invoice1);

    }

    public List<InvoiceViewModel> getInvoicesByCustomerName(String name) {
        List<Invoice> invoiceList = invoiceRepo.findByName(name);
        List<InvoiceViewModel> ivmList = new ArrayList<>();
        List<InvoiceViewModel> exceptionList = null;

        if (invoiceList == null) {
            return exceptionList;
        } else {
            invoiceList.stream().forEach(i -> ivmList.add(buildInvoiceViewModel(i)));
        }
        return ivmList;
    }

    public void deleteInvoice(long id){
        invoiceRepo.deleteById(id);
    }

    public InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {
        InvoiceViewModel invoiceViewModel = new InvoiceViewModel();
        invoiceViewModel.setId(invoice.getId());
        invoiceViewModel.setName(invoice.getName());
        invoiceViewModel.setStreet(invoice.getStreet());
        invoiceViewModel.setCity(invoice.getCity());
        invoiceViewModel.setState(invoice.getState());
        invoiceViewModel.setZipcode(invoice.getZipcode());
        invoiceViewModel.setItemType(invoice.getItemType());
        invoiceViewModel.setItemId(invoice.getItemId());
        invoiceViewModel.setUnitPrice(invoice.getUnitPrice());
        invoiceViewModel.setQuantity(invoice.getQuantity());
        invoiceViewModel.setSubtotal(invoice.getSubtotal());
        invoiceViewModel.setProcessingFee(invoice.getProcessingFee());
        invoiceViewModel.setTax(invoice.getTax());
        invoiceViewModel.setProcessingFee(invoice.getProcessingFee());
        invoiceViewModel.setTotal(invoice.getTotal());

        return invoiceViewModel;
    }

}
