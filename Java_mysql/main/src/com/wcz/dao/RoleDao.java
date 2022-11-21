package com.wcz.dao;

import com.wcz.bean.*;

import java.util.List;

public interface RoleDao {
    List<Role> selectRoleByAll();
    public Role selectRoleByUsernameAndPassWord(String username,String password);

    List<Product> showAllProduct();

    int insertCartById(int product_id, int r_id);

    Tuple<Integer,String> dealCartAndIndent(int user_id, Role bussiness,Cart cart);

    Product selectProductById(int product_id);

    List<Cart> selectAllCartByid(int r_id);

    void delProductFromMyCart(int cart_id);

    Role selectRoleById(int r_id);

    List<Indent> selectAllIndentById(int user_id);

    List<Indent> IndentByIdAndState(int r_id, int state);

    int changeIndentStateByInd_id(String ind_id,int state);

    List<Product> selectProductByKeyWord(String keyWord);

    List<Indent> selectAllIndentByBus_Id(int r_id,int state);

    int UpdatePronumByProId(int pro_id);

    int AddProduct(Product product);

    List<Product> selectProductByBus_id(int r_id);

    int DelProductByProId(int proId);

    int ChangeUserPassword(int userid, String newpassword);
}

