package com.ecristobale.apifirst.apifirstspringboot.repositories;

import com.ecristobale.apifirst.apifirstspringboot.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
}
