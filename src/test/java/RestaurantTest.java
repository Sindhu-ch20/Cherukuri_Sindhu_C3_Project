import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    Restaurant restaurant;
    List<String> items;

    @BeforeEach
    public void setup() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        items = new ArrayList<String>();
        items.add("Vegetable lasagne");
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        LocalTime currentTime = LocalTime.parse("12:00:00");
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(currentTime);
        boolean isRestaurantOpen = mockedRestaurant.isRestaurantOpen();
        assertTrue(isRestaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        LocalTime currentTime = LocalTime.parse("09:00:00");
        Restaurant mockedRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(currentTime);
        boolean isRestaurantOpen = mockedRestaurant.isRestaurantOpen();
        assertFalse(isRestaurantOpen);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<<<< Calculating Order Value >>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void calulate_items_cost_on_selected_items_should_return_cost_greater_than_0() {
        int totalCost = restaurant.calculateItemsCost(items);
        assertTrue(totalCost>0);
    }
    @Test
    public void calulate_items_cost_of_selected_items_should_return_cost_269() {
        int totalCost = restaurant.calculateItemsCost(items);
        assertEquals(269,totalCost);
    }

    @Test
    public void calulate_items_cost_with_no_items_selected_should_return_cost_0() {
        List<String> items_ = new ArrayList<String>();
        int totalCost = restaurant.calculateItemsCost(items_);
        assertEquals(0,totalCost);
    }

    @Test
    public void calulate_items_cost_on_selecting_new_item_should_return_cost_388() {
        items.add("Sweet corn soup");
        int totalCost = restaurant.calculateItemsCost(items);
        assertEquals(388,totalCost);
    }

    @Test
    public void calulate_items_cost_on_unselecting_item_should_return_cost_119() {
        items.add("Sweet corn soup");
        items.remove("Vegetable lasagne");
        int totalCost = restaurant.calculateItemsCost(items);
        assertEquals(119,totalCost);
    }

    @Test
    public void calulate_items_cost_on_unselecting_all_item_should_return_cost_0() {
        items.clear();
        int totalCost = restaurant.calculateItemsCost(items);
        assertEquals(0,totalCost);
    }

}