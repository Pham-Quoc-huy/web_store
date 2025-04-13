package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.Promotion;

import java.util.List;
import java.util.Optional;

public interface IPromotionService {
    Promotion create(Promotion promotion);
    List<Promotion> getAll();
    Optional<Promotion> findByCode(String code);
    boolean isValid(Promotion promotion);
}
