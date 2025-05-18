package Axis.Axis_Spring.service;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import Axis.Axis_Spring.data.dao.ShortUrlDAO;
import Axis.Axis_Spring.data.dto.BitlyUriDto;
import Axis.Axis_Spring.data.dto.ShortUrlResponseDto;
import Axis.Axis_Spring.data.entity.ShortUrlEntity;
import Axis.Axis_Spring.data.repository.ShortUrlRedisRepository;

@Service
public class ShortUrlServiceImpl implements ShortUrlService{

  
  private final Logger LOGGER = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

  private final ShortUrlDAO shortUrlDAO;
  private final ShortUrlRedisRepository shortUrlRedisRepository;

  @Autowired
  public ShortUrlServiceImpl(ShortUrlDAO shortUrlDAO, ShortUrlRedisRepository shortUrlRedisRepository){
    this.shortUrlDAO = shortUrlDAO;
    this.shortUrlRedisRepository = shortUrlRedisRepository;
   
  }

  @Override
  public ShortUrlResponseDto getShortUrl(String accessToken, String originalUrl) {
    LOGGER.info("서비스계층에서 전달받은 url= {}", originalUrl);

    // Cache Logic
    Optional<ShortUrlResponseDto> foundResponseDto = shortUrlRedisRepository.findById(originalUrl);
    if (foundResponseDto.isPresent()) {
      LOGGER.info("DRedisRepository에 데이터가 존재합니다.");
      return foundResponseDto.get();
    } else {
      LOGGER.info("RedisRepository에 데이터가 존재하지 않습니다.");
    }

    ShortUrlEntity getShortUrl = shortUrlDAO.getShortUrl(originalUrl);
    LOGGER.info("DB에서 엔티티를 꺼낸 단축 URL={}", getShortUrl);

    String id="";
    String orgUrl;
    String shortUrl;

    if (getShortUrl == null) {
      LOGGER.info("[getShortUrl] No Entity in Database.");
      ResponseEntity<BitlyUriDto> responseEntity =requestShortUrl(accessToken, originalUrl);

      orgUrl = responseEntity.getBody().getOrgUrl();
      shortUrl = responseEntity.getBody().getShortUrl();
      id = responseEntity.getBody().getId();

      ShortUrlEntity shortUrlEntity = new ShortUrlEntity();
      shortUrlEntity.setId(id);
      shortUrlEntity.setOrgUrl(orgUrl);
      shortUrlEntity.setShortUrl(shortUrl);
     

      shortUrlDAO.saveShortUrl(shortUrlEntity);

    } else {
      orgUrl = getShortUrl.getOrgUrl();
      shortUrl = getShortUrl.getShortUrl();
    }

    ShortUrlResponseDto shortUrlResponseDto = new ShortUrlResponseDto(id, orgUrl, shortUrl);

    shortUrlRedisRepository.save(shortUrlResponseDto);

    LOGGER.info("[getShortUrl] Response DTO : {}", shortUrlResponseDto);
    return shortUrlResponseDto;
  }

  @Override
  public ShortUrlResponseDto generateShortUrl(String accessToken, String originalUrl) {

    LOGGER.info("서비스계층에서 전달된 오리지널 url: {}", originalUrl);

    if (originalUrl.contains("bit.ly")) {
      throw new RuntimeException();
    }
 
    ResponseEntity<BitlyUriDto> responseEntity =requestShortUrl(accessToken, originalUrl);


    String id = responseEntity.getBody().getId(); // 예: bit.ly/abc123
    String orgUrl = responseEntity.getBody().getOrgUrl();
    String shortUrl = responseEntity.getBody().getShortUrl();
   
    

    ShortUrlEntity shortUrlEntity = new ShortUrlEntity();
    shortUrlEntity.setId(id);
    shortUrlEntity.setOrgUrl(orgUrl); 
    shortUrlEntity.setShortUrl(shortUrl);
   

    shortUrlDAO.saveShortUrl(shortUrlEntity);

    ShortUrlResponseDto shortUrlResponseDto = new ShortUrlResponseDto(id, orgUrl, shortUrl);


    // Cache Logic
    shortUrlRedisRepository.save(shortUrlResponseDto);

    LOGGER.info("서비스 계층애서 생성한 DTO : {}", shortUrlResponseDto);
    return shortUrlResponseDto;
  }

  @Override
  public ShortUrlResponseDto updateShortUrl(String accessToken, String originalUrl) {
    return null;
  }

  @Override
  public void deleteShortUrl(String url) {
    if (url.contains("me2.do")) {
      LOGGER.info("[deleteShortUrl] Request Url is 'ShortUrl'.");
      deleteByShortUrl(url);
    } else {
      LOGGER.info("[deleteShortUrl] Request Url is 'OriginalUrl'.");
      deleteByOriginalUrl(url);
    }
  }

  private void deleteByShortUrl(String url) {
    LOGGER.info("[deleteByShortUrl] delete record");
    shortUrlDAO.deleteByShortUrl(url);
  }

  private void deleteByOriginalUrl(String url) {
    LOGGER.info("[deleteByOriginalUrl] delete record");
    shortUrlDAO.deleteByOriginalUrl(url);
  }

  private ResponseEntity<BitlyUriDto> requestShortUrl(String accessToken , String originalUrl) {
    LOGGER.info("[requestShortUrl] accessToken:{}, original URL : {}", accessToken, originalUrl);

        URI uri = UriComponentsBuilder.fromUriString("https://api-ssl.bitly.com/v4/shorten")
                  .encode()
                  .build()
                  .toUri();
    
    LOGGER.info("[requestShortUrl] set HTTP Request Header");
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(new MediaType[] {MediaType.APPLICATION_JSON}));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.add("Authorization", "Bearer " + accessToken);
    headers.add("Content-Type", "application/json");
    
    String requestBody = String.format("{\"long_url\": \"%s\"}", originalUrl);
    HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    RestTemplate restTemplate = new RestTemplate();

    LOGGER.info("[requestShortUrl] request by restTemplate");
    ResponseEntity<BitlyUriDto> responseEntity =restTemplate.exchange(uri, HttpMethod.POST, entity, BitlyUriDto.class);
    //이 uri 주소로 POST 방식의 HTTP 요청을 보내고, entity에 담긴 내용을 보낸 다음, 응답이 오면 그 응답을 BitlyUriDto 클래스 형태로 바꿔서 responseEntity에 담아줘.
    LOGGER.info("[requestShortUrl] request has been successfully complete.");

    return responseEntity;
  }
}
