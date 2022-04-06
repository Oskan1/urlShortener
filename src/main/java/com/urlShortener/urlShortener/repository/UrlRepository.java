package com.urlShortener.urlShortener.repository;

import com.urlShortener.urlShortener.entity.UrlShort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Repository

public interface UrlRepository extends JpaRepository<UrlShort, Long> {

    UrlShort findByShortUrl(String shortUrl);
    void deleteByTimestampLessThan(Date date);
    public List<UrlShort> findAllByOrderByTimestampDesc();
    @Transactional
    @Modifying
    void deleteAllByTimestampLessThanEqual(Date now);

}
