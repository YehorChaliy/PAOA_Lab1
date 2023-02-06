import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    static Scanner input = new Scanner(System.in);
    static int dimension;

    static int choiceDimension() throws Exception {
        int result = 0;
        result = input.nextInt();
        if (result != 1 && result != 2 && result != 3) {
            System.out.println("Розмірність повинна дорівнювати: 1, 2 або 3");
            throw new Exception();
        }
        return result;
    }

    static class Point {

        List<Integer> coordinates = new ArrayList();

        Point() {}

        void print() {
            System.out.print("(");
            for (int i = 0; i < coordinates.size(); i++) {
                System.out.print(coordinates.get(i) + ";");
            }
            System.out.print(")");
        }

        public String toString() {
            StringBuilder string = new StringBuilder("(");
            for (int i = 0; i < coordinates.size(); i++) {
                string.append(coordinates.get(i)).append(";");
            }
            string.append(")");
            return string.toString();
        }
    }

    static List<Point> enterPoints() throws Exception {
        System.out.println("Задайте кількість точок:");
        int n = input.nextInt();
        System.out.println("Ввести точки вручну? Так-(1); Ні-(0):");
        int random = input.nextInt();
        List<Point> list = new ArrayList<Point>();
        for (int i = 0; i < n; i++) {
            Point point = new Point();
            for (int j = 0; j < dimension; j++) {
                int coordinate = 0;
                if (random == 0) {
                    coordinate = ThreadLocalRandom.current().nextInt(-80, 81);
                } else if (random == 1) {
                    System.out.print(i + 1 + ") " + (char) (88 + j) + ":");
                    coordinate = input.nextInt();
                    if (coordinate < -80 || coordinate > 80) {
                        System.out.println("Координати мають бути в межах [-80; 80]");
                        throw new Exception();
                    }
                } else {
                    System.out.println("Треба вибрати: Так-(1) або Ні-(0)!");
                    throw new Exception();
                }
                point.coordinates.add(coordinate);
            }
            list.add(point);
            point.print();
            System.out.println();
        }
        return list;
    }

    static double findDistance(Point point1, Point point2) {
        double res_pow = 0;
        for (int i = 0; i < dimension; i++) {
            res_pow += Math.pow(point1.coordinates.get(i) - point2.coordinates.get(i), 2);
        }
        return Math.sqrt(res_pow);
    }

    static Result findMaximumDistance(List<Point> list) {
        double maxDistance = 0;
        List<Point> firstPoint = new ArrayList();
        List<Point> secondPoint = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (findDistance(list.get(i), list.get(j)) > maxDistance) {
                    maxDistance = findDistance(list.get(i), list.get(j));
                    firstPoint.clear();
                    secondPoint.clear();
                    firstPoint.add(list.get(i));
                    secondPoint.add(list.get(j));
                } else if (findDistance(list.get(i), list.get(j)) == maxDistance) {
                    firstPoint.add(list.get(i));
                    secondPoint.add(list.get(j));
                }
            }
        }
        return new Result(firstPoint, secondPoint, maxDistance);
    }

    static Result findMinimumDistance(List<Point> list) {
        double minDistance = Integer.MAX_VALUE;
        List<Point> firstPoint = new ArrayList();
        List<Point> secondPoint = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (findDistance(list.get(i), list.get(j)) < minDistance) {
                    minDistance = findDistance(list.get(i), list.get(j));
                    firstPoint.clear();
                    secondPoint.clear();
                    firstPoint.add(list.get(i));
                    secondPoint.add(list.get(j));
                } else if (findDistance(list.get(i), list.get(j)) == minDistance) {
                    firstPoint.add(list.get(i));
                    secondPoint.add(list.get(j));
                }
            }
        }
        return new Result(firstPoint, secondPoint, minDistance);
    }

    static class Result {

        double distance;
        List<Point> allFirstPoints = new ArrayList();
        List<Point> allSecondPoints = new ArrayList();

        Result(List<Point> allFirstPoints, List<Point> allSecondPoints, double distance) {
            this.allFirstPoints.addAll(allFirstPoints);
            this.allSecondPoints.addAll(allSecondPoints);
            this.distance = distance;
        }

        void print() {
            for (int i = 0; i < allFirstPoints.size(); i++) {
                System.out.print("Точки: ");
                allFirstPoints.get(i).print();
                System.out.print(" та ");
                allSecondPoints.get(i).print();
                System.out.print(" - відстань: " + distance);
                System.out.println();
            }
        }
    }

    static Result maximumOrMinimum(List<Point> list) throws Exception {
        System.out.println("Знайти Максимальну-(1) або Мінімальну-(0) відстань:");
        int choice = input.nextInt();
        if (choice == 1) {
            return findMaximumDistance(list);
        } else if (choice == 0) {
            return findMinimumDistance(list);
        } else {
            System.out.println("Треба вибрати: Так-(1) або Ні-(0)!");
            throw new Exception();
        }
    }

   /* static void menu() {

        System.out.println("1.Вказати розмірність масиву");
        System.out.println("2.Ввести точки");
        System.out.println("3.Знайти максимальну/мінімальну відстань");
        System.out.println("4.Відобразити графічно");
        System.out.println("5.Вийти з програми");

    }*/

    public static void main(String[] args) {
        System.out.println("*********************************");
        try {
            System.out.println(" ");
            System.out.println("Вкажіть розмірність:");
            dimension = choiceDimension();
            List<Point> list = enterPoints();
            Result result = maximumOrMinimum(list);
            result.print();
            System.out.println(" ");
            System.out.println("Відобразити графічно? Так-(1); Ні-(0):");
            int temp = input.nextInt();
            if (temp == 1 && dimension == 1 || dimension == 2) {
                new Graphica(list, result, dimension);
            }
            else if (temp == 1 && dimension != 1 && dimension != 2){
                System.out.println("Графічний інтерфейс доступний лише для одно-/двовимірного просторів!");
                throw new Exception();
            }
        } catch (Exception exception) {
            System.out.println("Error!");
        }
        System.out.println("*********************************");
    }
}


