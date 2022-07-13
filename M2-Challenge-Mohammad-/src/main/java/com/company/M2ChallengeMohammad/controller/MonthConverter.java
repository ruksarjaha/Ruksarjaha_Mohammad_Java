package com.company.M2ChallengeMohammad.controller;

import com.company.M2ChallengeMohammad.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.company.M2ChallengeMohammad.Model.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RestController

public class MonthConverter {

    private static List<Month> monthList = new ArrayList<>(Arrays.asList(

            new Month(1, "January"),
            new Month(2, "February"),
            new Month(3, "March"),
            new Month(4, "April"),
            new Month(5, "May"),
            new Month(6, "June"),
            new Month(7, "July"),
            new Month(8, "August"),
            new Month(9, "September"),
            new Month(10, "October"),
            new Month(11, "November"),
            new Month(12, "December")
    ));

    @RequestMapping(value="/month/{monthNum}", method= RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Month getMonthNameByMonthNum(@PathVariable int monthNum) throws Exception {

        Month value = null;
        for (Month month : monthList) {
            if(month.getNumber() == monthNum){

                value = month;
                break;
            }
        }
        if(monthNum < 1 || monthNum > 12){

            throw new NotFoundException("Not in Range");
        }
        return value;
    }

    @RequestMapping(value = "/randomMonth", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Month getRandomMonth(){

        Random randomMonth = new Random();
        return monthList.get(randomMonth.nextInt(13));

    }
}
