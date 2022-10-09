package com.becommerce.service.impl;

import com.becommerce.model.Category;
import com.becommerce.model.Home;
import com.becommerce.model.ModelNew;
import com.becommerce.model.Partner;
import com.becommerce.service.HomeService;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named("HomeServiceImpl")
public class HomeServiceImpl implements HomeService {

    @Override
    public Home getHomepageData(String type) {
        Home home = new Home();

        Category category = new Category();
        category.setName("Alimentos");
        category.setIcon("https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg?cs=srgb&dl=pexels-pixabay-461198.jpg&fm=jpg");
        category.setDeeplink("//alimentos");

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);
        categoryList.add(category);

        ModelNew modelNew = new ModelNew();
        modelNew.setBackgroundImg("https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg?cs=srgb&dl=pexels-pixabay-461198.jpg&fm=jpg");
        modelNew.setPartnerName("BH & Cia Espetinhos");
        modelNew.setPartnerDescription("Maior distribuidor de cocos e produtos de praia da região sudeste");

        List<ModelNew> modelNewList = new ArrayList<>();
        modelNewList.add(modelNew);
        modelNewList.add(modelNew);
        modelNewList.add(modelNew);

        Partner partner = new Partner();
        partner.setIcon("https://images.pexels.com/photos/2059151/pexels-photo-2059151.jpeg?cs=srgb&dl=pexels-jer-chung-2059151.jpg&fm=jpg");
        partner.setName("BH & Cia Espetinhos");
        partner.setAvaliation("4.5");
        partner.setCategory("Alimentos");
        partner.setLocation("Belo Horizonte e região");
        partner.setBackgroundImage("https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg?cs=srgb&dl=pexels-pixabay-461198.jpg&fm=jpg");

        List<Partner> partnerList = new ArrayList<>();
        partnerList.add(partner);
        partnerList.add(partner);
        partnerList.add(partner);

        home.setCategories(categoryList);
        home.setNews(modelNewList);
        home.setPartners(partnerList);

        return home;
    }
}
