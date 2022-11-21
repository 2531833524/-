package com.wcz.dao;

import com.wcz.bean.*;
import com.wcz.service.RoleService;
import com.wcz.service.RoleServiceImpl;
import com.wcz.utils.JdbcUtils;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RoleDaoImpl implements RoleDao {
    private static Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    @Before
    public void getConnection(){
        conn=JdbcUtils.getConnection();
    }
    @After
    public void closeConnection(){
        JdbcUtils.closeConnection(rs,ps,conn);
    }

    @Override
    public List<Role> selectRoleByAll() {
        conn= JdbcUtils.getConnection();
        //创建集合存放数据
        List roles = new ArrayList<>();
        //编写sql语句
        String sql = "select * from role";
        //防止sql语句携带参数
        try {
            ps = conn.prepareStatement(sql);
            //执行sql语句
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                Role role = new Role();
                role.setR_id(rs.getInt(1));
                role.setR_name(rs.getString(2));
                role.setR_username(rs.getString(3));
                role.setR_password(rs.getString(4));
                role.setPower_id(rs.getInt(5));
                role.setR_address(rs.getString(6));
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }
    @Override
    public Role selectRoleByUsernameAndPassWord(String username,String password){
        conn=JdbcUtils.getConnection();
        Role role=new Role();
        String sql="select * from role where r_username=? and r_password=?";
        try {
            ps =conn.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
            rs=ps.executeQuery();
            if(!rs.next()){
                System.out.println("用户名密码错误");
            }else{
                role.setR_id(rs.getInt(1));
                role.setR_name(rs.getString(2));
                role.setPower_id(rs.getInt(5));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return role;
    }
    @Override
    public List<Product> showAllProduct(){
        List products=new ArrayList();
        String sql="select * from product";
        try {
            ps = conn.prepareStatement(sql);
            //执行sql语句
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                Product product = new Product();
                product.setPro_id(rs.getInt(1));
                product.setPro_name(rs.getString(2));
                product.setPro_price(rs.getDouble(3));
                product.setPro_num(rs.getInt(4));
                product.setPro_info(rs.getString(5));
                product.setPro_good(rs.getInt(6));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public int insertCartById(int product_id, int r_id) {
        int res=0;
        Date date=new Date();
        double money=0;
        String sql="select pro_price from product where pro_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setInt(1,product_id);
            rs= ps.executeQuery();
            if(!rs.next()){
                System.out.println("你没有加入商品");
            }else{
                money=rs.getDouble(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sql="insert into cart(user_id,pro_id,cart_time,cart_money) values(?,?,?,?)";
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,r_id);
            ps.setInt(2,product_id);
            ps.setString(3,df.format(date));
            ps.setDouble(4,money);
            res=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
//    生成账单
//    清空购物车
    @Override
    public Tuple<Integer,String> dealCartAndIndent(int user_id,Role bussiness,Cart cart) {
//      存在的因素 是否有多个商品结算，存在多条账单
//        一个商家一个个地址只要一条记录
//        1.统计购买的数量和金额
//
        RoleService roleService=new RoleServiceImpl();
        int cartNum=0;
        double carMoney=0;
//        订单编号
        String ind_id= "";
        String sql="select sum(cart_money),count(cart_id) from cart where cart_id=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,cart.getCart_id());
            rs=ps.executeQuery();
            if(!rs.next()){
                System.out.println("你尚未添加任何商品，请先添加至购物车");
            }else{
                cartNum=rs.getInt(2);
//                根据不同的商品编号找到不同的商家,进行统计分类(先忽略)
                carMoney=rs.getDouble(1);
//                通过user——id获取地址信息
//                生成订单，然后清楚购物车
                sql="insert into indent(ind_id,user_id,ind_money,ind_state,ind_aads,ind_sads,pro_id,bus_id) values(?,?,?,?,?,?,?,?)";
                Date date=new Date();
                SimpleDateFormat df=new SimpleDateFormat("yyyyMMDDhhmmss");
                ind_id=UUID.randomUUID()+df.format(date);

                ps=conn.prepareStatement(sql);
                ps.setString(1,ind_id);
                ps.setInt(2,user_id);
                ps.setDouble(3,carMoney);
                ps.setInt(4,1);
                ps.setString(5,roleService.selectrolebyId(user_id).getR_address());
                ps.setString(6,bussiness.getR_address());
                ps.setInt(7,cart.getPro_id());
                ps.setInt(8,bussiness.getR_id());
                int intIndent=ps.executeUpdate();
                if(intIndent>0){
//                    清楚购物车
                    sql="delete from cart where cart_id=?";
                    ps=conn.prepareStatement(sql);
                    ps.setInt(1,cart.getCart_id());
                    int cartRes= ps.executeUpdate();
                    if(cartRes!=1){
                        Tuple<Integer,String> a=new Tuple<>(0,"");
                        return a;
                    }else{
                        Tuple<Integer,String> a=new Tuple<>(cartRes,ind_id);
                        return a;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Tuple<Integer,String> a=new Tuple<>(0,"");
        return a;
    }

    @Override
    public Product selectProductById(int product_id) {
        Product product = new Product();
        String sql="select * from product where pro_id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,product_id);
            //执行sql语句
            rs = ps.executeQuery();
            while (rs.next()) {
                product.setPro_id(rs.getInt(1));
                product.setPro_name(rs.getString(2));
                product.setPro_price(rs.getDouble(3));
                product.setPro_num(rs.getInt(4));
                product.setPro_info(rs.getString(5));
                product.setPro_good(rs.getInt(6));
                product.setBus_id(rs.getInt(7));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Cart> selectAllCartByid(int r_id) {
        List carts=new ArrayList();
        String sql="select * from cart where user_id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,r_id);
            //执行sql语句
            rs = ps.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCart_id(rs.getInt(1));
                cart.setUser_id(rs.getInt(2));
                cart.setPro_id(rs.getInt(3));
                cart.setCart_time(rs.getDate(4));
                cart.setCart_money(rs.getDouble(5));
                carts.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return carts;
    }

    @Override
    public void delProductFromMyCart(int cart_id) {
        String sql="delete from cart where cart_id=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,cart_id);
            int res=ps.executeUpdate();
            if(res!=1){
                System.out.println("删除失败！");
            }else{
                System.out.println("删除成功！");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Role selectRoleById(int r_id) {
        Role role = new Role();
        String sql = "select * from role where r_id=?";
        //防止sql语句携带参数
        try {
            ps = conn.prepareStatement(sql);
            //执行sql语句
            ps.setInt(1,r_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                role.setR_id(rs.getInt(1));
                role.setR_name(rs.getString(2));
                role.setR_username(rs.getString(3));
                role.setR_password(rs.getString(4));
                role.setPower_id(rs.getInt(5));
                role.setR_address(rs.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return role;
    }

    @Override
    public List<Indent> selectAllIndentById(int user_id) {
        List<Indent> indents=new ArrayList<>();
        String sql = "select * from indent where user_id=?";
        //防止sql语句携带参数
        try {
            ps = conn.prepareStatement(sql);
            //执行sql语句
            ps.setInt(1,user_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Indent indent=new Indent();
                indent.setInd_id(rs.getString(1));
                indent.setUser_id(rs.getInt(2));
                indent.setInd_aads(rs.getString(3));
                indent.setInd_sads(rs.getString(4));
                indent.setInd_money(rs.getDouble(5));
                indent.setInd_state(rs.getInt(6));
                indent.setPro_id(rs.getInt(7));
                indent.setBus_id(rs.getInt(8));
                indents.add(indent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return indents;
    }

    @Override
    public List<Indent> IndentByIdAndState(int r_id, int state) {
        List<Indent> indents=new ArrayList<>();
        String sql = "select * from indent where user_id=? and ind_state=?";
        //防止sql语句携带参数
        try {
            ps = conn.prepareStatement(sql);
            //执行sql语句
            ps.setInt(1,r_id);
            ps.setInt(2,state);
            rs = ps.executeQuery();
            while (rs.next()) {
                Indent indent=new Indent();
                indent.setInd_id(rs.getString(1));
                indent.setUser_id(rs.getInt(2));
                indent.setInd_aads(rs.getString(3));
                indent.setInd_sads(rs.getString(4));
                indent.setInd_money(rs.getDouble(5));
                indent.setInd_state(rs.getInt(6));
                indent.setPro_id(rs.getInt(7));
                indent.setBus_id(rs.getInt(8));
                indents.add(indent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return indents;
    }

    @Override
    public int changeIndentStateByInd_id(String ind_id,int state) {
        int res=0;
        String sql="update indent set ind_state=? where ind_id=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,state);
            ps.setString(2,ind_id);
            res=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    @Override
    public List<Product> selectProductByKeyWord(String keyWord) {
        List products=new ArrayList();
        String sql="select * from product where pro_name like ?";
        try {
            ps = conn.prepareStatement(sql);
            //执行sql语句
            ps.setString(1,keyWord);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setPro_id(rs.getInt(1));
                product.setPro_name(rs.getString(2));
                product.setPro_price(rs.getDouble(3));
                product.setPro_num(rs.getInt(4));
                product.setPro_info(rs.getString(5));
                product.setPro_good(rs.getInt(6));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Indent> selectAllIndentByBus_Id(int r_id,int state) {
        List<Indent> indents=new ArrayList<>();
        String sql = "select * from indent where bus_id=? and ind_state=?";
        //防止sql语句携带参数
        try {
            ps = conn.prepareStatement(sql);
            //执行sql语句
            ps.setInt(1,r_id);
            ps.setInt(2,state);
            rs = ps.executeQuery();
            while (rs.next()) {
                Indent indent=new Indent();
                indent.setInd_id(rs.getString(1));
                indent.setUser_id(rs.getInt(2));
                indent.setInd_aads(rs.getString(3));
                indent.setInd_sads(rs.getString(4));
                indent.setInd_money(rs.getDouble(5));
                indent.setInd_state(rs.getInt(6));
                indent.setPro_id(rs.getInt(7));
                indent.setBus_id(rs.getInt(8));
                indents.add(indent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return indents;
    }

    @Override
    public int UpdatePronumByProId(int pro_id) {
        int res=0;
        String sql="update product set pro_num=pro_num-1 where pro_id=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,pro_id);
            res=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int AddProduct(Product product) {
        int res=0;
        String sql="insert into product(pro_name,pro_price,pro_num,pro_info,pro_good,bus_id) values(?,?,?,?,?,?)";
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,product.getPro_name());
            ps.setDouble(2,product.getPro_price());
            ps.setInt(3,product.getPro_num());
            ps.setString(4,product.getPro_info());
            ps.setInt(5,product.getPro_good());
            ps.setInt(6,product.getBus_id());
            res=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Product> selectProductByBus_id(int r_id) {
        List<Product> products=new ArrayList<>();

        String sql="select * from product where bus_id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1,r_id);
            //执行sql语句
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setPro_id(rs.getInt(1));
                product.setPro_name(rs.getString(2));
                product.setPro_price(rs.getDouble(3));
                product.setPro_num(rs.getInt(4));
                product.setPro_info(rs.getString(5));
                product.setPro_good(rs.getInt(6));
                product.setBus_id(rs.getInt(7));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public int DelProductByProId(int proId) {
        int res=0;
        String sql="delete from product where pro_id=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setInt(1,proId);
            res=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public int ChangeUserPassword(int userid, String newpassword) {
        int res=0;
        String sql="update role set r_password=? where r_id=?";
        try {
            ps=conn.prepareStatement(sql);
            ps.setString(1,newpassword);
            ps.setInt(2,userid);
            res=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
