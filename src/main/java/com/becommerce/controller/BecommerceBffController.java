package com.becommerce.controller;

import com.becommerce.api.PetApi;
import com.becommerce.model.ModelApiResponse;
import com.becommerce.model.Pet;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller("/becommerceBff")
public class BecommerceBffController implements PetApi {

    @Get(uri = "/", produces = "text/plain")
    public String index() {
        return "Example Response";
    }

    @Override
    public HttpResponse<Pet> addPet(Pet pet) {
        return HttpResponse.ok(new Pet("", new ArrayList<>()));
    }

    @Override
    public HttpResponse<Void> deletePet(Long petId, String apiKey) {
        return null;
    }

    @Override
    public HttpResponse<List<Pet>> findPetsByStatus(String status) {
        return null;
    }

    @Override
    public HttpResponse<List<Pet>> findPetsByTags(List<String> tags) {
        return null;
    }

    @Override
    public HttpResponse<Pet> getPetById(Long petId) {
        return null;
    }

    @Override
    public HttpResponse<Pet> updatePet(Pet pet) {
        return null;
    }

    @Override
    public HttpResponse<Void> updatePetWithForm(Long petId, String name, String status) {
        return null;
    }

    @Override
    public HttpResponse<ModelApiResponse> uploadFile(Long petId, String additionalMetadata, File _body) {
        return null;
    }
}