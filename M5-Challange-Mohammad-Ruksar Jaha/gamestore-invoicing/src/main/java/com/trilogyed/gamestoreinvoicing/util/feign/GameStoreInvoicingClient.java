package com.trilogyed.gamestoreinvoicing.util.feign;

import com.trilogyed.gamestoreinvoicing.viewmodel.GameViewModel;
import com.trilogyed.gamestoreinvoicing.viewmodel.ConsoleViewModel;
import com.trilogyed.gamestoreinvoicing.viewmodel.TShirtViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "gamestore-catalog-service")
public interface GameStoreInvoicingClient {

    // Game
    @GetMapping("/game")
    @ResponseStatus(HttpStatus.OK)
    List<GameViewModel> getAllGames();

    @GetMapping("/game/{id}")
    @ResponseStatus(HttpStatus.OK)
    GameViewModel getGameInfo(@PathVariable("id") long gameId);

    @PutMapping("/game")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateGame(@RequestBody @Valid GameViewModel gameViewModel);

    @GetMapping("game/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    List<GameViewModel> getGamesByTitle(@PathVariable("title") String title);

    @GetMapping("game/esrbrating/{esrb}")
    @ResponseStatus(HttpStatus.OK)
    List<GameViewModel> getGamesByEsrbRating(@PathVariable("esrb") String esrb);

    @GetMapping("game/studio/{studio}")
    @ResponseStatus(HttpStatus.OK)
    List<GameViewModel> getGamesByStudio(@PathVariable("studio") String studio);


    // Console

    @GetMapping("/console/{id}")
    @ResponseStatus(HttpStatus.OK)
    ConsoleViewModel getConsole(@PathVariable("id") long consoleId);

    @GetMapping("/console/manufacturer/{manufacturer}")
    @ResponseStatus(HttpStatus.OK)
    List<ConsoleViewModel> getConsoleByManufacturer(@PathVariable("manufacturer") String manu);

    @GetMapping("/console")
    @ResponseStatus(HttpStatus.OK)
    List<ConsoleViewModel> getAllConsoles();

    @PutMapping("/console")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateConsole(@RequestBody @Valid ConsoleViewModel consoleViewModel);

// TShirt

    @PostMapping("/tshirt")
    @ResponseStatus(HttpStatus.CREATED)
    TShirtViewModel createTShirt(@RequestBody @Valid TShirtViewModel tShirtViewModel);

    @GetMapping("/tshirt/{id}")
    @ResponseStatus(HttpStatus.OK)
    TShirtViewModel getTShirt(@PathVariable("id") long tShirtId);

    @GetMapping("/tshirt/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    List<TShirtViewModel> getTShirtsBySize(@PathVariable("size") String size);

    @GetMapping("/tshirt/color/{color}")
    @ResponseStatus(HttpStatus.OK)
    List<TShirtViewModel> getTShirtsByColor(@PathVariable("color") String color);

    @GetMapping("/tshirt")
    @ResponseStatus(HttpStatus.OK)
    List<TShirtViewModel> getAllTShirts();

    @PutMapping("/tshirt")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void updateTShirt(@RequestBody @Valid TShirtViewModel tShirtViewModel);


}
