import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main
{
    enum enumForCheckRoman{
        I(1),II(2), III(3), IV(4),  V(5),VI(6),
        VII(7),VIII(8),IX(9),X(10);

        private int value;
        enumForCheckRoman(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    enum RomanNumbers{
        I(1),II(2), III(3), IV(4),  V(5),VI(6),
        VII(7),VIII(8),Ix(9),X(10), XX(20), XXX(30), XL(40),
        L(50), LX(60), LXX(70), LXXX(80), XC(90), C(100);
        private static final Map<Integer, RomanNumbers> MY_MAP = new HashMap<Integer, RomanNumbers>();
        static {

            for (RomanNumbers myEnum : values()) {
                MY_MAP.put(myEnum.getValue(), myEnum);
            }
        }
        private int value;

        RomanNumbers(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static RomanNumbers getByValue(int value) {
            return MY_MAP.get(value);
        }

        @Override
        public String toString() {
            return name();
        }
    }


    public static void main(String[] args) throws Exception{
        System.out.println("Введите выражение:");
        String input = input();
        String result = null;
        if(input != null || !input.equals("")) {
            result = calc(input);
        }else{
            throw new IOException("т.к. вы ввели пустую строку");
        }
       // if(result != null) {
           printResult(result);
        //}
    }

    public static String calc(String input) throws Exception{
        String resultOfCalc = null;

        char[] c_arr = input.toCharArray();
        int kindOfInputNumbers = checkKindOfNumbers(c_arr);

        String inputWithoutSpace = input.replace(" ", "");
        int[] inputNumbers;
        if(kindOfInputNumbers == 1){

            inputNumbers = parseInputToInt(inputWithoutSpace);
            if((inputNumbers[0] > 0 & inputNumbers[0] <= 10) & (inputNumbers[1] > 0 & inputNumbers[1] <= 10)){
                resultOfCalc = doCalculation(inputNumbers, inputWithoutSpace, "Arabic");
            }else{
                throw new IOException("т.к. вы ввели числа не из заданного диапазона");
            }

        } else if(kindOfInputNumbers == 2){

            inputNumbers = new int[2];
            String[] inputNumbersString = parseInputToRoman(inputWithoutSpace);
            try{
                checkRomanNumbers(inputNumbersString[0]);
                checkRomanNumbers(inputNumbersString[1]);

                inputNumbers[0] = transformRomanNumbersToInt(inputNumbersString[0]);
                inputNumbers[1] = transformRomanNumbersToInt(inputNumbersString[1]);

                resultOfCalc = doCalculation(inputNumbers, inputWithoutSpace, "Roman");
            }catch(IllegalArgumentException e){

                throw new IOException("т.к. вы ввели числа не из заданного диапазона");
            }

        } else if(kindOfInputNumbers == 0){

            throw new IOException("неверный ввод");
        }else if(kindOfInputNumbers == -1){
            throw new IOException("т.к. строка не является математической операцией");
        }
        else if(kindOfInputNumbers == -2){
            throw new IOException("формат математической операции не удовлетворяет заданию");

        }
        return resultOfCalc;

    }

    static String input() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String inputExpression = reader.readLine();
        return inputExpression;

    }
    static int checkKindOfNumbers(char[] inputLine){
        int kindOfInput = 0;
        int inputIntNumbersCheck = 0;
        int inputRomanNumbersCheck = 0;
        int countOfOperator = 0;

        for(char x : inputLine){
            if((((int) x >= 47 && (int) x <= 57) || ((int) x == 42 | (int) x == 43 | (int) x == 45)) || x == ' '){
                if((int) x == 42 | (int) x == 43 | (int) x == 45 | (int) x == 47){
                    countOfOperator++;
                }
                if(countOfOperator > 1){
                    break;
                }

                inputIntNumbersCheck++;
            }
        }
        if(inputIntNumbersCheck == inputLine.length && countOfOperator != 0){
            kindOfInput = 1;

        }else if(inputIntNumbersCheck == inputLine.length && countOfOperator == 0){
            kindOfInput = -1;
        }else if(countOfOperator > 1){
            kindOfInput = -2;
        }

        else if(countOfOperator == 1){
            countOfOperator=0;
            for(char x : inputLine){
                if(x == 'I' || x == 'V' || x == 'X'  ||  x == '/' || x == '*' || x == '+' || x == '-' || x == ' '){
                    if((int) x == 42 | (int) x == 43 | (int) x == 45 | (int) x == 45){
                        countOfOperator++;
                    }
                    if(countOfOperator>1){
                        break;
                    }
                    inputRomanNumbersCheck++;
                }
            }

            if(inputRomanNumbersCheck == inputLine.length){
                kindOfInput = 2;
            }else{
                kindOfInput = 0;
            }
        }else{
            kindOfInput = 0;
        }
        return  kindOfInput;
    }

    static int[] parseInputToInt(String input){
        int index;
        int[] inputNumbers = new int[2];
        String firstNumber = null;
        String secondNumber = null;
        if((index = input.indexOf("+")) != -1){
            firstNumber = input.substring(0, index);
            secondNumber = input.substring(index + 1);
        }else if((index = input.indexOf("-")) != -1){
            firstNumber = input.substring(0, index);
            secondNumber = input.substring(index + 1);
        }else if((index = input.indexOf("*")) != -1){
            firstNumber = input.substring(0, index);
            secondNumber = input.substring(index + 1);
        }else if((index = input.indexOf("/")) != -1){
            firstNumber = input.substring(0, index);
            secondNumber = input.substring(index + 1);
        }

        inputNumbers[0] = Integer.parseInt(firstNumber);
        inputNumbers[1] = Integer.parseInt(secondNumber);

        return inputNumbers;

    }

    static String checkRomanNumbers(String input) throws IllegalArgumentException{

        String check = null;
        // use another enum;
        enumForCheckRoman num = enumForCheckRoman.valueOf(input);
        check = num.name();


        return check;
    }

    static String[] parseInputToRoman(String input){
        int index;
        String[] inputNumbers = new String[2];
        String firstNumber = null;
        String secondNumber = null;
        if((index = input.indexOf("+")) != -1){
            firstNumber = input.substring(0, index);
            secondNumber = input.substring(index + 1);
        }else if((index = input.indexOf("-")) != -1){
            firstNumber = input.substring(0, index);
            secondNumber = input.substring(index + 1);
        }else if((index = input.indexOf("*")) != -1){
            firstNumber = input.substring(0, index);
            secondNumber = input.substring(index + 1);
        }else if((index = input.indexOf("/")) != -1){
            firstNumber = input.substring(0, index);
            secondNumber = input.substring(index + 1);
        }

        inputNumbers[0] = firstNumber;
        inputNumbers[1] = secondNumber;

        return inputNumbers;

    }

    static int transformRomanNumbersToInt(String romanNumbers){
        char[] temp = romanNumbers.toCharArray();
        int number = 0;

        if(temp.length == 1) {

            number = (RomanNumbers.valueOf((temp[0] + ""))).value;
        } else {

            for (int i = 0; i < temp.length - 1; i++) {

                if ((int) temp[i + 1] <= (int) temp[i]) {

                    if(i < temp.length - 2) {
                        number = number + (RomanNumbers.valueOf((temp[i] + ""))).value;
                    }else{
                        number = number + (RomanNumbers.valueOf((temp[i] + ""))).value + (RomanNumbers.valueOf((temp[i+1] + ""))).value;
                    }
                }else{
                    number = (RomanNumbers.valueOf((temp[i + 1] + ""))).value - (RomanNumbers.valueOf((temp[i] + ""))).value;
                }

            }
        }
        return number;
    }

    static String doCalculation(int[] numbers, String operator, String kindOfNumbers){
        String resulOfCalc = null;
        int result;
        if((operator.indexOf("+")) != -1){
            result = numbers[0] + numbers[1];
            if(kindOfNumbers.equals("Arabic")) {
                resulOfCalc = result + "";
            }else{
                resulOfCalc = transformInttoRoman(result);
            }
        }else if((operator.indexOf("-")) != -1){
            result = numbers[0] - numbers[1];
            if(kindOfNumbers.equals("Arabic")) {
                resulOfCalc = result + "";
            }else{
                if(result > 0) {
                    resulOfCalc = transformInttoRoman(result);
                }else{
                    throw new RuntimeException("т.к. в римской системе нет отрицательных чисел");

                }
            }
        }else if((operator.indexOf("*")) != -1){
            result = numbers[0] * numbers[1];
            if(kindOfNumbers.equals("Arabic")) {
                resulOfCalc = result + "";
            }else{
                resulOfCalc = transformInttoRoman(result);
            }
        }else if((operator.indexOf("/")) != -1){
            result = numbers[0] / numbers[1];
            if(kindOfNumbers.equals("Arabic")) {
                resulOfCalc = result + "";
            }else{
                resulOfCalc = transformInttoRoman(result);
            }

        }
        return resulOfCalc;
    }

    static String transformInttoRoman(int resultOfCalculation){
        String output = null;


        for(int i = 10; i <= 100;){
            int condition = resultOfCalculation - i;
            if(condition >= 1 &&  condition < 10){
                output = String.valueOf(RomanNumbers.getByValue(i)) + String.valueOf(RomanNumbers.getByValue(condition));
                break;

            }else if(condition == 0){

                output = String.valueOf(RomanNumbers.getByValue(i));
                break;
            }else if(condition < 0){
                output = String.valueOf(RomanNumbers.getByValue(resultOfCalculation));
                break;
            }
            i+=10;
        }

        return output;
    }

    static void printResult(String result){
        if(result != null) {
            System.out.println(result);
        }
    }


}