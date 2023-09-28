package cn.shop.dao;

import cn.shop.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author NicholasLD
 * @createTime 2023/9/25 23:44
 */
public class JdbcDaoService {
    private final JdbcDao jdbcDao;
    public JdbcDaoService(){
        this.jdbcDao = new JdbcDao(); //创建与数据库访问对象
    }

    //管理员登录
    public boolean adminLogin(String username, String password) {
        return this.jdbcDao.adminLogin(username, password);
    }

    public boolean adminChangePassword(String username, String newPassword) {
        return this.jdbcDao.adminChangePassword(username, newPassword);
    }

    public List<Customer> listCustomers() {
        return this.jdbcDao.listCustomers();
    }

    public boolean changeCustomerPasswordById(String id, String newPassword) {
        return this.jdbcDao.changeCustomerPasswordById(id, newPassword);
    }

    public boolean deleteCustomerById(String id) {
        return this.jdbcDao.deleteCustomerById(id);
    }

    public Customer queryCustomerById(String id) {
        return this.jdbcDao.queryCustomerById(id);
    }

    public Customer queryCustomerByUsername(String username) {
        return this.jdbcDao.queryCustomerByUsername(username);
    }

    public List<Good> listGoods() {
        return this.jdbcDao.listGoods();
    }

    public boolean addGood(String id, String name, String factory, String date, String model, String purchasePrice, String price, String count) {
        return this.jdbcDao.addGood(id, name, factory, date, model, purchasePrice, price, count);
    }

    public boolean deleteGoodById(String id) {
        return this.jdbcDao.deleteGoodById(id);
    }

    public boolean updateGoodById(String id, String name, String factory, String date, String model, String purchasePrice, String price, String count) {
        return this.jdbcDao.updateGoodById(id, name, factory, date, model, purchasePrice, price, count);
    }

    public boolean register(String username, String password, String phone, String email) {
        Customer customer = this.jdbcDao.queryCustomerByUsername(username);
        if (customer.getId() != null) {
            System.out.println("用户名已存在");
            return false;
        }
        Customer newCustomer = new Customer();
        newCustomer.setUsername(username);
        newCustomer.setPassword(password);
        newCustomer.setRegisterTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        newCustomer.setTotalBuy(BigDecimal.valueOf(0.00));
        newCustomer.setPhone(phone);
        newCustomer.setEmail(email);
        return this.jdbcDao.register(newCustomer);
    }

    public boolean login(String username, String password) {
        Customer customer = this.jdbcDao.queryCustomerByUsername(username);
        if (customer.getId() == null) {
            System.out.println("用户名不存在");
            return false;
        }
        if (!customer.getPassword().equals(password)) {
            System.out.println("密码错误");
            return false;
        }
        return true;
    }

    public boolean addGoodToCart(String username, String id, String count) {
        //通过用户名查询用户是否存在
        Customer customer = this.jdbcDao.queryCustomerByUsername(username);
        if (customer.getId() == null) {
            System.out.println("用户不存在");
            return false;
        }

        // 判断商品是否存在
        Good good = this.jdbcDao.queryGoodById(id);
        if (good.getId() == null) {
            System.out.println("商品不存在");
            return false;
        }

        // 判断商品库存是否足够
        if (good.getCount() < Integer.parseInt(count)) {
            System.out.println("商品库存不足");
            return false;
        }

        // 判断购物车是否已经存在该商品
        if (this.jdbcDao.queryCartByUsernameAndGoodId(customer.getId(), id)) {
            System.out.println("购物车已存在该商品");
            return false;
        }

        // 添加商品到购物车
        return this.jdbcDao.addGoodToCart(customer.getId(), id, count);
    }

    public List<Cart> listCart(String username) {
        Customer customer = this.jdbcDao.queryCustomerByUsername(username);
        if (customer.getId() == null) {
            System.out.println("用户不存在");
            return null;
        }
        return this.jdbcDao.listCart(customer.getId());
    }

    public boolean deleteGoodFromCart(String username, String id) {
        Customer customer = this.jdbcDao.queryCustomerByUsername(username);
        if (customer.getId() == null) {
            System.out.println("用户不存在");
            return false;
        }

        //判断购物车是否存在该商品
        if (!this.jdbcDao.queryCartByUsernameAndGoodId(customer.getId(), id)) {
            System.out.println("购物车不存在该商品");
            return false;
        }

        return this.jdbcDao.deleteGoodFromCart(customer.getId(), id);
    }

    public boolean pay(String username) {
        Customer customer = this.jdbcDao.queryCustomerByUsername(username);
        if (customer.getId() == null) {
            System.out.println("用户不存在");
            return false;
        }

        // 判断购物车是否为空
        if (this.jdbcDao.listCart(customer.getId()).isEmpty()) {
            System.out.println("购物车为空");
            return false;
        }

        // 判断库存是否足够
        List<Cart> carts = this.jdbcDao.listCart(customer.getId());
        for (Cart cart : carts) {
            Good good = this.jdbcDao.queryGoodById(cart.getGoodId().toString());
            if (good.getCount() < cart.getCount()) {
                System.out.println("商品库存不足");
                return false;
            }
        }

        // 扣除库存
        for (Cart cart : carts) {
            if (!this.jdbcDao.updateGoodCountById(cart.getGoodId().toString(), cart.getCount().toString())) {
                System.out.println("扣除库存失败");
                return false;
            }
        }

        // 清空购物车
        if (!this.jdbcDao.clearCart(customer.getId())) {
            System.out.println("清空购物车失败");
            return false;
        }

        //计算总价
        BigDecimal total = BigDecimal.valueOf(0.00);
        for (Cart cart : carts) {
            Good good = this.jdbcDao.queryGoodById(cart.getGoodId().toString());
            total = total.add(good.getSellPrice().multiply(BigDecimal.valueOf(cart.getCount())));
        }

        //添加历史订单
        if (!this.jdbcDao.addHistoryShop(customer.getId(), total, carts)) {
            System.out.println("添加历史订单失败");
            return false;
        }

        // 更新用户购买总额
        if (!this.jdbcDao.updateCustomerTotalBuy(customer.getId(), BigDecimal.valueOf(customer.getTotalBuy().doubleValue()+total.doubleValue()))) {
            System.out.println("更新用户购买总额失败");
            return false;
        }

        //遍历商品，如果有库存为0的商品，则删除该商品
        List<Good> goods = this.jdbcDao.listGoods();
        for (Good good : goods) {
            if (good.getCount() == 0) {
                if (!this.jdbcDao.deleteGoodById(good.getId().toString())) {
                    System.out.println("删除商品失败");
                    return false;
                }
            }
        }

        return true;
    }

    public List<ShopHistory> queryShopHistory(String username) {
        Customer customer = this.jdbcDao.queryCustomerByUsername(username);
        if (customer.getId() == null) {
            System.out.println("用户不存在");
            return null;
        }
        return this.jdbcDao.queryShopHistory(customer.getId());
    }

    public List<ListHistory> queryListHistory(Integer shId) {
        return this.jdbcDao.queryListHistory(shId);
    }

    public boolean updateGoodCountFromCart(String username, String id, String count) {
        Customer customer = this.jdbcDao.queryCustomerByUsername(username);
        if (customer.getId() == null) {
            System.out.println("用户不存在");
            return false;
        }

        //判断购物车是否存在该商品
        if (!this.jdbcDao.queryCartByUsernameAndGoodId(customer.getId(), id)) {
            System.out.println("购物车不存在该商品");
            return false;
        }

        // 判断商品库存是否足够
        Good good = this.jdbcDao.queryGoodById(id);
        if (good.getCount() < Integer.parseInt(count)) {
            System.out.println("商品库存不足");
            return false;
        }

        //如果修改的数量小于等于0，则删除该商品
        if (Integer.parseInt(count) <= 0) {
            return this.jdbcDao.deleteGoodFromCart(customer.getId(), id);
        }

        return this.jdbcDao.updateGoodCountFromCart(customer.getId(), id, count);
    }

    public boolean changeCustomerPasswordByUsername(String username, String newPassword) {
        return this.jdbcDao.changeCustomerPasswordByUsername(username, newPassword);
    }
}
