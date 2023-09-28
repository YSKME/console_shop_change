package cn.shop.dao;

import cn.shop.entity.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author NicholasLD
 * @createTime 2023/9/25 23:30
 */
public class JdbcDao {
    Logger logger = Logger.getLogger(JdbcDao.class.getName());
    Connection connection = null;
    public void getConnection() {
        try{
            //加载驱动
            Class.forName("org.sqlite.JDBC");
            //创建连接
            connection = DriverManager.getConnection("jdbc:sqlite:shop.sqlite");
        }catch (Exception e){
            logger.info("连接数据库失败");
            e.printStackTrace();
        }
    }

    public boolean adminLogin(String username, String password) {
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from admins where admin_name = ? and admin_password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            return ps.executeQuery().next();
        }catch (Exception e){
            logger.info("管理员登录失败");
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                logger.info("关闭数据库连接失败");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean adminChangePassword(String username, String newPassword) {
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("update admins set admin_password = ? where admin_name = ?");
            ps.setString(1, newPassword);
            ps.setString(2, username);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            logger.info("管理员修改密码失败");
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                logger.info("关闭数据库连接失败");
                e.printStackTrace();
            }
        }
        return false;
    }

    public List<Customer> listCustomers() {
        List<Customer> customers = new ArrayList<>();
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from customers");
            var rs = ps.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("c_id"));
                customer.setUsername(rs.getString("c_name"));
                customer.setLevel(rs.getString("c_level"));
                customer.setRegisterTime(rs.getString("c_regtime"));
                customer.setTotalBuy(rs.getBigDecimal("c_total_money"));
                customer.setPhone(rs.getString("c_phone"));
                customer.setEmail(rs.getString("c_email"));
                customer.setPassword(rs.getString("c_password"));
                customers.add(customer);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return customers;
    }

    public boolean changeCustomerPasswordById(String id, String newPassword) {
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("update customers set c_password = ? where c_id = ?");
            ps.setString(1, newPassword);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            logger.info("管理员修改密码失败");
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                logger.info("关闭数据库连接失败");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteCustomerById(String id) {
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from customers where c_id = ?");
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            logger.info("管理员删除用户失败");
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                logger.info("关闭数据库连接失败");
                e.printStackTrace();
            }
        }
        return false;
    }

    public Customer queryCustomerById(String id) {
        Customer customer = new Customer();
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from customers where c_id = ?");
            ps.setString(1, id);
            var rs = ps.executeQuery();
            while (rs.next()) {
                customer.setId(rs.getInt("c_id"));
                customer.setUsername(rs.getString("c_name"));
                customer.setLevel(rs.getString("c_level"));
                customer.setRegisterTime(rs.getString("c_regtime"));
                customer.setTotalBuy(rs.getBigDecimal("c_total_money"));
                customer.setPhone(rs.getString("c_phone"));
                customer.setEmail(rs.getString("c_email"));
                customer.setPassword(rs.getString("c_password"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return customer;
    }

    public Customer queryCustomerByUsername(String username) {
        Customer customer = new Customer();
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from customers where c_name = ?");
            ps.setString(1, username);
            var rs = ps.executeQuery();
            while (rs.next()) {
                customer.setId(rs.getInt("c_id"));
                customer.setUsername(rs.getString("c_name"));
                customer.setLevel(rs.getString("c_level"));
                customer.setRegisterTime(rs.getString("c_regtime"));
                customer.setTotalBuy(rs.getBigDecimal("c_total_money"));
                customer.setPhone(rs.getString("c_phone"));
                customer.setEmail(rs.getString("c_email"));
                customer.setPassword(rs.getString("c_password"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return customer;
    }

    public List<Good> listGoods() {
        List<Good> goods = new ArrayList<>();
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from goods");
            var rs = ps.executeQuery();
            while (rs.next()) {
                Good good = new Good();
                good.setId(rs.getInt("g_id"));
                good.setName(rs.getString("g_name"));
                good.setFactory(rs.getString("g_factory"));
                good.setDate(rs.getString("g_date"));
                good.setModel(rs.getString("g_model"));
                good.setPurchasePrice(rs.getBigDecimal("g_purchase_price"));
                good.setSellPrice(rs.getBigDecimal("g_sell_price"));
                good.setCount(rs.getInt("g_count"));
                goods.add(good);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return goods;
    }

    public boolean addGood(String id, String name, String factory, String date, String model, String purchasePrice, String price, String count) {
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into goods values (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, factory);
            ps.setString(4, date);
            ps.setString(5, model);
            ps.setString(6, purchasePrice);
            ps.setString(7, price);
            ps.setString(8, count);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            logger.info("管理员添加商品失败");
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                logger.info("关闭数据库连接失败");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteGoodById(String id) {
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from goods where g_id = ?");
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            logger.info("管理员删除商品失败");
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                logger.info("关闭数据库连接失败");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateGoodById(String id, String name, String factory, String date, String model, String purchasePrice, String price, String count) {
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("update goods set g_name = ?, g_factory = ?, g_date = ?, g_model = ?, g_purchase_price = ?, g_sell_price = ?, g_count = ? where g_id = ?");
            ps.setString(1, name);
            ps.setString(2, factory);
            ps.setString(3, date);
            ps.setString(4, model);
            ps.setString(5, purchasePrice);
            ps.setString(6, price);
            ps.setString(7, count);
            ps.setString(8, id);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            logger.info("管理员修改商品失败");
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                logger.info("关闭数据库连接失败");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean register(Customer newCustomer) {
        try {
            getConnection();

            //获取当前最大的 id
            PreparedStatement ps1 = connection.prepareStatement("select max(c_id) from customers");
            var rs = ps1.executeQuery();
            int maxId = 0;
            while (rs.next()) {
                maxId = rs.getInt(1);
            }
            newCustomer.setId(maxId + 1);

            PreparedStatement ps = connection.prepareStatement("insert into customers values (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, newCustomer.getId());
            ps.setString(2, newCustomer.getUsername());
            ps.setString(3, "铜牌客户");
            ps.setString(4, newCustomer.getRegisterTime());
            ps.setBigDecimal(5, newCustomer.getTotalBuy());
            ps.setString(6, newCustomer.getPhone());
            ps.setString(7, newCustomer.getEmail());
            ps.setString(8, newCustomer.getPassword());
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public Good queryGoodById(String id) {
        Good good = new Good();
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from goods where g_id = ?");
            ps.setString(1, id);
            var rs = ps.executeQuery();
            while (rs.next()) {
                good.setId(rs.getInt("g_id"));
                good.setName(rs.getString("g_name"));
                good.setFactory(rs.getString("g_factory"));
                good.setDate(rs.getString("g_date"));
                good.setModel(rs.getString("g_model"));
                good.setPurchasePrice(rs.getBigDecimal("g_purchase_price"));
                good.setSellPrice(rs.getBigDecimal("g_sell_price"));
                good.setCount(rs.getInt("g_count"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return good;
    }

    public boolean queryCartByUsernameAndGoodId(Integer id, String id1) {
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from shopping_cart where c_id = ? and g_id = ?");
            ps.setInt(1, id);
            ps.setString(2, id1);
            return ps.executeQuery().next();
        }catch (Exception e){
            logger.info("查询购物车失败");
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                logger.info("关闭数据库连接失败");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean addGoodToCart(Integer cid, String gid, String count) {
        try {
            getConnection();

            //获取当前最大的 id
            PreparedStatement ps1 = connection.prepareStatement("select max(c_id) from shopping_cart");
            var rs = ps1.executeQuery();
            int maxId = 0;
            while (rs.next()) {
                maxId = rs.getInt(1);
            }
            int sid = maxId + 1;

            PreparedStatement ps = connection.prepareStatement("insert into shopping_cart values (?, ?, ?, ?)");
            ps.setInt(1, sid);
            ps.setString(2, String.valueOf(cid));
            ps.setString(3, gid);
            ps.setString(4, count);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<Cart> listCart(Integer id) {
        List<Cart> carts = new ArrayList<>();
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from shopping_cart where c_id = ?");
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            while (rs.next()) {
                Cart cart = new Cart();
                cart.setId(rs.getInt("sc_id"));
                cart.setCustomerId(rs.getInt("c_id"));
                cart.setGoodId(rs.getInt("g_id"));

                //获取商品单价
                PreparedStatement ps1 = connection.prepareStatement("select g_sell_price from goods where g_id = ?");
                ps1.setInt(1, cart.getGoodId());
                var rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    cart.setPrice(rs1.getBigDecimal("g_sell_price"));
                }

                //获取商品名称
                PreparedStatement ps2 = connection.prepareStatement("select g_name from goods where g_id = ?");
                ps2.setInt(1, cart.getGoodId());
                var rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    cart.setGoodName(rs2.getString("g_name"));
                }

                cart.setCount(rs.getInt("g_count"));
                carts.add(cart);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return carts;
    }

    public boolean deleteGoodFromCart(Integer id, String id1) {
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from shopping_cart where c_id = ? and g_id = ?");
            ps.setInt(1, id);
            ps.setString(2, id1);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            logger.info("管理员删除商品失败");
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                logger.info("关闭数据库连接失败");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateGoodCountById(String gid, String count) {
        try {
            getConnection();
            PreparedStatement ps = connection.prepareStatement("update goods set g_count = g_count - ? where g_id = ?");
            ps.setString(1, count);
            ps.setString(2, gid);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean clearCart(Integer id) {
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from shopping_cart where c_id = ?");
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            logger.info("清空购物车失败");
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                logger.info("关闭数据库连接失败");
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean updateCustomerTotalBuy(Integer id, BigDecimal totalBuy) {
        try {
            getConnection();
            PreparedStatement ps = connection.prepareStatement("update customers set c_total_money = ? where c_id = ?");
            ps.setBigDecimal(1, totalBuy);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean addHistoryShop(Integer cid, BigDecimal totalMoney, List<Cart> carts) {
        try {
            getConnection();

            //获取当前最大的 id
            PreparedStatement ps1 = connection.prepareStatement("select max(h_id) from shop_history");
            var rs = ps1.executeQuery();
            int maxId = 0;
            while (rs.next()) {
                maxId = rs.getInt(1);
            }
            int hid = maxId + 1;

            PreparedStatement ps = connection.prepareStatement("insert into shop_history values (?, ?, ?, ?)");
            ps.setInt(1, hid);
            ps.setString(2, String.valueOf(cid));
            ps.setString(3, totalMoney.toString());
            ps.setString(4, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(java.time.LocalDateTime.now()));

            //获取返回的 id
            PreparedStatement ps2 = connection.prepareStatement("select max(h_id) from shop_history");
            var rs2 = ps2.executeQuery();
            int maxId2 = 0;
            while (rs2.next()) {
                maxId2 = rs2.getInt(1);
            }
            int shid = maxId2 + 1;

            PreparedStatement ps3 = connection.prepareStatement("select max(l_id) from list_history");
            var rs3 = ps3.executeQuery();
            int maxId3 = 0;
            while (rs3.next()) {
                maxId3 = rs3.getInt(1);
            }
            int sid3 = maxId3 + 1;

            for (Cart cart : carts) {
                PreparedStatement ps4 = connection.prepareStatement("insert into list_history values (?, ?, ?, ?)");
                ps4.setInt(1, sid3);
                ps4.setString(2, String.valueOf(shid));
                ps4.setString(3, String.valueOf(cart.getGoodId()));
                ps4.setString(4, String.valueOf(cart.getCount()));
                ps4.executeUpdate();
            }
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public List<ShopHistory> queryShopHistory(Integer id) {
        List<ShopHistory> shopHistories = new ArrayList<>();
        try{
            getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from shop_history where c_id = ?");
            ps.setInt(1, id);
            var rs = ps.executeQuery();
            while (rs.next()) {
                ShopHistory shopHistory = new ShopHistory();
                shopHistory.setId(rs.getInt("h_id"));
                shopHistory.setCustomerId(rs.getInt("c_id"));
                shopHistory.setTotalMoney(rs.getBigDecimal("total_money"));
                shopHistory.setDate(rs.getString("h_time"));
                shopHistories.add(shopHistory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return shopHistories;
    }

    public List<ListHistory> queryListHistory(Integer id) {
        List<ListHistory> listHistories = new ArrayList<>();
        try {
            getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from list_history where sh_id = ?");
            ps.setInt(1, id);
            var rs = ps.executeQuery();



            while(rs.next()){
                ListHistory listHistory = new ListHistory();
                listHistory.setId(rs.getInt("l_id"));
                listHistory.setShopHistoryId(rs.getInt("sh_id"));
                listHistory.setGoodId(rs.getInt("g_id"));
                listHistory.setCount(rs.getInt("g_count"));

                //通过商品id查单价
                PreparedStatement ps1 = connection.prepareStatement("select g_sell_price from goods where g_id = ?");
                ps1.setString(1, String.valueOf(listHistory.getGoodId()));
                var rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    listHistory.setMoney(rs1.getBigDecimal("g_sell_price"));
                }

                //通过商品id查商品名称
                PreparedStatement ps2 = connection.prepareStatement("select g_name from goods where g_id = ?");
                ps2.setString(1, String.valueOf(listHistory.getGoodId()));
                var rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    listHistory.setGoodName(rs2.getString("g_name"));
                }

                listHistories.add(listHistory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listHistories;
    }

    public boolean updateGoodCountFromCart(Integer id, String id1, String count) {
        try {
            getConnection();
            PreparedStatement ps = connection.prepareStatement("update shopping_cart set g_count = ? where c_id = ? and g_id = ?");
            ps.setString(1, count);
            ps.setInt(2, id);
            ps.setString(3, id1);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeCustomerPasswordByUsername(String username, String newPassword) {
        try {
            getConnection();
            PreparedStatement ps = connection.prepareStatement("update customers set c_password = ? where c_name = ?");
            ps.setString(1, newPassword);
            ps.setString(2, username);
            return ps.executeUpdate() > 0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
