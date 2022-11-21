package com.wcz.service;

import com.wcz.bean.*;

import java.util.List;

public interface RoleService {
    List<Role> selectAllRole();
    Role loginRole(String username,String password);
    List<Product> selectAllProduct();
    int insertProduct(int product_id, int r_id);

    Tuple<Integer,String> claerCart(int r_id, Role bussiness,Cart cart);

    Product selectProductByPro_Id(int product_id);

    List<Cart> selectAllCharById(int r_id);

    void delProductFromMyCart(int cart_id);
    Role selectrolebyId(int r_id);

    List<Indent> selectAllIndentById(int r_id);

    List<Indent> selectIndentByIdAndState(int r_id, int state);

    int changeIndentStateByInd_id(String ind_id,int state);

    List<Product> selectProductByKeyWord(String keyWord);

    List<Indent> selectAllIndentByBus_Id(int r_id,int state);

    int UpdatePronumByProId(int pro_id);

    int AddProduct(Product product);

    List<Product> selectProductByBus_id(int r_id);

    int DelProductByProId(int proId);

    int ChangeUserPassword(int userid, String newpassword);
}
