package com.company;

public class ConverterIf implements Converter {

    public String convertMonth(int monthNum){
        if(monthNum == 1) {
            return "January";
        } else if (monthNum == 2){
            return "February";
        }
        else if (monthNum == 3){
            return "March";
        }
        else if (monthNum == 4){
            return"April";
        }
        else if (monthNum == 5){
            return "May";
        }
        else if (monthNum == 6){
            return "June";
        }
        else if (monthNum == 7){
            return "July";
        }
        else if (monthNum == 8){
            return "August";
        }
        else if (monthNum == 9){
           return "September";
        }
        else if (monthNum == 10){
            return "October";
        }
        else if (monthNum == 11){
            return "November";
        }
        else if (monthNum == 12){
            return "December";
        } else {
            return "Number not in range";
        }

    }
    public String convertDay(int dayNum){

        if(dayNum == 1){
            return "Sunday";

        } else if(dayNum == 2){
            return "Monday";

        } else if(dayNum == 3){
            return "Tuesday";
        }
        else if(dayNum == 4){
            return "Wednesday";
        }
        else if(dayNum == 5){
            return "Thursday";
        }
        else if(dayNum == 6){
            return "Friday";
        }
        else if(dayNum == 7){
            return "Saturday";

        } else {
            return "Number not in range";
        }

    }
}

