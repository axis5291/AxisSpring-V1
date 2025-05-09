package Axis.Axis_Spring.data.dao;


import java.util.List;

import Axis.Axis_Spring.data.entity.ProductEntity;

public interface ProductDao {

    ProductEntity getProduct(String productId);
    List<ProductEntity> getAllProduct();
    ProductEntity deleteProduct(String productId);
    ProductEntity saveProduct(ProductEntity product);
   

}
