package com.batchprocessing.customerupdate.configuration;

import com.batchprocessing.customerupdate.models.Customer;
import com.batchprocessing.customerupdate.models.User;
import org.springframework.batch.item.ItemProcessor;

public class CustomerProcessor implements ItemProcessor<User, Customer> {

        @Override
        public Customer process(User usage) {
            Double billAmount = usage.getDataUsage() * .001 + usage.getMinutes() * .01;
            return new Customer(usage.getId(), usage.getFirstName(), usage.getLastName(),
                    usage.getDataUsage(), usage.getMinutes(), billAmount);
        }
}
