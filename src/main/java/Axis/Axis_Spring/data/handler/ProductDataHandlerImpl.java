package Axis.Axis_Spring.data.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Axis.Axis_Spring.data.dao.ProductDao;
import Axis.Axis_Spring.data.entity.ProductEntity;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductDataHandlerImpl implements ProductDataHandler {

    private final ProductDao productDao;
    @Autowired
    public ProductDataHandlerImpl(ProductDao productDao){
        this.productDao=productDao;
    }

    @Override  //DB에서 엔티티를 불러오는 단계
    public ProductEntity getProductEntity(String productId) {
       return productDao.getProduct((productId));
    }

    @Override  //DB에서 모든 엔티티를 불러오는 단계
    public List<ProductEntity> getAllProductEntity(){
        return productDao.getAllProduct();
    }

    @Override
    public ProductEntity deleteProductEntity(String productId) {
        return productDao.deleteProduct(productId);
    }

    @Override  //DB에 저장하는 작업
    public ProductEntity saveProductEntity(ProductEntity productEntity) {
              return  productDao.saveProduct(productEntity);
     }
   
}

// @Transactional이 클래스에 붙어 있기 때문에, 이 클래스의 모든 public 메서드는 트랜잭션 범위 안에서 실행
// 🔧 구체적인 역할
// 트랜잭션 시작
// ProductDataHandlerImpl의 메서드가 호출되면 자동으로 트랜잭션이 시작됩니다.

// 정상 실행 시 커밋
// 메서드가 예외 없이 잘 끝나면 자동으로 DB에 커밋됩니다.

// 예외 발생 시 롤백
// 메서드 실행 중 RuntimeException이나 Error가 발생하면 자동으로 롤백됩니다.



// @Override  //DB에 저장하는 작업
// public ProductEntity saveProductEntity(String productId, String productName, int productPrice, int productStock) {
//     ProductEntity productEntity =new ProductEntity(productId, productName, productPrice, productStock);
//    return  productDao.saveProduct(productEntity);
//  }

