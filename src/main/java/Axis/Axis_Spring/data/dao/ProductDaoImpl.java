package Axis.Axis_Spring.data.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Axis.Axis_Spring.data.entity.ProductEntity;
import Axis.Axis_Spring.data.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;

//DAO는 Repository를 이용하여 작성한다.
@Repository
public  class ProductDaoImpl implements ProductDao {

    private final ProductRepository productRepository;

    //작성자가 객체를 만들지 않고 스프링이 만들어 낸 객체를 끌어와서 쓴다..의존성주입, 스프링은 싱클톤만 사용하기 때문에 미리 레포지토리객체를 하나 띄워놓고 이 하나를 여러곳에서 사용하는 방식
    //그래서 미리 메모리에 띄워져있는 productRepository 객체를 주입
    @Autowired   
    public ProductDaoImpl(ProductRepository productRepository){
        this.productRepository=productRepository;
    }


    @Override
    public ProductEntity getProduct(String productId) {  //하단 예전방식 참조
        return productRepository.findById(productId)   //findById는 Optional을 넘겨주기 때문에 orElseThrow를 사용하여 예외처리를 해줘야 한다.
                .orElseThrow(() -> new RuntimeException("찾으시는 상품이 없습니다."));
    }

//findById는 Optional로 값을 반환하므로, 반드시 Optional의 메서드(isPresent(), orElse(), orElseThrow() 등)를 통해 값을 안전하게 처리해야 합니다.
//실무에서는 orElseThrow를 많이 사용한다. 이 메서드는 Optional이 비어있을 때 예외를 던지도록 해준다.

    @Override
    public List<ProductEntity> getAllProduct() {
       return productRepository.findAll();  //findAll()은 List를 반환한다.
    }

    @Override
    public ProductEntity deleteProduct(String productId) {
       // 1. 먼저 삭제할 Product 조회
         ProductEntity productEntity = productRepository.findById(productId)
        .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다: " + productId));

        // 2. 그 다음 삭제
        productRepository.delete(productEntity);
        System.out.println("삭제된 상품: " + productId);

        // 3. 삭제 전에 조회한 product 객체를 사용
        return productEntity;
    }

    @Override
    public ProductEntity saveProduct(ProductEntity productEntity){
        productRepository.save(productEntity);  //productEntity를 넘겨주면 DB에 저장, save()는 ProductRepository에는 없지만 조상인터페이스에 있음
        return productEntity;
    }

  
}


    // @Override   예전방식
    // public ProductEntity getProduct(String productId) {
    //     ProductEntity productEntity =productRepository.getById(productId); //productId를 넘겨주고 productEntity를 받아옴, getById() ->ProductRepository에는 없지만 조상인터페이스에 있음
    //     return productEntity;
    // }
