package com.example.advquerying;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    private final Scanner scan = new Scanner(System.in);
    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    public CommandLineRunnerImpl(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Enter exercise number:");
        String input= scan.nextLine();
        while (input!="End") {
            try{
                int num= Integer.parseInt(input);
                switch (num) {
                    case 1: exerciseOne();
                    case 2: exerciseTwo();
                    case 3: exerciseThree();
                    case 4: exerciseFour();
                    case 5: exerciseFive();
                    case 6: exerciseSix();
                    case 7: exerciseSeven();
                    case 8: exerciseEight();
                    case 9: exerciseNine();
                    case 10: exerciseTen();
                    case 11: exerciseEleven();
                }
            } catch (Exception e) {
                System.out.println("Incorrect input!");
            }
            finally {
                System.out.println();
                System.out.println("Enter exercise number:");
                input= scan.nextLine();
            }
        }
    }

    private void exerciseOne() {
        System.out.println("Please enter shampoo size (SMALL, MEDIUM, LARGE):");
        Size size= Size.valueOf(scan.nextLine());
        List<Shampoo> allShampoosBySizeOrderById = this.shampooService.getAllShampoosBySizeOrderById(size);
        allShampoosBySizeOrderById.forEach(shampoo -> System.out.printf("%s %s %.2f%n",shampoo.getBrand(),shampoo.getSize(),shampoo.getPrice()));

    }

    private void exerciseTwo() {
        System.out.println("Please, enter shampoo size (SMALL, MEDIUM, LARGE) and shampoo id:");
        Size size=Size.valueOf(scan.nextLine());
        Long labelId=Long.parseLong(scan.nextLine());
        this.shampooService.getAllShampoosBySizeOrLabelId(size,labelId)
                .forEach(shampoo -> System.out.printf("%s %s %.2f%n",shampoo.getBrand(),shampoo.getSize(),shampoo.getPrice()));
    }

    private void exerciseThree() {
        System.out.println("Please enter min price:");
        BigDecimal price=new BigDecimal(scan.nextLine());
        this.shampooService.getAllShampoosWithPriceGreaterThanOrderByPrice(price)
                .forEach(shampoo -> System.out.printf("%s %s %.2f%n",shampoo.getBrand(),shampoo.getSize(),shampoo.getPrice()));
    }

    private void exerciseFour() {
        System.out.println("Please enter starting string:");
        String input=scan.nextLine();
        this.ingredientService.getAllIngredientsByNameStartingWithString(input)
                .forEach(ingredient -> System.out.printf("%s%n",ingredient.getName()));
    }

    private void exerciseFive() {
        System.out.println("Please enter ingredients:");
        List<String> inputs=new ArrayList<>();
        while(true) {
                String line = scan.nextLine();
                if (line.length()==0){
                break;
                    }
                inputs.add(line);
        }
        this.ingredientService.getAllIngredientsByNameIn(inputs)
                .forEach(ingredient -> System.out.printf("%s%n",ingredient.getName()));
    }

    private void exerciseSix() {
        System.out.println("Please, enter max price:");
        BigDecimal price=new BigDecimal(scan.nextLine());
        System.out.println(this.shampooService.getCountOfShampoosWithPriceLowerThanTheGiven(price));
    }

    private void exerciseSeven() {
        System.out.println("Please, enter ingredients:");
        List<String> ingredientsNames=new ArrayList<>();
        while(true) {
            String line=scan.nextLine();
            if(line.length()==0) {
                break;
            }
            ingredientsNames.add(line);
        }
        this.shampooService.getAllShampoosByIngredientsNames(ingredientsNames)
                .forEach(shampoo -> System.out.printf("%s%n",shampoo.getBrand()));
    }

    private void exerciseEight() {
        System.out.println("Please, enter ingredients count:");
        Long count= Long.parseLong(scan.nextLine());
        this.shampooService.getAllShampoosWithCountOfIngredientsLessThanTheGiven(count)
                .forEach(shampoo -> System.out.printf("%s%n",shampoo.getBrand()));
    }

    private void exerciseNine() {
        System.out.println("Please enter ingredient name:");
        String name=scan.nextLine();
        this.ingredientService.deleteIngredientByName(name);
    }

    private void exerciseTen() {
        this.ingredientService.increaseAllIngredientPrices();
    }

    private void exerciseEleven() {
        System.out.println("Please, enter ingredients names in format 'i1, i2, i3':");
        List<String> ingredientsNames = Arrays.stream(scan.nextLine().split(", ")).collect(Collectors.toList());
        this.ingredientService.increasePricesByGivenName(ingredientsNames);
    }
}
