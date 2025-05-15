package Axis.Axis_Spring.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Axis.Axis_Spring.data.entity.ShortUrlEntity;

public interface ShortUrlRepository extends JpaRepository<ShortUrlEntity, String> {
    ShortUrlEntity findByShortUrl(String url);

    ShortUrlEntity findByOrgUrl(String originalUrl);
}
