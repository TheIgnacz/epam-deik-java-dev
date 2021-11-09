package com.epam.training.webshop.domain.order.orderconfirm.impl;

import com.epam.training.webshop.domain.order.model.Cart;
import com.epam.training.webshop.domain.order.orderconfirm.OrderConfirmationService;
import com.epam.training.webshop.domain.order.orderconfirm.lib.ConfirmationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailConfirmationAdapter implements OrderConfirmationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConfirmationAdapter.class);

    @Autowired
    private ConfirmationService emailConfirmationService;

    @Override
    public void notify(final Cart cart) {
        LOGGER.info("Order confirmation, email adapter, cart: {}", cart);
        sendOrderConfirmation(cart);

    }

    @Override
    public void sendOrderConfirmation(final Cart cart) {
        emailConfirmationService.sendConfirmationMessageAbout(cart.getProducts());
    }
}