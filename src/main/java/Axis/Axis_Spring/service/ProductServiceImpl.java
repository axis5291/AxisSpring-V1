package Axis.Axis_Spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Axis.Axis_Spring.data.dto.ProductDto;
import Axis.Axis_Spring.data.entity.ProductEntity;
import Axis.Axis_Spring.data.handler.ProductDataHandler;

/* ***실무에서는 서비스 계층에서 DTO 변환을 처리하는 것이 일반적이고 좋은 설계*/
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDataHandler productDataHandler;

    @Autowired
   public ProductServiceImpl(ProductDataHandler productDataHandler){
       this.productDataHandler=productDataHandler;
   }

   @Override
   public ProductDto getProduct(String productId) {
       ProductEntity productEntity =productDataHandler.getProductEntity(productId);
       ProductDto productDto=productEntity.toDto(); //ProductEntity에서 ProductDto로 변환하는 메서드
       return productDto;
   }  //맨 아래의 것을 productEntity.toDto()를 이용하여 변환작업을 해주었다.

   @Override   //*내가 만든거
   public List<ProductDto> getProductAll() {
       List<ProductEntity> productAllList = productDataHandler.getAllProductEntity();
       return productAllList.stream()
               .map(ProductEntity::toDto) //ProductEntity에서 ProductDto로 변환하는 메서드
               .toList(); //List<ProductDto>로 변환

    //List<ProductEntity> → List<ProductDto>을 작업함
    //***리스트에서 다른 타입의 값을 담은 새로운 리스트로 변환할 때 stream().map().toList()를 쓰는 거야
   }

   @Override  //*내가 만든거
   public ProductDto deleteProduct(String productId) {
        ProductEntity productEntity = productDataHandler.deleteProductEntity(productId);
        ProductDto productDto=productEntity.toDto(); //ProductEntity에서 ProductDto로 변환하는 메서드
        return productDto;
      
   }
   
    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        try{
            ProductEntity productEntitySaved =productDataHandler.saveProductEntity(productDto.toEntity()); //ProductDto를 ProductEntity로 변환하여 저장
            ProductDto productDtoSaved=productEntitySaved.toDto(); //저장된 ProductEntity를 ProductDto로 변환
            return productDtoSaved;  //ProductDto를 반환하는 이유는 저장이 잘 되었다는 것을 알려주기 위해서 
        }catch (Exception e) {
            throw new RuntimeException("상품 저장에 실패했습니다.", e); //RuntimeException을 던져서 호출한 곳에서 처리하도록 함
        }
    }

}

//    @Override
//    public ProductDto getProduct(String productId) {
//        ProductEntity productEntity =productDataHandler.getProductEntity(productId);
//        ProductDto productDto=new ProductDto(productEntity.getProductId(), productEntity.getProductName(),
//        productEntity.getProductPrice(), productEntity.getProductStock());
//        return productDto;
//    }


// @Override
// public ProductDto saveProduct(String productId, String productName, int productPrice, int productStock) {
//     ProductEntity productEntity =productDataHandler.saveProductEntity(productId, productName, productPrice, productStock);
//     ProductDto productDto=productEntity.toDto(); //ProductEntity에서 ProductDto로 변환하는 메서드
//     return productDto;
// }
