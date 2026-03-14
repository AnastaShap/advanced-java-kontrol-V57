package ua.university.order.processor;

import ua.university.order.domain.Money;
import ua.university.order.domain.Order;
import ua.university.order.payment.PaymentMethod;

import java.util.logging.Logger;

public abstract class OrderProcessorTemplate extends AppException{
    private static final Logger logger = Logger.getLogger(OrderProcessorTemplate.class.getName());

    public final void process(Order order, PaymentMethod paymentMethod) {
       try{
           logger.info("Початок обробки замовлення " + order.getId());
           validate(order);
           validateCategoryMix(order);
           Money total = calculate(order);
           paymentMethod.pay(total);

           logger.info("Замовлення успішно завершено");
       }catch (AppException e) {
           logger.warning("Бізнес-помилка: " + e.getMessage());
           throw e;
       } catch (Exception e) {
           logger.log(Level.SEVERE, "Критичний збій!", e);
           throw new OrderInfrastructureException("Системна помилка", e); // Chaining
       }



        finish(order);
    }


}
