package Axis.Axis_Spring.data.repository;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Axis.Axis_Spring.data.entity.ProductEntity;

// 방법 2. 메서드 이름 규칙을 따라 작성하면 Spring Data JPA가 쿼리를 자동으로 생성한다.
//    ***  메서드 이름으로 쿼리를 생성할 때 사용하는 필드명은 반드시 실제 대상 엔티티의 필드명과 일치해야 한다***
//        ex) findByProductName(), findByProductPriceGreaterThan() 등

// 방법 3. @Query 어노테이션을 사용하면 직접 JPQL 또는 네이티브 SQL 쿼리를 작성할 수 있다.
//        - JPQL: 엔티티 기반 쿼리 (예: SELECT p FROM ProductEntity p WHERE p.productPrice > 2000)
//        - nativeQuery = true: 실제 데이터베이스 테이블 기반 쿼리 사용 가능

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    // 기본 조회
    List<ProductEntity> findByProductName(String productName);

    List<ProductEntity> queryByProductName(String productName);

    boolean existsByProductName(String productName);

    long countByProductName(String productName);

    void deleteByProductName(String productName);

    long removeByProductName(String productName);

    // 개수 제한
    List<ProductEntity> findFirst5ByProductName(String productName);

    List<ProductEntity> findTop3ByProductName(String productName);

    // 조건자 키워드
    ProductEntity findByProductId(String productId);

    ProductEntity findByProductIdEquals(String productId);
   
    List<ProductEntity> findByProductIdIsNot(String productId);

    List<ProductEntity> findByProductStockIsNull();

    List<ProductEntity> findByProductStockIsNotNull();

    List<ProductEntity> findTopByProductIdAndProductName(String productId, String productName);  //아이디와 이름이 둘 다 일치하는 상품을 찾는다.

    List<ProductEntity> findByProductPriceGreaterThan(Integer price);

    List<ProductEntity> findByProductNameContaining(String productName);

    // 정렬과 페이징
    List<ProductEntity> findByProductNameContainingOrderByProductStockAsc(String productName);

    List<ProductEntity> findByProductNameContainingOrderByProductStockDesc(String productName);

    List<ProductEntity> findByProductNameContainingOrderByProductPriceAscProductStockDesc(String productName);
    
 
    List<ProductEntity> findByProductNameContaining(String productName, Sort sort);

    List<ProductEntity> findByProductPriceGreaterThan(Integer price, Pageable pageable);

     List<ProductEntity> findByProductPriceWithParameterPaging(Integer price, Pageable pageable);

   
    
    

    // @Query 예제 - JPQL (엔티티명과 필드명 정확히 맞춤)
    @Query("SELECT p FROM ProductEntity p WHERE p.productPrice > 2000")
    List<ProductEntity> findByPriceBasis();

    // @Query 예제 - 네이티브 쿼리
    @Query(value = "SELECT * FROM product p WHERE p.price > 2000", nativeQuery = true)
    List<ProductEntity> findByPriceBasisNativeQuery();

    @Query("SELECT p FROM ProductEntity p WHERE p.productPrice > ?1")
    List<ProductEntity> findByPriceWithParameter(Integer price);

    @Query("SELECT p FROM ProductEntity p WHERE p.productPrice > :price")
    List<ProductEntity> findByPriceWithParameterNaming(@Param("price") Integer price);

    @Query("SELECT p FROM ProductEntity p WHERE p.productPrice > :pri")
    List<ProductEntity> findByPriceWithParameterNaming2(@Param("pri") Integer price);

}


//JpaRepository<@Entity가 붙은 대상클래스, 대상클래스의 @id필드 데이터타입>로 지정
//JpaRepository<레포지토리가 사용할 엔티티, primary key의 id값 데이터 타입>
//JpaRepository를 사용하게 되면 @Repository을 사용할 필요가 없다, 코드를 작성하지 않아도  기본적인 CRUD메서드를 제공한다.
//이렇게 작성하면 ..쿼리문이 실제로..insert into...식으로 실행이 된다.
