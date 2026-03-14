import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ua.university.order.domain.*;
import ua.university.order.payment.PayPalPayment;
import ua.university.order.exception.*;
import ua.university.order.domain.Order;
import ua.university.order.exception.AppException;
import ua.university.order.exception.PaymentException;
import ua.university.order.payment.CardPayment;
import ua.university.order.processor.DefaultOrderProcessor;
import ua.university.order.processor.OrderProcessorTemplate;

import static org.junit.jupiter.api.Assertions.*;

public class OrderProcessorTest {
    private OrderProcessorTemplate processor;
    private final OrderItem item1 =
            new OrderItem("Phone", "Electronics", 1, new Money(1000));

    private final OrderItem item2 =
            new OrderItem("Book", "Education", 1, new Money(200));

    private final OrderItem item3 =
            new OrderItem("Pen", "Office", 1, new Money(50));

    @BeforeEach
    void setUp() {
        processor = new DefaultOrderProcessor(new CardPayment());
    }

    // 1 POSITIVE
    @Test
    void shouldProcessOrderSuccessfully() {

        Order order = new Order("ORD-1", new OrderItem[]{item1, item2});

        assertDoesNotThrow(() -> processor.process(order));

        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    // 2 NEGATIVE
    @Test
    void shouldFailWhenLessThanTwoItems() {

        Order order = new Order("ORD-2", new OrderItem[]{item1});

        assertThrows(AppException.class,
                () -> processor.process(order));
    }

    // 3 NEGATIVE
    @Test
    void shouldThrowCategoryMixException() {

        OrderItem sameCategory1 =
                new OrderItem("Phone", "Electronics", 1, new Money(100));

        OrderItem sameCategory2 =
                new OrderItem("Tablet", "Electronics", 1, new Money(200));

        Order order = new Order("ORD-3",
                new OrderItem[]{sameCategory1, sameCategory2});

        assertThrows(CategoryMixException.class,
                () -> processor.process(order));
    }

    // 4 NEGATIVE
    @Test
    void shouldPreventDoublePayment() {

        Order order = new Order("ORD-4", new OrderItem[]{item1, item2});

        processor.process(order);

        assertThrows(AppException.class,
                () -> processor.process(order));
    }

    // 5 NEGATIVE
    @Test
    void shouldFailCardLimit() {

        OrderItem expensive =
                new OrderItem("Gaming PC", "Electronics", 1, new Money(40000));

        Order order = new Order("ORD-5",
                new OrderItem[]{expensive, item2});

        assertThrows(PaymentException.class,
                () -> processor.process(order));
    }

    // 6 POSITIVE
    @Test
    void shouldApplyDiscountForThreeCategories() {

        Order order =
                new Order("ORD-6",
                        new OrderItem[]{item1, item2, item3});

        assertDoesNotThrow(() -> processor.process(order));
    }
    @Test
    void shouldHandleNullItems() {

        assertThrows(Exception.class,
                () -> new Order("ORD-8", null));
    }
    @ParameterizedTest
    @ValueSource(doubles = {50, 100, 299})
    void paypalShouldRejectSmallPayments(double value) {

        processor = new DefaultOrderProcessor(new PayPalPayment());

        OrderItem cheap = new OrderItem("Toy", "Electronics", 1, new Money(value));

        OrderItem item2 = new OrderItem("Book", "Education", 1, new Money(200));

        OrderItem item3 = new OrderItem("Pen", "Office", 1, new Money(50));

        Order order =
                new Order("ORD-P", new OrderItem[]{cheap, item2, item3});

        assertThrows(PaymentException.class,
                () -> processor.process(order));
    }


    //  OPTIONAL API
   /*@Test
    void shouldReturnOptionalEmpty() {

        OrderRepository repo = new InMemoryOrderRepository();

        Optional<Order> result = repo.findById("missing");

        assertTrue(result.isEmpty());
    }*/


}
