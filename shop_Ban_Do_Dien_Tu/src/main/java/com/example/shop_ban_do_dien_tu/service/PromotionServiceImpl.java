package com.example.shop_ban_do_dien_tu.service;

import com.example.shop_ban_do_dien_tu.model.Promotion;
import com.example.shop_ban_do_dien_tu.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements IPromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionServiceImpl(PromotionRepository repo) {
        this.promotionRepository = repo;
    }

    @Override
    public Promotion create(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    @Override
    public List<Promotion> getAll() {
        return promotionRepository.findAll();
    }

    @Override
    public Optional<Promotion> findByCode(String code) {
        return promotionRepository.findByCode(code);
    }

    @Override
    public boolean isValid(Promotion promotion) {
        LocalDateTime now = LocalDateTime.now();
        return promotion.getActive()
                && !now.isBefore(promotion.getStartDate())
                && !now.isAfter(promotion.getEndDate());
    }
}
