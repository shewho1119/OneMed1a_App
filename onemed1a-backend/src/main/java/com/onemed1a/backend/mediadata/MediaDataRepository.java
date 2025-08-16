package com.onemed1a.backend.mediadata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaDataRepository extends JpaRepository<MediaData, Integer> {



}
