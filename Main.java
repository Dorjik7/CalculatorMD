import java.util.Scanner;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);

        //Класс для вывода сообщений об обшибке
        class MyExceptions extends Exception {
            MyExceptions(String message) {
                super(message);
            }
        }

        //Список соответствий арабских и римских чисел
        enum RomanNumbers {
            I(1),II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10),XI(11),XII(12),XIII(13), XIV(14),
            XV(15), XVI(16), XVII(17), XVIII(18), XIX(19), XX(20), XXI(21), XXII(22), XXIII(23), XXIV(24), XXV(25),
            XXVI(26), XXVII(27), XXVIII(28), XXIX(29), XXX(30), XXXI(31), XXXII(32), XXXIII(33), XXXIV(34), XXXV(35),
            XXXVI(36), XXXVII(37), XXXVIII(38), XXXIX(39), XL(40), XLI(41), XLII(42), XLIII(43), XLIV(44), XLV(45),
            XLVI(46), XLVII(47), XLVIII(48), XLIX(49), L(50), LI(51), LII(52), LIII(53), LIV(54), LV(55), LVI(56),
            LVII(57), LVIII(58), LIX(59), LX(60), LXI(61), LXII(62), LXIII(63), LXIV(64), LXV(65), LXVI(66), LXVII(67),
            LXVIII(68), LXIX(69), LXX(70), LXXI(71), LXXII(72), LXXIII(73), LXXIV(74), LXXV(75), LXXVI(76), LXXVII(77),
            LXXVIII(78), LXXIX(79), LXXX(80),  LXXXI(81), LXXXII(82), LXXXIII(83), LXXXIV(84), LXXXV(85), LXXXVI(86),
            LXXXVII(87), LXXXVIII(88), LXXXIX(89), XC(90), XCI(91), XCII(92), XCIII(93), XCIV(94), XCV(95), XCVI(96),
            XCVII(97), XCVIII(98), XCIX(99), C(100);
            final int number;
            RomanNumbers(int num){
                this.number = num;
            }

            // Получение числового значения
            int isNumber() {
                return number;
            }

            // Приведение типов данных
            static RomanNumbers toArabic(int num){
                return values()[num-1];
            }
            static RomanNumbers toArabic(String str){
                return RomanNumbers.valueOf(str);
            }

        }

        // Класс для описания арифметических операций и исключений по ним
        class Calculator{
            static boolean operator(char c){
                return c == '+' || c == '-' ||c == '*' || c == '/';
            }
            static boolean validNumber(int num){
                return num>=1 && num<=10;
            }
            static String calc(String input) throws MyExceptions {
                input = input.replaceAll(" ", ""); // Удаление пробелов в строке ввода
                if(input.isEmpty()){
                    throw new MyExceptions("Пустая строка!"); // Проверка на пустую строку ввода
                }

                // Массив типа Строка для определния 1, 2 числа и знака математической операции
                ArrayList<String> matrix = new ArrayList<>();
                for(int i = 0; i < input.length(); ++i){ // Разделение строки на 3 элемента
                    if( operator(input.charAt(i)) ){
                        matrix.add(input.substring(0,i));   // Первое число (num1)
                        matrix.add(input.substring(i,i+1)); // Знак операции между ними
                        matrix.add(input.substring(i+1));   // Второе число (num2)
                        break;
                    }
                }
                //Проверка на некоректный ввод
                if(matrix.isEmpty()){
                    throw new MyExceptions("Ошибка ввода!");
                }

                int num1, num2;  // Переменные для записи чисел после парсинга
                boolean roman = false; // Индикатор римских чисел

                try{
                    // Парсинг первого числа
                    num1 = Integer.parseInt(matrix.get(0));
                    try{
                        // Если получилось, парсинг второго числа
                        num2 = Integer.parseInt(matrix.get(2));
                    }
                    catch (NumberFormatException e){
                        throw new MyExceptions("Ошибка! Недопустимый элемент " + matrix.get(2) + ".");
                    }
                }
                catch (NumberFormatException e){
                    try{
                        num1 = RomanNumbers.toArabic(matrix.get(0)).isNumber(); // 1 римское число
                    }
                    catch (IllegalArgumentException e1){
                        throw new MyExceptions("Ошибка! Недопустимый элемент " + matrix.get(0) + ".");
                    }
                    try{
                        num2 = RomanNumbers.toArabic(matrix.get(2)).isNumber(); // 2 римское число
                    }
                    catch (IllegalArgumentException e1){
                        throw new MyExceptions("Ошибка! Недопустимый элемент " + matrix.get(2) + ".");
                    }
                    roman = true; // Активирован индикатор римских чисел
                }

                // Проверка вводимых чисел на соответствие диапазону 1-10
                if (!validNumber(num1) || !validNumber(num2)) {
                    throw new MyExceptions("Ошибка! Недопустимое значение " + (validNumber(num2) ?
                            (roman ? RomanNumbers.toArabic(num1): num1) :
                            (roman ? RomanNumbers.toArabic(num2): num2)
                    ) + ".");
                }
                else{
                    int result = switch (matrix.get(1).charAt(0)) { // Возможные операции
                        case '+' -> num1 + num2;
                        case '-' -> num1 - num2;
                        case '*' -> num1 * num2;
                        case '/' -> num1 / num2;
                        default -> throw new MyExceptions("Ошибка ввода!");
                    };
                    return roman ? RomanNumbers.toArabic(result).toString() :
                            Integer.toString(result);
                }
            }
        }
        System.out.println("""
                    Возьмите два целых числа в диапазоне 1-10 или I-X
                    и произведите с ними одну из следующих операций: + - * /
                    Поехали:""");
        try{
            System.out.println( Calculator.calc(scan.nextLine()) );
        }catch (MyExceptions ex){
            System.err.println(ex.getMessage()); // Сообщение об ошибке выводится красным
        }
    }

}