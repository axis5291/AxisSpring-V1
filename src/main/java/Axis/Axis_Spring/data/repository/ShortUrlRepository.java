package Axis.Axis_Spring.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Axis.Axis_Spring.data.entity.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {
    ShortUrl findByUrl(String url);

    ShortUrl findByOrgUrl(String originalUrl);
}
