package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class DBUtil {

    private static final Logger log = LoggerFactory.getLogger(DBUtil.class);


    public static Connection getConn() throws Exception {
        // 加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 获取链接对象

        String url = "jdbc:mysql://39.106.49.7:3306/books01?characterEncoding=UTF-8";

        return DriverManager.getConnection(url, "root", "Test_123");
    }

    // 释放资源
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 根据指定的sql查询一条数据，封装成map集合
    public static Map<String, Object> getOne(String sql) {

        log.info("sql={}", sql);
        Map<String, Object> result = new HashMap<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    String columnLabel = metaData.getColumnLabel(i);
                    Object value = rs.getObject(i);
                    result.put(columnLabel, value);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(conn);
            close(rs);
            close(ps);
        }
        return result;
    }

    // 执行指定的sql
    public static void execute(String sql) {
        log.info("sql={}", sql);
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConn();
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (Exception e) {
//            e.printStackTrace();
            log.error("error:", e);
        } finally {
            close(ps);
            close(conn);
        }


    }

    public static void main(String[] args) {

        String sql = "select id,title,`read` from t_book where id=1";
        Map<String, Object> one = getOne(sql);
        System.out.println("one=" + one);

//        String sql = "delete from t_book where id=7";
//
//        execute(sql);
    }

}
