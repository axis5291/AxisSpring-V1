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

    List<ProductEntity> findByProductNameContaining(String productName);  //상품 이름에 특정 문자열이 포함된 상품을 찾는다.

    // 정렬과 페이징
    List<ProductEntity> findByProductNameContainingOrderByProductStockAsc(String productName);  //오름차순
    List<ProductEntity> findByProductNameContainingOrderByProductStockDesc(String productName);  //내림차순

    List<ProductEntity> findByProductNameContainingOrderByProductPriceAscProductStockDesc(String productName);
    
    List<ProductEntity> findByProductNameContaining(String productName, Sort sort); //Sort를 이용한 정렬

    List<ProductEntity> findByProductPriceGreaterThan(Integer price, Pageable pageable);  //페이징 처리
    
     
//비교적 복잡한 쿼리의 경우 @Query 어노테이션을 사용하여 직접 작성할 수 있다.
// @Query 예제 - JPQL (엔티티명과 필드명 정확히 맞춤)
    @Query("SELECT p FROM ProductEntity p WHERE p.productPrice > 2000")
    List<ProductEntity> findByPriceBasis();

    // @Query 예제 - 네이티브 쿼리
    @Query(value = "SELECT * FROM product p WHERE p.price > 2000", nativeQuery = true)  //nativeQuery =true를 사용하면 스프링과 관련없는 일반 SQL 쿼리를 사용할 때 작성
    List<ProductEntity> findByPriceBasisNativeQuery();

    @Query("SELECT p FROM ProductEntity p WHERE p.productPrice > ?1")  // ?는 파라미터를 의미, ?1은 첫 번째 파라미터를 의미한다.
    List<ProductEntity> findByPriceWithParameter(Integer price);

    @Query("SELECT p FROM ProductEntity p WHERE p.productPrice > :price")  // :price 는 파라미터에 이름을 부여하는 방식이다.
    List<ProductEntity> findByPriceWithParameterNaming( Integer price);    // 메서드 파라미터 이름과 JPQL 쿼리에서 사용하는 이름이 동일하면 @Param 없이도 자동 매핑된다.

    @Query("SELECT p FROM ProductEntity p WHERE p.productPrice > :pri")  // 위 쿼리와 의미는 동일하지만, 쿼리에서 사용하는 파라미터 이름(:pri)과 메서드 파라미터 이름(price)이 다르므로
    List<ProductEntity> findByPriceWithParameterNaming2(@Param("pri") Integer price); //"price라는 자바 변수에게 pri라는 JPQL 별명을 붙여준 것

    @Query(value = "SELECT * FROM product WHERE product_price > :product_price", countQuery = "SELECT count(*) FROM product WHERE product_price > ?1", nativeQuery = true)
    List<ProductEntity> findByPriceWithParameterPaging(Integer product_price, Pageable pageable);

}


//JpaRepository<@Entity가 붙은 대상클래스, 대상클래스의 @id필드 데이터타입>로 지정
//JpaRepository<레포지토리가 사용할 엔티티, primary key의 id값 데이터 타입>
//JpaRepository를 사용하게 되면 @Repository을 사용할 필요가 없다, 코드를 작성하지 않아도  기본적인 CRUD메서드를 제공한다.
//이렇게 작성하면 ..쿼리문이 실제로..insert into...식으로 실행이 된다.
