package Axis.Axis_Spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Axis.Axis_Spring.data.dto.ShortUrlResponseDto;
import Axis.Axis_Spring.service.ShortUrlService;

@RestController
@RequestMapping("/api/v1/short-url")
public class ShortUrlController {
    private final Logger LOGGER = LoggerFactory.getLogger(ShortUrlController.class);

    private String CLIENT_ID="ZJtn8I8tSMNBtlUhuF9l4gPmSt5cEbvEaTDREJq31PQ";
    private String CLIENT_SECRET="tWXd8DA35z/XD28NuFNrvNRY4xJTU+v0";

    ShortUrlService shortUrlService;

  @Autowired
  public ShortUrlController(ShortUrlService shortUrlService) {
    this.shortUrlService = shortUrlService;
  }

  @PostMapping()
  public ShortUrlResponseDto generateShortUrl(@RequestParam String originalUrl) {
    LOGGER.info(
        "[generateShortUrl] perform API. CLIENT_ID : {}, CLIENT_SECRET : {}",
        CLIENT_ID,
        CLIENT_SECRET);

    return shortUrlService.generateShortUrl(CLIENT_ID, CLIENT_SECRET, originalUrl);
  }

  @GetMapping()
  public ShortUrlResponseDto getShortUrl(@RequestParam  String originalUrl) {
    long startTime = System.currentTimeMillis();
    ShortUrlResponseDto shortUrlResponseDto = shortUrlService.getShortUrl(CLIENT_ID, CLIENT_SECRET, originalUrl);
    long endTime = System.currentTimeMillis();

    LOGGER.info("[getShortUrl] response Time : {}ms", (endTime - startTime));

    return shortUrlResponseDto;
  }

  @PutMapping("/")
  public ShortUrlResponseDto updateShortUrl(@RequestParam  String originalUrl) {
    return null;
  }

  @DeleteMapping("/")
  public ResponseEntity<String> deleteShortUrl(@RequestParam  String url) {
    try {
      shortUrlService.deleteShortUrl(url);
    } catch (RuntimeException e) {
      e.printStackTrace();
    }

    return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
  }


}