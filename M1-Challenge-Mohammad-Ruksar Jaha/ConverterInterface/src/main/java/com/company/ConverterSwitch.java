package com.company;

public class ConverterSwitch {

    public String convertMonth(int monthNum){

        switch (monthNum){
            case 1:
                return "January";

            case 2:
                return "February";

            case 3:
                return "March";

            case 4:
                return "April";

            case 5:
                return "May";

            case 6:
                return "June";

            case 7:
                return "July";

            case 8:
                return "August";

            case 9:
                return "September";

            case 10:
                return "October";

            case 11:
                return "November";

            case 12:
                return "December";


        }

    return "Number not in range";

    }
    public String convertDay( int dayNum){
        switch (dayNum){
            case 1:
                return ("Sunday");

            case 2:
                return ("Monday");

            case 3:
                return ("Tuesday");

            case 4:
                return ("Wednesday");

            case 5:
                return ("Thursday");

            case 6:
                return ("Friday");

            case 7:
                return ("Saturday");


        }

        return "Number not in range";
    }
}
