package com.newbie.service;

import com.newbie.domain.Customer;

public interface IConsistentHashService {
    String addConstomer();
    String queryCustomerFromMater();
    String queryCustomer();
}
