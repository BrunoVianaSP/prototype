package application.backend.service;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Customer;
import com.stripe.model.Token;

import application.exceptions.StripeException;
@Service
public class StripeService {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(StripeService.class);

    @Autowired
    private String stripeKey;

    /**
     * Creates a Stripe customer and returns the Stripe customer id
     * @param tokenParams The credit card details to obtain a token. These will never be stored in the DB
     * @param customerParams The parameters which identify the customer
     * @return The stripe customer id which can then be used to perform billing operations at a later stage
     * @throws StripeException If an error occurred while interacting with Stripe
     */
    public String createCustomer(Map<String, Object> tokenParams, Map<String, Object> customerParams) {

        Stripe.apiKey = stripeKey;

        String stripeCustomerId = null;
        try {
            Token token = Token.create(tokenParams);
            customerParams.put("source", token.getId());
            Customer customer = Customer.create(customerParams);
            stripeCustomerId = customer.getId();
        } catch (AuthenticationException e) {
            LOG.error("An authentication exception occurred while creating the Stripe customer", e);
            throw new StripeException(e);
        } catch (InvalidRequestException e) {
            LOG.error("An invalid request exception occurred while creating the Stripe customer", e);
            throw new StripeException(e);
        } catch (APIConnectionException e) {
            LOG.error("An API connection exception occurred while creating the Stripe customer", e);
            throw new StripeException(e);
        } catch (CardException e) {
            LOG.error("A Credit Card exception occurred while creating the Stripe customer", e);
            throw new StripeException(e);
        } catch (APIException e) {
            LOG.error("An API exception occurred while creating the Stripe customer", e);
            throw new StripeException(e);
        }
        return stripeCustomerId;
    }
}