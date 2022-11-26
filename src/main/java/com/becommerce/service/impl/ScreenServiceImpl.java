package com.becommerce.service.impl;

import com.becommerce.mapper.Mapper;
import com.becommerce.model.*;
import com.becommerce.repository.CategoryRepository;
import com.becommerce.repository.PartnerRepository;
import com.becommerce.service.ScreenService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@Named("ScreenService")
public class ScreenServiceImpl implements ScreenService {

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private PartnerRepository partnerRepository;

    @Inject
    Mapper mapper;

    @Override
    public HomeScreenSchema getHomeData(String token) {
        //TODO: TOKEN VALIDATION

        List<CategorySchema> categories = mapper.toCategoriesSchema(categoryRepository.findAll());
        List<PartnerSchema> partners = mapper.toPartnersSchema(partnerRepository.findAll());
        List<PartnerModel> partnersFilter = partnerRepository.findWithLimit(3);
        List<NewsSchema> news = mapper.toNewsSchema(partnersFilter);

        return HomeScreenSchema.builder()
                .categories(categories)
                .partners(partners)
                .news(news)
                .build();
    }
}
