package com.crispandchill.config;

import com.crispandchill.model.Category;
import com.crispandchill.model.Product;
import com.crispandchill.repository.CategoryRepository;
import com.crispandchill.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * DataInitializer - Seeds categories and products into MySQL on startup.
 * Only runs if the categories table is empty.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) return; // already seeded

        // ── Categories ────────────────────────────────────────────
        Category burger     = save(new Category(null, "Burger",      "Juicy burgers",          "Images/burger.jpg",       null));
        Category iceCream   = save(new Category(null, "Ice Cream",   "Cool ice creams",         "Images/iice.jpg",         null));
        Category pizza      = save(new Category(null, "Pizza",       "Cheesy pizzas",           "Images/pizza.jpg",        null));
        Category sandwich   = save(new Category(null, "Sandwich",    "Fresh sandwiches",        "Images/sandwich.jpg",     null));
        Category rolls      = save(new Category(null, "Rolls",       "Spicy rolls",             "Images/rolls.jpg",        null));
        Category fries      = save(new Category(null, "French Fries","Golden fries",            "Images/french_fries.jpg", null));
        Category milkshake  = save(new Category(null, "Milkshake",   "Creamy milkshakes",       "Images/milkshake.jpg",    null));
        Category snacks     = save(new Category(null, "Snacks",      "Quick bites",             "Images/snacks.jpg",       null));
        Category cake       = save(new Category(null, "Cake",        "Delicious cakes",         "Images/cake.jpg",         null));
        Category pasta      = save(new Category(null, "Pasta",       "Creamy pasta",            "Images/pasta.jpg",        null));
        Category chocolate  = save(new Category(null, "Chocolate",   "Rich chocolates",         "Images/chocolate.jpg",    null));
        Category paniPuri   = save(new Category(null, "Pani Puri",   "Crispy pani puri & chaat","Images/pani_puri.jpg",    null));

        // ── Products ──────────────────────────────────────────────
        productRepository.saveAll(List.of(
            // Burgers
            p("Veg Burger",          99,  burger,    "Images/veg_burger.jpg"),
            p("Chicken Burger",      149, burger,    "Images/chicken_burger.jpg"),
            p("Cheese Burger",       129, burger,    "Images/cheese_burger.jpg"),
            p("Double Patty Burger", 179, burger,    "Images/double_patty.jpg"),
            p("Mushroom Burger",     159, burger,    "Images/mushroom_burger.jpg"),
            p("Spicy Burger",        139, burger,    "Images/spicy_burger.jpg"),
            // Ice Cream
            p("Strawberry Icecream", 80,  iceCream,  "Images/strawberry.jpg"),
            p("Chocochip Icecream",  90,  iceCream,  "Images/chocochip.jpeg"),
            p("Chocolate Icecream",  85,  iceCream,  "Images/chocolateice.jpeg"),
            p("Vanilla Icecream",    70,  iceCream,  "Images/vanilla.jpg"),
            p("Mint Chocochip",      95,  iceCream,  "Images/mint chocchip.jpeg"),
            p("Rocky Road",          100, iceCream,  "Images/rockyroad.jpeg"),
            p("Cookies & Cream",     110, iceCream,  "Images/cookies&cream.jpeg"),
            p("Lemon Icecream",      75,  iceCream,  "Images/lemon.jpeg"),
            p("Banana Icecream",     85,  iceCream,  "Images/banana.jpeg"),
            p("Blackberry Icecream", 120, iceCream,  "Images/blackberry.jpeg"),
            p("Blueberry Icecream",  130, iceCream,  "Images/blueberry.jpeg"),
            p("Peanut Butter",       140, iceCream,  "Images/peanutbutter.jpeg"),
            p("Caramel Icecream",    125, iceCream,  "Images/caramel.jpeg"),
            p("Cherry Icecream",     110, iceCream,  "Images/cherry.jpeg"),
            p("Pistachio Icecream",  150, iceCream,  "Images/pistachio.webp"),
            // Pizza
            p("Margherita Pizza",    250, pizza,     "Images/margherita.jpg"),
            p("Pepperoni Pizza",     320, pizza,     "Images/pepperoni.jpg"),
            p("Veggie Supreme",      280, pizza,     "Images/veggie.jpg"),
            p("BBQ Chicken Pizza",   350, pizza,     "Images/bbq_chicken.jpg"),
            p("Cheese Burst Pizza",  300, pizza,     "Images/cheese_burst.jpg"),
            p("Paneer Tikka Pizza",  330, pizza,     "Images/paneer_tikka.jpg"),
            // Sandwich
            p("Veg Sandwich",        80,  sandwich,  "Images/veg_sandwich.jpg"),
            p("Grilled Sandwich",    90,  sandwich,  "Images/grilled.jpg"),
            p("Club Sandwich",       120, sandwich,  "Images/club.jpg"),
            p("Paneer Sandwich",     110, sandwich,  "Images/paneer.jpg"),
            p("Chicken Sandwich",    130, sandwich,  "Images/chicken.jpg"),
            p("BLT Sandwich",        150, sandwich,  "Images/blt.jpg"),
            // Rolls
            p("Veg Roll",            90,  rolls,     "Images/veg_roll.jpg"),
            p("Paneer Roll",         120, rolls,     "Images/paneer_roll.jpg"),
            p("Egg Roll",            80,  rolls,     "Images/egg_roll.jpg"),
            p("Chicken Roll",        150, rolls,     "Images/chicken_roll.jpg"),
            p("Kathi Roll",          140, rolls,     "Images/kathi_roll.jpg"),
            // French Fries
            p("Regular Fries",       70,  fries,     "Images/RegularFrenchFries.jpg"),
            p("Cheese Fries",        120, fries,     "Images/CheeseFF.jpg"),
            p("Masala Fries",        90,  fries,     "Images/masala.jpg"),
            p("Curly Fries",         110, fries,     "Images/curlyFF.png"),
            p("Waffle Fries",        130, fries,     "Images/Waffleff.jpg"),
            p("Sweet Potato Fries",  140, fries,     "Images/sweetpotatos.jpg"),
            p("KitKat Fries",        150, fries,     "Images/kitkat.jpg"),
            // Milkshake
            p("Chocolate Milkshake", 120, milkshake, "Images/chocolate_milkshake.jpg"),
            p("Strawberry Milkshake",110, milkshake, "Images/strawberryMS.jpg"),
            p("Vanilla Milkshake",   100, milkshake, "Images/vanilla_milkshake.jpg"),
            p("Banana Milkshake",    115, milkshake, "Images/banana_milkshake.jpg"),
            p("Oreo Milkshake",      130, milkshake, "Images/oreo_milkshake.jpg"),
            p("Mango Milkshake",     140, milkshake, "Images/mango_milkshake.jpg"),
            p("KitKat Milkshake",    150, milkshake, "Images/kitkat_milkshake.jpg"),
            p("Snickers Milkshake",  160, milkshake, "Images/snickers_milkshake.jpg"),
            p("Nutella Milkshake",   170, milkshake, "Images/nutella_milkshake.jpg"),
            // Snacks
            p("Pakora / Bhaji",      50,  snacks,    "Images/pakora.jpg"),
            p("Nachos",              120, snacks,    "Images/nachos.jpg"),
            p("Popcorn",             70,  snacks,    "Images/popcorn.jpg"),
            p("Vada",                40,  snacks,    "Images/vada.jpg"),
            p("Bonda",               35,  snacks,    "Images/bonda.jpg"),
            // Cake
            p("Chocolate Cake",      350, cake,      "Images/chocolatecake.jpg"),
            p("Vanilla Cake",        300, cake,      "Images/vanillacake.jpg"),
            p("Red Velvet Cake",     450, cake,      "Images/red_velvet.jpg"),
            p("Black Forest Cake",   400, cake,      "Images/black_forest.jpg"),
            p("Fruit Cake",          380, cake,      "Images/Fruitcake.png"),
            p("Cheesecake",          500, cake,      "Images/cheesecake.jpg"),
            // Pasta
            p("Alfredo Pasta",       180, pasta,     "Images/Alferdo.jpg"),
            p("White Sauce Pasta",   160, pasta,     "Images/white_sauce_pasta.png"),
            p("Red Sauce Pasta",     150, pasta,     "Images/red_sauce.jpg"),
            p("Macaroni Cheese",     170, pasta,     "Images/Macronni.jpg"),
            p("Veg Pasta",           140, pasta,     "Images/veg_pasta.jpg"),
            p("Chicken Pasta",       190, pasta,     "Images/chicken_pasta.jpg"),
            // Chocolate
            p("Dark Chocolate",      120, chocolate, "Images/dark.jpg"),
            p("Milk Chocolate",      110, chocolate, "Images/milk.jpg"),
            p("White Chocolate",     100, chocolate, "Images/white.jpg"),
            p("Ruby Chocolate",      150, chocolate, "Images/ruby.jpg"),
            p("Chocolate Truffles",  180, chocolate, "Images/truffle.jpg"),
            p("Nut Chocolate Bar",   130, chocolate, "Images/nut.jpg"),
            p("Caramel Chocolate",   140, chocolate, "Images/caramel.jpg"),
            p("Mint Chocolate",      95,  chocolate, "Images/mint.jpg"),
            // Pani Puri
            p("Cheese Bhel",         80,  paniPuri,  "Images/Cheese_Bhel.jpeg"),
            p("Cheese Dabeli",        90,  paniPuri,  "Images/Cheese_Dabeli.jpeg"),
            p("Dahi Puri",            70,  paniPuri,  "Images/Dahi_Puri.jpg"),
            p("Kacchi Dabeli",        60,  paniPuri,  "Images/Kacchi_Dabeli.jpeg"),
            p("Pani Puri Special",    50,  paniPuri,  "Images/Pani.webp"),
            p("Ragda Patties",        90,  paniPuri,  "Images/Ragda_Patties.jpeg"),
            p("Ragda Puri",           75,  paniPuri,  "Images/Ragda_Puri.jpeg"),
            p("Samosa Chat",          85,  paniPuri,  "Images/Samosa_Chat.jpeg"),
            p("Dry Bhel",             60,  paniPuri,  "Images/dry_bhel.jpeg"),
            p("Masala Puri",          55,  paniPuri,  "Images/masala.jpeg"),
            p("Pani Puri Classic",    45,  paniPuri,  "Images/pani_1.jpg"),
            p("Plain Puri Pack",      40,  paniPuri,  "Images/plain.jpg")
        ));

        System.out.println("✅ Database seeded with " + productRepository.count() + " products.");
    }

    private Category save(Category c) {
        return categoryRepository.save(c);
    }

    private Product p(String name, int price, Category cat, String img) {
        Product prod = new Product();
        prod.setProductName(name);
        prod.setPrice(BigDecimal.valueOf(price));
        prod.setCategory(cat);
        prod.setImageUrl(img);
        prod.setIsAvailable(true);
        return prod;
    }
}
