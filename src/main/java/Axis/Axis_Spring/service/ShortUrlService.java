package Axis.Axis_Spring.service;

import Axis.Axis_Spring.data.dto.ShortUrlResponseDto;

public interface ShortUrlService {
    ShortUrlResponseDto getShortUrl(String accessToken, String originalUrl);

    ShortUrlResponseDto generateShortUrl(String accessToken, String originalUrl);
  
    ShortUrlResponseDto updateShortUrl(String accessToken, String originalUrl);
  
    void deleteShortUrl(String url);
}
