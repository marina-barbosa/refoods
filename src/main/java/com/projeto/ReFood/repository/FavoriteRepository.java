package com.projeto.ReFood.repository;

import com.projeto.ReFood.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
