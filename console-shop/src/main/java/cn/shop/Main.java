package cn.shop;

import cn.shop.dao.JdbcDaoService;
import cn.shop.entity.*;

import java.util.List;
import java.util.Scanner;

/**
 * @author NicholasLD
 * @createTime 2023/9/25 23:20
 */
public class Main {
    private static final JdbcDaoService jdbcDaoService;
    static Scanner scanner = new Scanner(System.in);

    static {
        jdbcDaoService = new JdbcDaoService();
    }

    public static void main(String[] args) {
        //输出菜单，选择身份：管理员、用户
        while (true) {
            System.out.println("欢迎使用超市管理系统");
            System.out.println("1. 管理员");
            System.out.println("2. 用户");
            System.out.println("3. 退出");
            System.out.println("请选择：");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    admin();
                    break;
                case 2:
                    user();
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("输入错误，请重新输入");
                    break;
            }
        }
    }

    //管理员，登录、密码管理：修改自身密码、修改用户密码、客户管理：列出客户、删除客户、通过ID或用户名查询客户、查询所有客户、商品管理：列出商品、添加商品、删除商品、修改商品信息。
    private static void admin() {
        System.out.println("请输入管理员用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入管理员密码：");
        String password = scanner.nextLine();
        if (jdbcDaoService.adminLogin(username, password)) {
            System.out.println("登录成功");
            //当前登录的管理员用户名
            while (true) {
                System.out.println("1. 密码管理");
                System.out.println("2. 客户管理");
                System.out.println("3. 商品管理");
                System.out.println("4. 退出");
                System.out.println("请选择：");
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("1. 修改自身密码");
                        System.out.println("2. 修改用户密码");
                        System.out.println("3. 退出");
                        System.out.println("请选择：");
                        int choice2 = Integer.parseInt(scanner.nextLine());
                        switch (choice2) {
                            case 1:
                                adminChangePassword(username);
                                break;
                            case 2:
                                listCustomers();
                                System.out.println("请输入要修改密码的ID：");
                                String id = scanner.nextLine();
                                changeCustomerPasswordById(id);
                                break;
                            case 3:
                                System.exit(0);
                                break;
                            default:
                                System.out.println("输入错误，请重新输入");
                                break;
                        }
                        break;
                    case 2:
                        System.out.println("1. 列出客户");
                        System.out.println("2. 删除客户");
                        System.out.println("3. 通过ID查询客户");
                        System.out.println("4. 通过用户名查询客户");
                        System.out.println("5. 退出");
                        System.out.println("请选择：");
                        int choice3 = Integer.parseInt(scanner.nextLine());
                        switch (choice3) {
                            case 1:
                                listCustomers();
                                break;
                            case 2:
                                System.out.println("请输入要删除的客户ID：");
                                String id = scanner.nextLine();
                                deleteCustomerById(id);
                                break;
                            case 3:
                                System.out.println("请输入要查询的客户ID：");
                                String id2 = scanner.nextLine();
                                queryCustomerById(id2);
                                break;
                            case 4:
                                System.out.println("请输入要查询的客户用户名：");
                                String username3 = scanner.nextLine();
                                queryCustomerByUsername(username3);
                                break;
                            case 5:
                                System.exit(0);
                                break;
                            default:
                                System.out.println("输入错误，请重新输入");
                                break;
                        }
                        break;
                    case 3:
                        System.out.println("1. 列出商品");
                        System.out.println("2. 添加商品");
                        System.out.println("3. 删除商品");
                        System.out.println("4. 修改商品信息");
                        System.out.println("5. 退出");
                        System.out.println("请选择：");
                        int choice4 = Integer.parseInt(scanner.nextLine());
                        switch (choice4) {
                            case 1:
                                listGoods();
                                break;
                            case 2:
                                addGood();
                                break;
                            case 3:
                                System.out.println("请输入要删除的商品ID：");
                                String id2 = scanner.nextLine();
                                deleteGoodById(id2);
                                break;
                            case 4:
                                listGoods();
                                updateGoodById();
                                break;
                            case 5:
                                System.exit(0);
                                break;
                            default:
                                System.out.println("输入错误，请重新输入");
                                break;
                        }
                        break;
                    case 4:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("输入错误，请重新输入");
                        break;
                }
            }
        } else {
            System.out.println("登录失败");
        }
    }

    //用户：注册、登录、修改密码、购物：添加商品到购物车、从购物车删除商品、结算、查询购物车、修改商品购买数量、商品付款、查询购物历史记录
    private static void user() {
        System.out.println("1. 注册");
        System.out.println("2. 登录");
        System.out.println("3. 退出");
        System.out.println("请选择：");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                System.exit(0);
                break;
            default:
                System.out.println("输入错误，请重新输入");
                break;
        }
    }

    //管理员修改自身密码
    private static void adminChangePassword(String username) {
        System.out.println("请输入新密码：");
        String newPassword = scanner.nextLine();
        System.out.println("请再次输入新密码：");
        String newPassword2 = scanner.nextLine();
        if (newPassword.equals(newPassword2)) {
            if (jdbcDaoService.adminChangePassword(username, newPassword)) {
                System.out.println("修改成功");
            } else {
                System.out.println("修改失败");
            }
        } else {
            System.out.println("两次输入的密码不一致");
        }
    }

    //列出客户
    private static void listCustomers() {
        List<Customer> customers = jdbcDaoService.listCustomers();
        System.out.println("==================================");
        System.out.println("客户ID\t\t客户姓名\t\t客户等级\t\t注册时间\t\t\t\t\t消费总金额\t\t手机号码\t\t\t邮箱");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        System.out.println("==================================");
    }

    //通过id修改用户密码
    private static void changeCustomerPasswordById(String id) {
        System.out.println("请输入新密码：");
        String newPassword = scanner.nextLine();
        System.out.println("请再次输入新密码：");
        String newPassword2 = scanner.nextLine();
        if (newPassword.equals(newPassword2)) {
            if (jdbcDaoService.changeCustomerPasswordById(id, newPassword)) {
                System.out.println("修改成功");
            } else {
                System.out.println("修改失败");
            }
        } else {
            System.out.println("两次输入的密码不一致");
        }
    }

    //通过ID删除客户
    private static void deleteCustomerById(String id) {
        //给出提示，是否确认删除
        System.out.println("是否确认删除？");
        System.out.println("1. 确认");
        System.out.println("2. 取消");
        System.out.println("请选择：");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                if (jdbcDaoService.deleteCustomerById(id)) {
                    System.out.println("删除成功");
                } else {
                    System.out.println("删除失败");
                }
                break;
            case 2:
                break;
            default:
                System.out.println("输入错误，请重新输入");
                break;
        }
    }

    //通过ID查询客户
    private static void queryCustomerById(String id) {
        System.out.println("==================================");
        System.out.println("客户ID\t\t客户姓名\t\t客户等级\t\t注册时间\t\t\t\t\t消费总金额\t\t手机号码\t\t\t邮箱");
        Customer customer = jdbcDaoService.queryCustomerById(id);
        System.out.println(customer);
        System.out.println("==================================");
    }

    //通过用户名查询客户
    private static void queryCustomerByUsername(String username) {
        System.out.println("==================================");
        System.out.println("客户ID\t\t客户姓名\t\t客户等级\t\t注册时间\t\t\t\t\t消费总金额\t\t手机号码\t\t\t邮箱");
        Customer customer = jdbcDaoService.queryCustomerByUsername(username);
        System.out.println(customer);
        System.out.println("==================================");
    }

    //列出所有商品
    private static void listGoods() {
        List<Good> goods = jdbcDaoService.listGoods();
        System.out.println("==================================");
        System.out.println("商品编号\t\t商品名称\t\t生产厂家\t\t生产日期\t\t\t\t\t型号\t\t\t\t\t进货价\t\t\t\t售价\t\t\t\t数量");
        for (Good good : goods) {
            System.out.println(good);
        }
        System.out.println("==================================");
    }

    //添加商品
    private static void addGood() {
        //商品编号、商品名称、生产厂家、生产日期、型号、进货价、售价、数量
        System.out.println("请输入商品编号：");
        String id = scanner.nextLine();
        System.out.println("请输入商品名称：");
        String name = scanner.nextLine();
        System.out.println("请输入生产厂家：");
        String factory = scanner.nextLine();
        System.out.println("请输入生产日期：");
        String date = scanner.nextLine();
        System.out.println("请输入型号：");
        String model = scanner.nextLine();
        System.out.println("请输入进货价：");
        String purchasePrice = scanner.nextLine();
        System.out.println("请输入售价：");
        String price = scanner.nextLine();
        System.out.println("请输入数量：");
        String count = scanner.nextLine();

        if (jdbcDaoService.addGood(id, name, factory, date, model, purchasePrice, price, count)) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }
    }

    //删除商品
    private static void deleteGoodById(String id) {
        //给出提示，是否确认删除
        System.out.println("是否确认删除？");
        System.out.println("1. 确认");
        System.out.println("2. 取消");
        System.out.println("请选择：");
        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                if (jdbcDaoService.deleteGoodById(id)) {
                    System.out.println("删除成功");
                } else {
                    System.out.println("删除失败");
                }
                break;
            case 2:
                break;
            default:
                System.out.println("输入错误，请重新输入");
                break;
        }
    }

    //修改商品信息
    private static void updateGoodById() {
        System.out.println("请输入商品编号：");
        String id = scanner.nextLine();
        System.out.println("请输入商品名称：");
        String name = scanner.nextLine();
        System.out.println("请输入生产厂家：");
        String factory = scanner.nextLine();
        System.out.println("请输入生产日期：");
        String date = scanner.nextLine();
        System.out.println("请输入型号：");
        String model = scanner.nextLine();
        System.out.println("请输入进货价：");
        String purchasePrice = scanner.nextLine();
        System.out.println("请输入售价：");
        String price = scanner.nextLine();
        System.out.println("请输入数量：");
        String count = scanner.nextLine();

        if (jdbcDaoService.updateGoodById(id, name, factory, date, model, purchasePrice, price, count)) {
            System.out.println("修改成功");
        } else {
            System.out.println("修改失败");
        }
    }

    //用户注册
    private static void register() {
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入手机号：");
        String phone = scanner.nextLine();
        System.out.println("请输入邮箱：");
        String email = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
        System.out.println("请再次输入密码：");
        String password2 = scanner.nextLine();
        if (username.length() < 5){
            System.out.println("用户名长度不能小于5");
            return;
        }
        if (password.equals(password2)) {
            //密码长度大于8，大小写字母数字组合
            if (password.length() < 8) {
                System.out.println("密码长度不能小于8");
                return;
            }
            //正则表达式
            String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
            if (!password.matches(regex)) {
                System.out.println("密码必须包含大小写字母和数字");
                return;
            }

            if (jdbcDaoService.register(username, password, phone, email)) {
                System.out.println("注册成功");
            } else {
                System.out.println("注册失败");
            }
        } else {
            System.out.println("两次输入的密码不一致");
        }
    }

    //用户登录
    private static void login() {
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
        if (jdbcDaoService.login(username, password)) {
            System.out.println("登录成功");
            //当前登录的用户用户名

            while (true) {
                System.out.println("0. 查看购物车");
                System.out.println("1. 添加商品到购物车");
                System.out.println("2. 从购物车删除商品");
                System.out.println("3. 结算");
                System.out.println("4. 修改商品购买数量");
                System.out.println("5. 查询购物历史记录");
                System.out.println("6. 修改密码");
                System.out.println("7. 退出");
                System.out.println("请选择：");
                int choice2 = Integer.parseInt(scanner.nextLine());
                switch (choice2) {
                    case 0:
                        listCart(username);
                        break;
                    case 1:
                        listGoods();

                        addGoodToCart(username);
                        break;
                    case 2:
                        listCart(username);
                        deleteGoodFromCart(username);
                        break;
                    case 3:
                        pay(username);
                        break;
                    case 4:
                        updateGoodCountFromCart(username);
                        break;
                    case 5:
                        queryHistory(username);
                        break;
                    case 6:
                        System.out.println("请输入新密码：");
                        String newPassword = scanner.nextLine();
                        System.out.println("请再次输入新密码：");
                        String newPassword2 = scanner.nextLine();
                        if (newPassword.equals(newPassword2)) {
                            //密码长度大于8，大小写字母数字组合
                            if (newPassword.length() < 8) {
                                System.out.println("密码长度不能小于8");
                                break;
                            }
                            //正则表达式
                            String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
                            if (!newPassword.matches(regex)) {
                                System.out.println("密码必须包含大小写字母和数字");
                                break;
                            }

                            if (jdbcDaoService.changeCustomerPasswordByUsername(username, newPassword)) {
                                System.out.println("修改成功");
                            } else {
                                System.out.println("修改失败");
                            }
                        } else {
                            System.out.println("两次输入的密码不一致");
                        }
                        break;
                    case 7:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("输入错误，请重新输入");
                        break;
                }
            }
        } else {
            System.out.println("登录失败");
        }
    }

    //查看购物车
    private static void listCart(String username) {
        List<Cart> carts = jdbcDaoService.listCart(username);
        System.out.println("==================================");
        System.out.println("商品编号\t\t商品名称\t\t数量\t\t单价\t\t总价");
        for (Cart cart : carts) {
            System.out.println(cart);
        }
        System.out.println("==================================");
    }

    //添加商品到购物车
    private static void addGoodToCart(String username) {
        System.out.println("请输入商品编号：");
        String id = scanner.nextLine();
        System.out.println("请输入购买数量：");
        String count = scanner.nextLine();
        if (jdbcDaoService.addGoodToCart(username, id, count)) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }
    }

    //从购物车删除商品
    private static void deleteGoodFromCart(String username) {
        System.out.println("请输入商品编号：");
        String id = scanner.nextLine();
        if (jdbcDaoService.deleteGoodFromCart(username, id)) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

    //结算
    private static void pay(String username) {
        if (jdbcDaoService.pay(username)) {
            System.out.println("结算成功");
        } else {
            System.out.println("结算失败");
        }
    }

    //查询购物历史记录
    private static void queryHistory(String username) {
        List<ShopHistory> shopHistories = jdbcDaoService.queryShopHistory(username);
        System.out.println("==================================");
        for (ShopHistory shopHistory : shopHistories) {
            System.out.println("订单号\t\t总价\t\t购物时间");
            System.out.println(shopHistory);
            System.out.println("————————————————————————————————————");
            List<ListHistory> carts = jdbcDaoService.queryListHistory(shopHistory.getId());
            for (ListHistory cart : carts) {
                System.out.println(cart);
            }
            System.out.println("==================================");
        }
    }

    //修改购物车商品的数量
    private static void updateGoodCountFromCart(String username) {
        listCart(username);
        System.out.println("请输入商品编号：");
        String id = scanner.nextLine();
        System.out.println("请输入购买数量：");
        String count = scanner.nextLine();
        if (jdbcDaoService.updateGoodCountFromCart(username, id, count)) {
            System.out.println("修改成功");
        } else {
            System.out.println("修改失败");
        }
    }
}
