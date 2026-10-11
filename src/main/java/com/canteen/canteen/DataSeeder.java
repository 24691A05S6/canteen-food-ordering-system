package com.canteen.canteen;

import com.canteen.canteen.entity.FoodItem;
import com.canteen.canteen.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Override
    public void run(String... args) throws Exception {
        if (foodItemRepository.count() == 0) {
            foodItemRepository.save(new FoodItem(
                    "Veg Fried Rice",
                    "Stir-fried rice with mixed vegetables and soy sauce",
                    80.0,
                    "Main Course",
                    "https://images.unsplash.com/photo-1512058564366-18510be2db19",
                    true
            ));

            foodItemRepository.save(new FoodItem(
                    "Chicken Biryani",
                    "Aromatic basmati rice cooked with spiced chicken",
                    120.0,
                    "Main Course",
                    "https://images.unsplash.com/photo-1563379091339-03246963d96c",
                    true
            ));

            foodItemRepository.save(new FoodItem(
                    "Masala Dosa",
                    "Crispy rice crepe filled with spiced potato masala",
                    50.0,
                    "South Indian",
                    "https://images.unsplash.com/photo-1668236543090-82eba5ee5976",
                    true
            ));

            foodItemRepository.save(new FoodItem(
                    "Idly",
                    "Steamed rice cakes served with chutney and sambar",
                    30.0,
                    "South Indian",
                    "https://images.unsplash.com/photo-1589301760014-d929f3979dbc",
                    true
            ));

            foodItemRepository.save(new FoodItem(
                    "Vada",
                    "Crispy fried lentil doughnuts served with chutney",
                    25.0,
                    "South Indian",
                    "https://images.unsplash.com/photo-1626132647523-66f5bf380027",
                    true
            ));

            foodItemRepository.save(new FoodItem(
                    "Veg Noodles",
                    "Stir-fried noodles tossed with fresh vegetables",
                    70.0,
                    "Main Course",
                    "https://images.unsplash.com/photo-1585032226651-759b368d7246",
                    true
            ));

            foodItemRepository.save(new FoodItem(
                    "Samosa",
                    "Crispy pastry filled with spiced potatoes and peas",
                    20.0,
                    "Snacks",
                    "https://images.unsplash.com/photo-1601050690597-df0568f70950",
                    true
            ));

            foodItemRepository.save(new FoodItem(
                    "Tea",
                    "Freshly brewed Indian masala chai",
                    15.0,
                    "Beverages",
                    "https://images.unsplash.com/photo-1571934811356-5cc061b6821f",
                    true
            ));

            foodItemRepository.save(new FoodItem(
                    "Coffee",
                    "Hot filter coffee, South Indian style",
                    20.0,
                    "Beverages",
                    "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085",
                    true
            ));

            foodItemRepository.save(new FoodItem(
                    "Fresh Juice",
                    "Seasonal fresh fruit juice, no added sugar",
                    40.0,
                    "Beverages",
                    "https://images.unsplash.com/photo-1610970881699-44a5587cabec",
                    true
            ));

            System.out.println("Sample food data inserted successfully!");
        }
    }
}