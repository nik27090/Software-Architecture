package com.arch.soft.database.repos;

import com.arch.soft.database.model.order.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
