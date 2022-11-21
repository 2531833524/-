package com.wcz;

import com.wcz.bean.*;
import com.wcz.service.RoleService;
import com.wcz.service.RoleServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
    //要借助服务器的
    //public static HttpServletRequest request;

    public static void main(String[] args) {
        //JdbcUtils.getConnection();
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("欢迎来到电商系统！！");
            System.out.println("1.登录  2.退出");
            int k=sc.nextInt();
            switch (k){
                case 1:
                    login();
                    break;
                case 2:
                    return;
            }
        }

        //    1、先进行登录,request
        //分用户、管理员和商家  判断登录信息是否正确，在判断power id的值

        // 假如我是用户


        //获取到返回值了  可以对其做任意的操作
//        List<Role> roles=roleService.selectAllRole();
//        System.out.println(roles.toString());
    }

    private static void login() {
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入用户名，密码");
        System.out.print("用户名:");
        String username=sc.next();
        System.out.print("密码:");
        String password=sc.next();
        Role role=roleService.loginRole(username,password);
        switch (role.getPower_id()){
            case 1:
                jumpToAdmin(role);
                break;
            case 2:
                jumpToBusiness(role);
                break;
            case 3:
                jumpToUser(role);
                break;
        }
    }

    private static void jumpToAdmin(Role role) {
        System.out.println("欢迎回来，管理员"+role.getR_name());
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("1.查看商品 2.下架商品 3.查看所有用户信息 4.修改用户密码 5.退出");
            int i=sc.nextInt();
            switch (i){
                case 1:
                    showAllProduct();
                    break;
                case 2:
                    System.out.println("请输入要下架的商品ID");
                    int proId = sc.nextInt();
                    DelProduct(proId);
                    break;
                case 3:
                    List<Role> roles=roleService.selectAllRole();
                    if(!roles.isEmpty()){
                        System.out.println("用户Id" +
                                "\t  " + "用户昵称" +
                                "\t\t" + "用户名" +
                                "\t  "+ "用户密码" +
                                "\t" + "用户权限等级" +
                                "\t" + "用户地址"
                                );
                        for(Role role2:roles){
                            role2.Print();
                        }
                    }else{
                        System.out.println("系统中没有任何用户!");
                    }
                    break;
                case 4:
                    System.out.println("请输入要修改密码的用户ID号码");
                    int userid=sc.nextInt();
                    System.out.println("请输入新的密码");
                    String newpassword= sc.next();
                    ChangeUserPassword(userid,newpassword);
                    break;
                case 5:
                    return;

            }
        }

    }

    private static void ChangeUserPassword(int userid, String newpassword) {
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        int res=roleService.ChangeUserPassword(userid,newpassword);
        if(res==1){
            System.out.println("密码修改成功！");
        }else{
            System.out.println("密码修改失败！！");
        }
    }

    private static void showAllProduct() {
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        List<Product> products=roleService.selectAllProduct();
        if(!products.isEmpty()){
            System.out.println(" 商品Id" +
                    "\t  " + "商品名称" +
                    "\t\t\t        " + "商品价格" +
                    "\t"+ "商品数量 " +
                    "\t  " + "商品信息    " +
                    "\t\t\t  " + "商品评价"+
                    "\t"+"商家Id"
            );
            for(Product product:products){
                product.Print();
            }
        }else{
            System.out.println("系统中没有任何商品！");
        }

    }

    private static void jumpToBusiness(Role role) {
        System.out.println("欢迎回来，商家"+role.getR_name());
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        while(true) {
            System.out.println("1.发货 2.上架商品 3.下架商品 4.我的商品 5.退出");
            int i = sc.nextInt();
            switch (i) {
                case 1:
                    //通过商品id找到商品 减库存 改订单状态
                    DeliverGoods(role.getR_id());
                    break;
                case 2:
                    AddProduct(role.getR_id());
                    break;
                case 3:
                    System.out.println("请输入要下架的商品ID");
                    int proId = sc.nextInt();
                    DelProduct(proId);
                    break;
                case 4:
                    MyGoods(role.getR_id());
                    break;
                case 5:
                    return;
            }
        }
    }

    private static void DelProduct(int proId) {
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        int res=roleService.DelProductByProId(proId);
        if(res==1){
            System.out.println("商品下架成功！");
        }else{
            System.out.println("商品下架失败");
        }
    }

    private static void MyGoods(int r_id) {
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        List<Product> products=roleService.selectProductByBus_id(r_id);
        if(!products.isEmpty()){
            System.out.println(" 商品Id" +
                    "\t  " + "商品名称" +
                    "\t\t\t        " + "商品价格" +
                    "\t"+ "商品数量 " +
                    "\t  " + "商品信息    " +
                    "\t\t\t  " + "商品评价"+
                    "\t"+"商家Id"
            );
            for(Product product:products){
                product.Print();
            }
        }else{
            System.out.println("您还没有任何上架的商品！");
        }
    }

    private static void AddProduct(int r_id) {
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        Product product=new Product();
        System.out.println("请输入商品名称");
        String proname= null;
        try {
            proname = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        product.setPro_name(proname);
        System.out.println("请输入商品价格");
        double proprice= sc.nextDouble();
        product.setPro_price(proprice);
        System.out.println("请输入商品库存量");
        int num= sc.nextInt();
        product.setPro_num(num);
        System.out.println("请输入商品信息");
        String info=sc.next();
        product.setPro_info(info);
        product.setBus_id(r_id);
        product.setPro_good(99999);
        int res=roleService.AddProduct(product);
        if(res==1){
            System.out.println("商品上架成功");
        }else{
            System.out.println("商品上架失败");
        }
    }

    private static void DeliverGoods(int r_id) {
        //通过商品id找到商品 减库存 改订单状态
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        List<Indent> indents=roleService.selectAllIndentByBus_Id(r_id,1);
        if(!indents.isEmpty()){
            for(Indent indent:indents){
                //通过indent.pro_id去减商品库存
                int res1=roleService.UpdatePronumByProId(indent.getPro_id());
                int res2=roleService.changeIndentStateByInd_id(indent.getInd_id(),2);
                if(res1!=1||res2!=1){
                    System.out.println("发货失败");
                }else if(res1==1&&res2==1){
                    System.out.println("发货成功！ 订单号为:"+indent.getInd_id());
                }
            }
        }else{
            System.out.println("没有订单要发货");
        }
    }

    private static void jumpToUser(Role role) {
        //将一些常用的一直存在的数据放入session中,HttpServletRequest request
//        HttpSession session=request.getSession();
//        session.setAttribute("user_id",role.getR_id());
//        System.out.println(session.getAttribute("user_id"));
//
        System.out.println("欢迎回来，用户"+role.getR_name());
        System.out.println("------------在线商城-----------");
//        进入
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        List<Product> products=roleService.selectAllProduct();
        System.out.println(" 商品Id" +
                "\t  " + "商品名称" +
                "\t\t\t        " + "商品价格" +
                "\t"+ "商品数量 " +
                "\t  " + "商品信息    " +
                "\t\t\t  " + "商品评价"+
                "\t"+"商家Id"
        );
        for(Product product:products){
            product.Print();
        }
        while(true) {
            System.out.println("1.查找商品 2.添加购物车 3.前往下单 4.查看购物车 5.把商品移出我的购物车 6.我的订单 7.退出");
            int i = sc.nextInt();
            switch (i) {
                case 1:
                    System.out.println("请输入关键字");
                    String KeyWord= sc.next();
                    KeyWord="%"+KeyWord+"%";
                    List<Product> products1=roleService.selectProductByKeyWord(KeyWord);
                    System.out.println(" 商品Id" +
                            "\t  " + "商品名称" +
                            "\t\t\t        " + "商品价格" +
                            "\t"+ "商品数量 " +
                            "\t  " + "商品信息    " +
                            "\t\t\t  " + "商品评价"+
                            "\t"+"商家Id"
                    );
                    for(Product product:products1){
                        product.Print();
                    }
                    break;
                case 2:
                    addCart(role);
                    break;
                case 3:

                    List<Cart> carts=roleService.selectAllCharById(role.getR_id());
                    if(!carts.isEmpty()){
                        for (Cart cart:carts) {
                            Role business=roleService.selectrolebyId(roleService.selectProductByPro_Id(cart.getPro_id()).getBus_id());
//                            System.out.println(cart.toString()+roleService.selectProductByPro_Id(cart.getPro_id()));
                            goToCheckOut(role,business,cart);
                        }
                    }else{
                        System.out.println("您的购物车为空，快去选一点心仪的商品吧");
                    }

                    break;
                case 4:
                    checkMyCart(role);
                    break;
                case 5:
                    System.out.println("输入删除的商品所在的购物车号");
                    int cart_id=sc.nextInt();
                    delProductFromMyCart(cart_id);
                    break;
                case 6:
                    System.out.println("1.查看所有订单 2.查看已付款未发货订单 3.查看已收货订单 4.确认收货");
                    int j=sc.nextInt();
                    switch(j){
                        case 1:
                            showAllIndent(role.getR_id());
                            break;
                        case 2:
                            showIndentByIdAndState(role.getR_id(),1);
                            break;
                        case 3:
                            showIndentByIdAndState(role.getR_id(), 3);
                            break;
                        case 4:
                            System.out.println("请输入订单号:");
                            String ind_id=sc.next();
                            int res=roleService.changeIndentStateByInd_id(ind_id,3);
                            if(res==1){
                                System.out.println("收货成功");
                            }else{
                                System.out.println("收货失败");
                            }
                            break;
                    }
                    break;
                case 7:
                    return;
            }
        }
//        if(i==1){
//            //加入购物车
//            addCart(role);
//        }else{
//            goToCheckOut(role,0);//product_id暂未使用
//        }

    }

    private static void showIndentByIdAndState(int r_id, int i) {
        RoleService roleService=new RoleServiceImpl();
        List<Indent> indents=roleService.selectIndentByIdAndState(r_id,i);
        if(!indents.isEmpty()){
            for (Indent indent:indents) {
                System.out.println(indent.toString());
            }
        }else{
            System.out.println("您还没有任何订单");
        }
    }

    private static void showAllIndent(int r_id) {
        RoleService roleService=new RoleServiceImpl();
        List<Indent> indents=roleService.selectAllIndentById(r_id);
        if(!indents.isEmpty()){
            for (Indent indent:indents) {
                System.out.println(indent.toString());
            }
        }else{
            System.out.println("您还没有任何订单");
        }
    }

    private static void delProductFromMyCart(int cart_id) {
        RoleService roleService=new RoleServiceImpl();
        roleService.delProductFromMyCart(cart_id);
    }

    private static void checkMyCart(Role role){
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        List<Cart> carts=roleService.selectAllCharById(role.getR_id());
        if(!carts.isEmpty()){
            for (Cart cart:carts) {
                System.out.println(cart.toString()+roleService.selectProductByPro_Id(cart.getPro_id()));
            }
        }else{
            System.out.println("您的购物车为空，快去选一点心仪的商品吧");
        }

    }
//    private static void checkMyCart() {
//        RoleService roleService=new RoleServiceImpl();
//        Scanner sc=new Scanner(System.in);
//        if(!shoplist.isEmpty()){
//            for(int j=0;j<shoplist.size();j++){
//                System.out.println(roleService.selectProductByPro_Id(shoplist.get(j)).toString());
//            }
//        }else{
//            System.out.println("您的购物车为空");
//        }
//    }

    /*单独封装 实现服用*/
    public static ArrayList<Integer> shoplist=new ArrayList<>();
    public static void addCart(Role role){
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        System.out.println("----请选择心仪商品加入购物车----");
        //下单
        System.out.println("输入商品ID:");
        int product_id=sc.nextInt();
        shoplist.add(product_id);
        int res=roleService.insertProduct(product_id,role.getR_id());

        if(res>=1){
            System.out.println("添加成功");
            System.out.println("1.继续添加商品  2.前往下单 3.查看购物车 4.返回");
            int i = sc.nextInt();
            switch (i){
                case 1:
//                  已经加入购物车的内容可以显示出来
                    addCart(role);
                    break;
                case 2:
//                    下单(用户的编号，地址，付款金额，商家发货地址)
                    List<Cart> carts=roleService.selectAllCharById(role.getR_id());
                    if(!carts.isEmpty()){
                        for (Cart cart:carts) {
                            Role business=roleService.selectrolebyId(roleService.selectProductByPro_Id(cart.getPro_id()).getBus_id());
//                            System.out.println(cart.toString()+roleService.selectProductByPro_Id(cart.getPro_id()));
                            goToCheckOut(role,business,cart);
                        }
                    }else{
                        System.out.println("您的购物车为空，快去选一点心仪的商品吧");
                    }

                    break;
                case 3:
                    checkMyCart(role);
                    break;
                case 4:
                    return;

            }
        }else{
            System.out.println("添加失败，请重新选择");
            addCart(role);
        }
    }

    private static void goToCheckOut(Role role, Role bussiness,Cart cart) {
        RoleService roleService=new RoleServiceImpl();
        Scanner sc=new Scanner(System.in);
        Tuple<Integer,String> a=roleService.claerCart(role.getR_id(), bussiness, cart);

        if(a.first>0){
            System.out.println("下单成功,订单号为:"+a.second);
//            如果需要打印订单号，根据user_id去查询订单
        }else{
            System.out.println("下单失败，请重新处理");
        }
    }


}
