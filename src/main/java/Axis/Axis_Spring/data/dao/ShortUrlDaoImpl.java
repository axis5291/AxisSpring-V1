package Axis.Axis_Spring.data.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Axis.Axis_Spring.data.entity.ShortUrlEntity;
import Axis.Axis_Spring.data.repository.ShortUrlRepository;

@Repository
public class ShortUrlDaoImpl implements ShortUrlDAO{
    private final ShortUrlRepository shortUrlRepository;

  @Autowired
  public ShortUrlDaoImpl(ShortUrlRepository shortUrlRepository) {
    this.shortUrlRepository = shortUrlRepository;
  }

  @Override
  public ShortUrlEntity saveShortUrl(ShortUrlEntity shortUrlEntity) {
    ShortUrlEntity foundShortUrl = shortUrlRepository.save(shortUrlEntity);
    return foundShortUrl;
  }

  @Override
  public ShortUrlEntity getShortUrl(String originalUrl) {
    ShortUrlEntity foundShortUrl = shortUrlRepository.findByOrgUrl(originalUrl);
    return foundShortUrl;
  }

  @Override
  public ShortUrlEntity getOriginalUrl(String shortUrl) {
    ShortUrlEntity foundShortUrl = shortUrlRepository.findByShortUrl(shortUrl);
    return foundShortUrl;
  }

  @Override
  public ShortUrlEntity updateShortUrl(ShortUrlEntity newShortUrl) {
    ShortUrlEntity foundShortUrl = shortUrlRepository.findByOrgUrl(newShortUrl.getOrgUrl());

    foundShortUrl.setOrgUrl(newShortUrl.getOrgUrl());

    ShortUrlEntity savedShortUrl = shortUrlRepository.save(foundShortUrl);

    return savedShortUrl;
  }

  @Override
  public void deleteByShortUrl(String shortUrl) {
    ShortUrlEntity foundShortUrl = shortUrlRepository.findByShortUrl(shortUrl);
    shortUrlRepository.delete(foundShortUrl);
  }

  @Override
  public void deleteByOriginalUrl(String originalUrl) {
    ShortUrlEntity foundShortUrl = shortUrlRepository.findByOrgUrl(originalUrl);
    shortUrlRepository.delete(foundShortUrl);
  }
}
