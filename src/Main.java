import java.io.BufferedReader;
import java.io.InputStreamReader;


class Main {

    public static void main(String[] args) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String str = reader.readLine();                                                                  //считываем введенную строку
        Character[] charss = new Character[]{'*', '+', '-', '/'};   //кладем операторы в массив

        splitter(str, charss);
    }
    static void splitter(String str, Character[] charss) throws Exception {
        int key = 0;   // по key определим какое действие (умножение или...) во введенной строке
        int count = 0;  // count - счетчик операторов во введенной строке, по нему будем выбрасывать исключения
        for (int i = 0; i < 4; i++) {

            int index = str.indexOf(charss[i]);
            int index1 = str.lastIndexOf(charss[i]);
            //ловим ошибку, когда во введенной строке два одинаковых операнда, типа 8**9
            if (index != index1) throw new Exception("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
            if (index != -1) {// присутствует ли хоть один оператор во введенной строке?
                key = i;      // присутствует, под индексом key
            } else {
                count = count + index;
            }
        }
        // по key определяем от 0 до 3 какой оператор использовать");
        //count -  количество отсутствующих операторов");
        //  str.indexOf(charss[key])  - порядковый номер во введенной строке сработавшего оператора, по нему будем резать строку;
        //НАДО РАЗРЕЗАТЬ СТРОКУ ПО ИНДЕКСУ ОПЕРАТОРА
        if (count == -4) throw new Exception("т.к. во введенной строке отсутствует оператор");
        if (count >= -2) throw new Exception("т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        String substring0 = null;
        String substring1 = null;
        try {
            substring0 = str.substring(0, str.indexOf(charss[key]));             //режем строку до оператора
            substring1 = str.substring(str.indexOf(charss[key]) + 1);   // после оператора
            int a = Integer.parseInt(substring0);
            int b = Integer.parseInt(substring1);
            if (a > 10 || b > 10 || a < 1 || b < 1) throw new Exception("Ошибка: вводите числа от 1 до 10");
            calc(a, b, key);
        } catch (NumberFormatException e) {        // при ошибке парсинга предполагаем что введены римские числа
            romaCalc(substring0, substring1, key);
        }
    }
    static void calc(int a, int b, int key) {
        if (key == 0) {
            System.out.println(a * b);
        } else if (key == 1) {
            System.out.println(a + b);
        } else if (key == 2) {
            System.out.println(a - b);
        } else {
            System.out.println(a / b);
        }
    }
    static void romaCalc(String substring0, String substring1, int key) throws Exception {
        String[] units = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};  //римские единицы
        String[] tens = new String[]{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC", "C"};   //римские десятки

        int a = 0;
        for (int i = 0; i < 11; i++) {
            if (substring0.equals(units[i])) {    //сравниваю на соответствие введенное с римскими единицами
                a = i;
            }
        }
        int b = 0;
        for (int j = 0; j < 11; j++) {
            if (substring1.equals(units[j])) {
                b = j;
            }
        }
        if (a == 0 || b == 0) throw new Exception("т.к. оба операнда должны быть либо римскими, либо арабскими числами, в пределах от 1 до 10");
        if (key == 0) System.out.println(tens[(a * b) / 10] + units[(a * b) % 10]);  //пишу сначала римские десятки, потом единицы
        if (key == 1) System.out.println(tens[(a + b) / 10] + units[(a + b) % 10]);
        if (key == 2) System.out.println(tens[(a - b) / 10] + units[(a - b) % 10]);
        if (key == 2 && a < b) throw new Exception("т.к. в римской системе нет отрицательных чисел");
        if (key == 2 && a == b) throw new Exception("Ошибка: римляне не придумали ноль");
        if (key == 3 && a > b) System.out.println(tens[(a / b) / 10] + units[(a / b) % 10]);
        else if (key == 3 && a < b) throw new Exception("т.к. в римской системе нет нуля");
    }
}




