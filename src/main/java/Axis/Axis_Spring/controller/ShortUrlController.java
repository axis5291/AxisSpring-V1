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

   
    private final String ACCESS_TOKEN="67335b8ac21db31594d4ec284e2ca620fe435600";

    ShortUrlService shortUrlService;

  @Autowired
  public ShortUrlController(ShortUrlService shortUrlService) {
    this.shortUrlService = shortUrlService;
  }

  @PostMapping()
  public ShortUrlResponseDto generateShortUrl(@RequestParam String originalUrl) {
    System.out.println("입력하신 Url = " + originalUrl);
    LOGGER.info("post방식으로 생성한 토큰 : {}, originalUrl : {}", ACCESS_TOKEN, originalUrl);

    ShortUrlResponseDto shortUrlResponseDto = shortUrlService.getShortUrl(ACCESS_TOKEN, originalUrl);
    LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>오류 테스트 코드" );
    return shortUrlResponseDto;
    
   
  }

  @GetMapping()
  public ShortUrlResponseDto getShortUrl(@RequestParam  String originalUrl) {
    long startTime = System.currentTimeMillis();
    ShortUrlResponseDto shortUrlResponseDto = shortUrlService.getShortUrl(ACCESS_TOKEN, originalUrl);
    long endTime = System.currentTimeMillis();

    LOGGER.info("[조회응답시간 : {}ms", (endTime - startTime));

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