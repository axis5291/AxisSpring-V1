package Axis.Axis_Spring.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import Axis.Axis_Spring.data.entity.ProductEntity;
import Axis.Axis_Spring.data.repository.ProductRepository;
import jakarta.transaction.Transactional;


@SpringBootTest
class ProductRepositoryTest {

  @Autowired 
  ProductRepository productRepository;

  @BeforeEach
  void GenerateData() {  //임시 데이터 생성
    int count = 1;
    productRepository.save(getProductEntity(Integer.toString(count), count++, 2000, 3000));
    productRepository.save(getProductEntity(Integer.toString(count), count++, 3000, 3000));
    productRepository.save(getProductEntity(Integer.toString(--count), count = count + 2, 1500, 200));
    productRepository.save(getProductEntity(Integer.toString(count), count++, 4000, 3000));
    productRepository.save(getProductEntity(Integer.toString(count), count++, 10000, 1500));
    productRepository.save(getProductEntity(Integer.toString(count), count++, 10000, 1000));
    productRepository.save(getProductEntity(Integer.toString(count), count++, 500, 10000));
    productRepository.save(getProductEntity(Integer.toString(count), count++, 8500, 3500));
    productRepository.save(getProductEntity(Integer.toString(count), count++, 1000, 2000));
    productRepository.save(getProductEntity(Integer.toString(count), count, 5100, 1700));
  }

  private ProductEntity getProductEntity(String id, int nameNumber, int price, int stock) {
    return new ProductEntity(id, "상품" + nameNumber, price, stock);
  }

  @Test
  void findTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
    System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity ProductEntity : foundAll) {
      System.out.println(ProductEntity.toString());
    }
    System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    List<ProductEntity> foundEntities = productRepository.findByProductName("상품4");

    for (ProductEntity productEntity : foundEntities) {
      System.out.println(productEntity.toString());
    }

    List<ProductEntity> queryEntities = productRepository.queryByProductName("상품4");   // queryByProductName는 findByProductName과 동일한 기능을 한다.

    for (ProductEntity productEntity : queryEntities) {
      System.out.println(productEntity.toString());
    }
  }

  @Test
  void existTest() {   // 존재 여부 확인
    List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
   System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    System.out.println("상품4가 존재? =====>"+productRepository.existsByProductName("상품4"));
    System.out.println("상품2가 존재? =====>"+productRepository.existsByProductName("상품2"));
  }

  @Test
  void countTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
    System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    System.out.println("상품4의 수량 =====>"+productRepository.countByProductName("상품4"));
  }

  @Test
  @Transactional  // 조회를 제외한 나머지는 트랜잭션을 걸어야 한다.
  void deleteTest() {
    System.out.println("삭제전 상품 갯수 : " + productRepository.count());

    productRepository.deleteByProductName("상품1");
    productRepository.removeByProductName("상품9");

    System.out.println("삭제후 남은 갯수 =====>"+ productRepository.count());
  }

  @Test
  void topTest() {
    productRepository.save(getProductEntity("109", 123, 1500, 5000));
    productRepository.save(getProductEntity("101", 123, 2500, 5000));
    productRepository.save(getProductEntity("102", 123, 3500, 5000));
    productRepository.save(getProductEntity("103", 123, 4500, 5000));
    productRepository.save(getProductEntity("104", 123, 1000, 5000));
    productRepository.save(getProductEntity("105", 123, 2000, 5000));
    productRepository.save(getProductEntity("106", 123, 3000, 5000));
    productRepository.save(getProductEntity("107", 123, 4000, 5000));

    List<ProductEntity> foundEntities = productRepository.findFirst5ByProductName("상품123");
    for (ProductEntity productEntity : foundEntities) {
      System.out.println(productEntity.toString());
    }

    List<ProductEntity> foundEntities2 = productRepository.findTop3ByProductName("상품123");
    for (ProductEntity productEntity : foundEntities2) {
      System.out.println(productEntity.toString());
    }
  }

  /* ↓↓ 조건자 키워드 테스트 ↓↓ */

  @Test
  void isEqualsTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
    System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    System.out.println("아이디가 1인 상품 조회  ===>"+productRepository.findByProductId("1"));
    System.out.println("아이디가 1인 상품 조회  ===>"+productRepository.findByProductIdEquals("1"));
  }

  @Test 
  void notTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
   System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    List<ProductEntity> foundEntities = productRepository.findByProductIdIsNot("1");
    for (ProductEntity productEntity : foundEntities) {
      System.out.println(productEntity);
    }
    // System.out.println(ProductEntityRepository.findByProductEntityIdNot("1"));
    System.out.println("아이디가 1을 제외한 상품 조회  ===>"+productRepository.findByProductIdIsNot("1"));
  }

  @Test
  void nullTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
  System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    System.out.println("Stock이 Null인 상품 조회  ===>"+productRepository.findByProductStockIsNull());
    System.out.println("Stock이 Null이 아닌 상품 조회  ===>"+productRepository.findByProductStockIsNotNull());
  }

  @Test
  void andTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
  System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
   System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    System.out.println("아이디 1, 상품1인 것 찾기 ====>" + productRepository.findTopByProductIdAndProductName("1", "상품1"));
  }

  @Test
  void greaterTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
  System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
  System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    List<ProductEntity> ProductEntityEntities = productRepository.findByProductPriceGreaterThan(5000);

    for (ProductEntity productEntity : ProductEntityEntities) {
      
      System.out.println("5000원 이상인 상품:"+productEntity);
      
    }
  }

  @Test
  void containTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
   System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");
    // System.out.println("상품1이 담긴 갯수:"+productRepository.findByProductNameContaining("상품1").size());
    System.out.println("상품1이 담긴 것:"+productRepository.findByProductNameContaining("상품1"));
  }

  /* 정렬과 페이징 */
  @Test
  void orderByTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
    System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
    System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    System.out.println("상품 이름에 '상품'이 포함된 상품을 재고 오름차순으로 정렬**************************");

    List<ProductEntity> foundProductEntities = productRepository.findByProductNameContainingOrderByProductStockAsc("상품");
    for (ProductEntity productEntity : foundProductEntities) {
      System.out.println(productEntity);
    }
    System.out.println("상품 이름에 '상품'이 포함된 상품을 재고 내림차순으로 정렬=============================");
     foundProductEntities = productRepository.findByProductNameContainingOrderByProductStockDesc("상품");
    for (ProductEntity productEntity : foundProductEntities) {
      System.out.println(productEntity);
    }
  }

  @Test
  void multiOrderByTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
    System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
    System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    System.out.println("상품 이름에 '상품'이 포함된 상품을 가격 오름차순, 재고 내림차순으로 정렬**************************");
    List<ProductEntity> foundProductEntitys =
        productRepository.findByProductNameContainingOrderByProductPriceAscProductStockDesc("상품");  // 가격 오름차순을 우선하고  재고 내림차순
    for (ProductEntity productEntity : foundProductEntitys) {
      System.out.println(productEntity);
    }
  }

 @Test
 void orderByWithParameterTest() {
   List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("====↓↓ Test Data ↓↓====");
   for (ProductEntity productEntity : foundAll) {
     System.out.println(productEntity.toString());
   }
   System.out.println("====↑↑ Test Data ↑↑====");

    System.out.println("상품 이름에 '상품'이 포함된 상품을 가격 오름차순으로 정렬**************************");
    List<ProductEntity> foundProductEntitys = productRepository.findByProductNameContaining("상품", Sort.by(Order.asc("productPrice")));
   for (ProductEntity productEntity : foundProductEntitys) {
     System.out.println(productEntity);
   }

    System.out.println("상품 이름에 '상품'이 포함된 상품을 가격 오름차순, 재고 내림차순으로 정렬**************************");  
    foundProductEntitys =  productRepository.findByProductNameContaining("상품", Sort.by(Order.asc("productPrice"), Order.asc("productStock")));
   for (ProductEntity productEntity : foundProductEntitys) {
     System.out.println(productEntity);
   }
 }

 @Test
 void pagingTest() {
   List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("====↓↓ Test Data ↓↓====");
   for (ProductEntity productEntity : foundAll) {
     System.out.println(productEntity.toString());
   }
   System.out.println("====↑↑ Test Data ↑↑====");

    System.out.println("상품 이름에 '상품'이 포함된 상품을 가격이 200원 이상인 상품을 페이징 처리");
   List<ProductEntity> foundProductEntitys = productRepository.findByProductPriceGreaterThan(200, PageRequest.of(0, 2));
   for (ProductEntity productEntity : foundProductEntitys) {
     System.out.println(productEntity);
   }

   System.out.println("상품 이름에 '상품'이 포함된 상품을 가격이 200원 이상인 상품을 페이징 처리, 페이지 1");
   foundProductEntitys = productRepository.findByProductPriceGreaterThan(200, PageRequest.of(4, 2));
   for (ProductEntity productEntity : foundProductEntitys) {
     System.out.println(productEntity);
   }
 }

  @Test
  public void queryTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
    System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
    System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    System.out.println("상품 가격이 2000원 이상인 상품을 조회");   //ProudctRepository에서 findByPriceBasis()메서드에 2000원 이상인 상품을 조회하는 JPQL 쿼리를 작성했다.
    List<ProductEntity> foundProductEntitys = productRepository.findByPriceBasis();
    for (ProductEntity productEntity : foundProductEntitys) {
      System.out.println(productEntity);
    }
  }

  @Test
  public void nativeQueryTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
    System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
    System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");
 
    System.out.println("상품 가격이 2000원 이상인 상품을 조회");   //ProudctRepository에서 findByPriceBasisNativeQuery()메서드에 2000원 이상인 상품을 조회하는 네이티브 쿼리를 작성했다.
    List<ProductEntity> foundProductEntitys = productRepository.findByPriceBasisNativeQuery();
    for (ProductEntity productEntity : foundProductEntitys) {
      System.out.println(productEntity);
    }
  }

  @Test
  public void parameterQueryTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
  System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    System.out.println("상품 가격이 5000원 이상인 상품을 조회****************");  //ProudctRepository에서 findByPriceWithParameter()메서드에 5000원 이상인 상품을 조회하는 JPQL 쿼리를 작성했다.
    List<ProductEntity> foundProductEntitys = productRepository.findByPriceWithParameter(5000);
    for (ProductEntity productEntity : foundProductEntitys) {
      System.out.println(productEntity);
    }
  }

  @Test
  public void parameterNamingQueryTest() {
    List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
  System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    System.out.println("상품 가격이 2000원 이상인 상품을 조회****************");  //ProudctRepository에서 findByPriceWithParameterNaming()메서드에 2000원 이상인 상품을 조회하는 JPQL 쿼리를 작성했다.
    List<ProductEntity> foundProductEntitys = productRepository.findByPriceWithParameterNaming(2000);
    for (ProductEntity productEntity : foundProductEntitys) {
      System.out.println(productEntity);
    }
  }

  @Test
  public void parameterNamingQueryTest2() {
    List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("**********↓↓ 임시 데이터 생성  ↓↓**********");
    for (ProductEntity productEntity : foundAll) {
      System.out.println(productEntity.toString());
    }
   System.out.println("**********↑↑ 생성된 임시데이터 ↑↑**********");

    System.out.println("상품 가격이 2000원 이상인 상품을 조회****************");  //ProudctRepository에서 findByPriceWithParameterNaming2()메서드에 2000원 이상인 상품을 조회하는 JPQL 쿼리를 작성했다.
    List<ProductEntity> foundProductEntitys = productRepository.findByPriceWithParameterNaming2(2000);
    for (ProductEntity productEntity : foundProductEntitys) {
      System.out.println(productEntity);
    }
  }

 @Test
 public void nativeQueryPagingTest() {
   List<ProductEntity> foundAll = productRepository.findAll();
   System.out.println("====↓↓ Test Data ↓↓====");
   for (ProductEntity productEntity : foundAll) {
     System.out.println(productEntity.toString());
   }
   System.out.println("====↑↑ Test Data ↑↑====");

   List<ProductEntity> foundProductEntitys =  productRepository.findByPriceWithParameterPaging(2000, PageRequest.of(2, 2));
     // PageRequest.of(2, 2))  -->1번째 인자:가져올 페이지 번호 (0부터 시작), 2번째 인자:한 페이지에 가져올 데이터의 개수
   for (ProductEntity productEntity : foundProductEntitys) {
     System.out.println(productEntity);
   }
 }

  @Test
  public void basicCRUDTest() {
    /* create */   
    // given
    ProductEntity productEntity =ProductEntity.builder().productId("testProductEntity").productName("testP").productPrice(1000).productStock(500).build();

    // when
    ProductEntity savedEntity = productRepository.save(productEntity);

    // then
    Assertions.assertThat(savedEntity.getProductId()).isEqualTo(productEntity.getProductId());
    Assertions.assertThat(savedEntity.getProductName()).isEqualTo(productEntity.getProductName());
    Assertions.assertThat(savedEntity.getProductPrice()).isEqualTo(productEntity.getProductPrice());
    Assertions.assertThat(savedEntity.getProductStock()).isEqualTo(productEntity.getProductStock());

    /* read */
    // when
    ProductEntity selectedEntity = productRepository.findById("testProductEntity").orElseThrow(RuntimeException::new);

    // then
    Assertions.assertThat(savedEntity.getProductId()).isEqualTo(productEntity.getProductId());
    Assertions.assertThat(savedEntity.getProductName()).isEqualTo(productEntity.getProductName());
    Assertions.assertThat(savedEntity.getProductPrice()).isEqualTo(productEntity.getProductPrice());
    Assertions.assertThat(savedEntity.getProductStock()).isEqualTo(productEntity.getProductStock());
  }


}