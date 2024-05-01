CREATE TABLE IF NOT EXISTS tb_product_item (
    id INT PRIMARY KEY, -- ID
    name VARCHAR(8) NOT NULL, -- 商品类目名称
    pid INT NOT NULL -- 父级类目SQL
);