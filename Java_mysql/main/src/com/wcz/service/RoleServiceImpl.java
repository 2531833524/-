package com.wcz.service;

import com.wcz.bean.*;
import com.wcz.dao.RoleDao;
import com.wcz.dao.RoleDaoImpl;

import java.util.List;

public class RoleServiceImpl implements RoleService{
    public RoleDao roleDao;
    //通过roleDao调用实现类的返回值
    public RoleServiceImpl(){
        roleDao=new RoleDaoImpl();
    }
    @Override
    public List<Role> selectAllRole(){
        return roleDao.selectRoleByAll();
    }
    @Override
    public Role loginRole(String username,String password){
        return roleDao.selectRoleByUsernameAndPassWord(username,password);
    }
    @Override
    public List<Product> selectAllProduct(){
        return roleDao.showAllProduct();
    }

    @Override
    public int insertProduct(int product_id, int r_id) {
        return roleDao.insertCartById(product_id,r_id);
    }

    @Override
    public Tuple<Integer,String> claerCart(int r_id, Role bussiness,Cart cart) {
        return roleDao.dealCartAndIndent(r_id,bussiness,cart);
    }

    @Override
    public Product selectProductByPro_Id(int product_id) {
        return roleDao.selectProductById(product_id);
    }

    @Override
    public List<Cart> selectAllCharById(int r_id) {
        return roleDao.selectAllCartByid(r_id);
    }

    @Override
    public void delProductFromMyCart(int cart_id) {
        roleDao.delProductFromMyCart(cart_id);
    }

    @Override
    public Role selectrolebyId(int r_id) {
        return roleDao.selectRoleById(r_id);
    }

    @Override
    public List<Indent> selectAllIndentById(int r_id) {
        return roleDao.selectAllIndentById(r_id);
    }

    @Override
    public List<Indent> selectIndentByIdAndState(int r_id, int state) {
        return roleDao.IndentByIdAndState(r_id,state);
    }

    @Override
    public int changeIndentStateByInd_id(String ind_id,int state) {
        return roleDao.changeIndentStateByInd_id(ind_id,state);
    }

    @Override
    public List<Product> selectProductByKeyWord(String keyWord) {
        return roleDao.selectProductByKeyWord(keyWord);
    }

    @Override
    public List<Indent> selectAllIndentByBus_Id(int r_id,int state) {
        return roleDao.selectAllIndentByBus_Id(r_id,state);
    }

    @Override
    public int UpdatePronumByProId(int pro_id) {
        return roleDao.UpdatePronumByProId(pro_id);
    }

    @Override
    public int AddProduct(Product product) {
        return roleDao.AddProduct(product);
    }

    @Override
    public List<Product> selectProductByBus_id(int r_id) {
        return roleDao.selectProductByBus_id(r_id);
    }

    @Override
    public int DelProductByProId(int proId) {
        return roleDao.DelProductByProId(proId);
    }

    @Override
    public int ChangeUserPassword(int userid, String newpassword) {
        return roleDao.ChangeUserPassword(userid,newpassword);
    }
}
