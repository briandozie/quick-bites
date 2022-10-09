package curtin.edu.my.quickbites;

import java.util.ArrayList;

public class Data
{
    public static ArrayList<Restaurant> getAllRestaurant()
    {
        ArrayList<Restaurant> list = new ArrayList<>();

        list.add(new Restaurant(100, "KFC", "kfc_logo.png", "Senadin"));
        list.add(new Restaurant(101, "McDonalds", "mcdonalds_logo.png", "Pujut"));
        list.add(new Restaurant(102, "Wendy's", "wendys_logo.png", "Lutong"));
        list.add(new Restaurant(103, "Burger King", "burger_king_logo.png", "Kuala Baram"));
        list.add(new Restaurant(104, "SugarBun", "sugarbun_logo.png", "Piasau"));
        list.add(new Restaurant(105, "Pezzo", "pezzo_logo.png", "Boulevard"));
        list.add(new Restaurant(106, "Sushi King", "sushi_king_logo.png", "Permyjaya"));
        list.add(new Restaurant(107, "Domino's Pizza", "dominos_logo.png", "Bintang Plaza"));
        list.add(new Restaurant(108, "The Chicken Rice Shop", "tcrs_logo.png", "Permyjaya"));
        list.add(new Restaurant(109, "Nando's", "nandos_logo.png", "Marina"));
        list.add(new Restaurant(110, "Subway", "subway_logo.png", "Senadin"));

        return list;
    }

    public static ArrayList<Food> getAllFood()
    {
        ArrayList<Food> list = new ArrayList<>();

        // food for restaurant ID 100
        list.add(new Food(251, 100, "Snek Jimat Kombo B", 9.99,
                "Simple and delicious snack combo with a choice of drink", "snek_jimat_kombo_b.png"));
        list.add(new Food(252, 100, "6-pc Bucket Bersama", 44.99,
                "Bucket filled with deliciously friend chicken", "six_pc_bucket_bersama.png"));
        list.add(new Food(253, 100, "6-pc WoW Bucket", 44.99,
                "Bucket filled with a WoW surprise of delicacies.", "six_pc_wow_bucket.png"));
        list.add(new Food(254, 100, "KFC x Lay's Crunch Box Meal", 19.99,
                "Prepare for an absolutely delicious and crunchy meal with Lay's!", "lays_crunch_box_meal.png"));
        list.add(new Food(255, 100, "5-pc Combo", 12.50,
                "The classic tested and orignal KFC chicken recipe.", "five_pc_combo.png"));
        list.add(new Food(255, 100, "Loaded Cheezy Fries", 39.99,
                "A load of unbelievably tasty and cheezy fries!", "loaded_cheezy_fries.png"));

        // food for restaurant ID 101
        list.add(new Food(201, 101, "Big Mac", 12.50,
                "Two all-beef patties with lettuce, onions, pickles, cheese and special sauce.", "big_mac.png"));
        list.add(new Food(202, 101, "French Fries", 3.99,
                "Fluffy inside, crispy outside taste of our world-famous fries.", "french_fries.png"));
        list.add(new Food(203, 101, "Happy Meal: Chicken Burger", 10.99,
                "Your favorite chicken burger cooked to golden perfection", "hm_chicken_burger.png"));
        list.add(new Food(204, 101, "Hotcakes", 3.49,
                "Fluffy and light Hotcakes served with syrup and margarine.", "hotcakes.png"));
        list.add(new Food(205, 101, "Sausage McMuffin", 6.99,
                "Chicken sausage and cheese tucked in a freshly toasted English Muffin.", "sausage_mcmuffin.png"));
        list.add(new Food(205, 101, "Double Quarter Pounder with Cheese", 20.99,
                "Prepare for an extremely cheezy and delicious adventure!", "double_quarter_pounder_with_cheese.png"));

        // food for restaurant ID 102
        list.add(new Food(211, 102, "Breakfast Baconator Combo", 8.99,
                "Grilled sausage, American cheese, Applewood smoked bacon, and a fresh-cracked free range egg", "breakfast_baconator_combo.png"));
        list.add(new Food(212, 102, "Cheese Fries", 4.99,
                "Natural-cut, skin-on, sea-salted fries served hot and crispy.", "cheese_fries.png"));
        list.add(new Food(213, 102, "Taco Salad", 4.50,
                "Made fresh daily, all topped with a Pomegranate Vinaigrette Dressing.", "taco_salad.png"));
        list.add(new Food(214, 102, "Spicy Chicken Sandwich", 8.99,
                "A juicy chicken breast marinated and breaded in our unique, fiery blend of peppers and spices", "spicy_chicken_sandwich.png"));
        list.add(new Food(215, 102, "Bourbon Bacon Cheeseburger Double", 12.50,
                "Fresh British beef, Applewood smoked bacon, American cheese, crisp lettuce, tomato, and mayo.", "bourbon_bacon_cheeseburger_double.png"));
        list.add(new Food(215, 102, "Dave's Combo", 9.99,
                "Just the way Dave intended.", "daves_combo.png"));

        // food for restaurant ID 103
        list.add(new Food(206, 103, "Onion Rings", 4.99,
                "Perfect snack on its own or with a meal.", "onion_rings.jpg"));
        list.add(new Food(207, 103, "Mushroom Veggie Burger", 12.50,
                "Here's an option for our Veggie-loving friends!  It's like a party in your mouth!", "mushroom_veggie_burger.jpg"));
        list.add(new Food(208, 103, "Fish'N Crisp", 9.99,
                "Score a catch of marvelous flavors of your tastebuds with this delightful burger.", "fish_n_crisp.jpg"));
        list.add(new Food(209, 103, "Single Mushroom Swiss", 11.99,
                "some nice food description here haha xd", "single_mushroom_swiss.png"));
        list.add(new Food(210, 103, "Double BBQ Beefacon", 13.99,
                "Double it up...Double the taste!!", "double_bbq_beefacon.png"));
        list.add(new Food(210, 103, "Whopper with Cheese", 14.50,
                "All the great taste of the WHOPPER with cheese. A truly cheesillicious WHOPPIN' Time.", "whopper_with_cheese.png"));

        // food for restaurant ID 104
        list.add(new Food(216, 104, "Chickies", 3.99,
                "Deliciously fried crispy chicken wings!", "chickies.png"));
        list.add(new Food(217, 104, "Double Swinger", 12.50,
                "Prepare for an absolute feast of a double patty burger!", "double_swinger.png"));
        list.add(new Food(218, 104, "Fish & Rice", 8.99,
                "Nice and simple rice dish served with freshly fried fish", "fish_and_rice.png"));
        list.add(new Food(219, 104, "Sambal Eco Fish", 7.50,
                "Nothing beats fish served with out special sambal sauce!", "sambal_eco_fish.png"));
        list.add(new Food(220, 104, "Nasi Lemak", 9.50,
                "The classic and traditional dish that everyone loves!", "nasi_lemak.png"));
        list.add(new Food(220, 104, "Beef Stew", 15.00,
                "Absolutely delicious and scrumptious beef stew!", "beef_stew.png"));

        // food for restaurant ID 105
        list.add(new Food(221, 105, "BBQ Bonanza", 14.99,
                "Red Onions, Mozzarella Cheese, topped with BBQ Sauce", "bbq_bonanza.png"));
        list.add(new Food(222, 105, "Big Daddy", 19.99,
                "Minced Beef, Turkey Ham, Italian Beef Sausages, Mozzarella Cheese with a dash of BBQ Sauce", "big_daddy.png"));
        list.add(new Food(223, 105, "Cheesy Cheese", 17.50,
                "Mozzarella, Parmesan Cheese", "cheesy_cheese.png"));
        list.add(new Food(224, 105, "Hola Hawaiian", 13.99,
                "Turkey Ham, Turkey Bacon, Sweet Pineapples, Mozzarella Cheese", "hola_hawaiian.png"));
        list.add(new Food(225, 105, "Pepperoni Party", 14.99,
                "Extra Beef Pepperoni with Mozzarella Cheese", "pepperoni_party.png"));
        list.add(new Food(225, 105, "Very Veggie", 12.99,
                "Mushrooms, Green Peppers, Sweet Pineapples, Diced Tomatoes", "very_veggie.png"));

        // food for restaurant ID 106
        list.add(new Food(226, 106, "Oyako Don", 4.99,
                "Rice with chicken, egg and onions", "oyako_don.jpg"));
        list.add(new Food(227, 106, "Ebi Tenpura", 2.99,
                "Deep-fried prawns served with tempura gravy", "ebi_tenpura.jpg"));
        list.add(new Food(228, 106, "Cheesy Takoyaki", 5.99,
                "Crunchy texture outside and tender soft inside.", "cheesy_takoyaki.jpg"));
        list.add(new Food(229, 106, "Karaage Egg Salad", 3.50,
                "Indulge in a healthy, satisfying salad.", "karaage_egg_salad.jpg"));
        list.add(new Food(230, 106, "Norwegian Salmon Sashimi", 8.50,
                "Fresh raw seafood, sliced to perfection.", "norwegian_salmon_sashimi.jpg"));
        list.add(new Food(230, 106, "Soft Shell Crab Temaki", 7.99,
                "Hand roll of seaweed wrapped with vinegared rice and fresh, choice ingredients.", "soft_shell_crab_temaki.jpg"));

        // food for restaurant ID 107
        list.add(new Food(231, 107, "Prawn Sensation", 12.50,
                "Succulent Prawns marinated in Italian herbs & spices.", "prawn_sensation.png"));
        list.add(new Food(232, 107, "Meatasaurus", 13.50,
                "Generous portions of everyone's favorite beef pepperoni", "meatasaurus.png"));
        list.add(new Food(233, 107, "Signature Quattro Chic", 16.50,
                "Loaded with mushrooms for extra flavor.", "signature_quattro_chic.png"));
        list.add(new Food(234, 107, "Ultimate Hawaiian", 14.50,
                "With shredded chicken, juicy pineapples and fresh mushrooms.", "ultimate_hawaiian.png"));
        list.add(new Food(235, 107, "Sambal Surf & Turf", 14.99,
                "Topped with new and authentic sambal sauce.", "sambal_surf_and_turf.png"));
        list.add(new Food(235, 107, "Chickensaurus", 14.50,
                "Topped with awesome Smoky Blended BBQ Sauce.", "chickensaurus.png"));

        // food for restaurant ID 108
        list.add(new Food(236, 108, "Crispy Wanton", 13.99,
                "Delicious and crispy treats.", "crispy_wanton.jpg"));
        list.add(new Food(237, 108, "Ladies Fingers", 7.99,
                "Scrumptious and healthy addition.", "ladies_fingers.jpg"));
        list.add(new Food(238, 108, "Curry Laksa", 11.50,
                "Spicy and tasty curry thats second to none.", "curry_laksa.jpg"));
        list.add(new Food(239, 108, "Sliced Chicken Noodles", 13.00,
                "Delicately sliced chicken mixed with fresh noodles", "sliced_chicken_noodles.jpg"));
        list.add(new Food(240, 108, "Crispy Roast Chicken", 8.99,
                "Rice with chicken that's roasted perfectly to a crisp", "crispy_roast_chicken.jpg"));
        list.add(new Food(240, 108, "Steamed Chicken", 7.99,
                "Nice and simple tasty steamed chicken with rice.", "steamed_chicken.jpg"));

        // food for restaurant ID 109
        list.add(new Food(241, 109, "Saucy Chicken Bowl", 12.50,
                "Available with Mediterranean Rice or Port Roll.", "saucy_chicken_bowl.png"));
        list.add(new Food(242, 109, "Chicken Butterfly", 12.50,
                "Succulent chicken breasts joined by crispy skin.", "chicken_butterfly.png"));
        list.add(new Food(243, 109, "Whole Chicken", 12.50,
                "Perfectly grilled whole chicken.", "whole_chicken.jpg"));
        list.add(new Food(244, 109, "Wing Roulette", 12.50,
                "Drumettes randomly spiced in all our PERi-PERi flavours.", "wing_roulette.jpg"));
        list.add(new Food(245, 109, "Veggie Carnival Bowl", 12.50,
                "Delicious and healthy meal with Spicy Rice.", "veggie_carnival_bowl.jpg"));
        list.add(new Food(245, 109, "Family Platter", 12.50,
                "A tasty meal for the whole family.", "family_platter.jpg"));

        // food for restaurant ID 110
        list.add(new Food(246, 110, "Seafood & Crab", 9.50,
                "Sweet, tender chunks of seafood blended with creamy light mayonnaise.", "seafood_and_crab.jpg"));
        list.add(new Food(247, 110, "Turkey Breast & Chicken Slice", 7.50,
                "Delicious sliced turkey breast and chicken slice with your choice of fresh vegetables.", "turkey_breast_and_chicken_slice.jpg"));
        list.add(new Food(248, 110, "Tuna", 8.99,
                "Tuna is blessed with creamy mayonnaise and making it a lovely blend.", "tuna.jpg"));
        list.add(new Food(249, 110, "Roast Beef", 6.60,
                " comprises of lean and tender sliced roast beef with your choice of crisp vegetables.", "roast_beef.jpg"));
        list.add(new Food(250, 110, "Italian B.M.T.", 10.50,
                "World favourite sandwich that is made up of sliced salami, pepperoni , sliced chicken.", "italian_bmt.jpg"));
        list.add(new Food(250, 110, "Chicken Teriyaki", 5.00,
                "Flavorful blend of tender chicken pieces dressed lightly with teriyaki sauce.",
                "chicken_teriyaki.jpg"));

        return list;
    }



}
