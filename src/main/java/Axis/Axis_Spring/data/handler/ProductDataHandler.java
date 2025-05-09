package Axis.Axis_Spring.data.handler;
import java.util.List;

import Axis.Axis_Spring.data.entity.ProductEntity;
public interface ProductDataHandler {

    public ProductEntity getProductEntity(String productId) ;
    public List<ProductEntity> getAllProductEntity() ;
    public ProductEntity deleteProductEntity(String productId) ;
    public ProductEntity saveProductEntity(ProductEntity productEntity) ;
    
}
