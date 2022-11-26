package com.becommerce.service;

import com.becommerce.model.HomeScreenSchema;

public interface ScreenService {
    HomeScreenSchema getHomeData(String token);
}
