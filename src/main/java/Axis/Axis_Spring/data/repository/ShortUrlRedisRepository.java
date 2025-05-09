package Axis.Axis_Spring.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import Axis.Axis_Spring.data.dto.ShortUrlResponseDto;

@Repository
public interface ShortUrlRedisRepository extends CrudRepository<ShortUrlResponseDto, String>{

}
