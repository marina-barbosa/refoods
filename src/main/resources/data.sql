





-- Inserindo dados na tabela tb_users
INSERT INTO tb_users (date_creation, email, last_login, name, password, phone)
VALUES -- -- as senhas são 123456
    (NOW(), 'user1@example.com', NOW(), 'User One', '$2y$10$p1IXPiQ29t1mUit/msZV3OY7Dpz2voS6pK4TBQzPEcVC6QbG6cWhe', '1112223333' ),
    (NOW(), 'user2@example.com', NOW(), 'User Two', '$2y$10$p1IXPiQ29t1mUit/msZV3OY7Dpz2voS6pK4TBQzPEcVC6QbG6cWhe', '4445556666' );

-- Inserindo dados na tabela tb_restaurants
INSERT INTO tb_restaurants (average_rating, cnpj, date_creation, email, fantasy, last_login, password, quantity_evaluations, total_evaluations, url_banner, url_logo)
VALUES -- -- as senhas são 123456
    (4.5, '12345678000195', NOW(), 'restaurant1@example.com', 'Restaurant One', NOW(), '$2y$10$p1IXPiQ29t1mUit/msZV3OY7Dpz2voS6pK4TBQzPEcVC6QbG6cWhe', 10, 50, 'banner1.jpg', 'logo1.jpg'),
    (4.0, '98765432000100', NOW(), 'restaurant2@example.com', 'Restaurant Two', NOW(), '$2y$10$p1IXPiQ29t1mUit/msZV3OY7Dpz2voS6pK4TBQzPEcVC6QbG6cWhe', 20, 100, 'banner2.jpg', 'logo2.jpg');

-- Inserindo dados na tabela tb_addresses
INSERT INTO tb_addresses (address_type, cep, complement, district, is_standard, number, state, street, restaurant_id, user_id)
VALUES 
    ('RESTAURANT', '12345678', 'Apto 101', 'Centro', TRUE, '123', 'SP', 'Rua A', 1, NULL),
    ('USER', '87654321', 'Apto 202', 'Jardins', FALSE, '456', 'SP', 'Rua B', NULL, 2);

-- Inserindo dados na tabela tb_cards
INSERT INTO tb_cards (cpf, cvv, number, validity, holder_name, user_id) VALUES 
('40002050000', '123', '4111111111111111', '12/25', 'Nome do Titular 1', 1), 
('11102050110', '456', '5500000000000004', '01/24', 'Nome do Titular 2', 2);

-- Inserindo dados na tabela tb_contacts
INSERT INTO tb_contacts (description, phone, restaurant_id)
VALUES 
    ('Contato Principal', '1112223333', 1),
    ('Contato Alternativo', '4445556666', 2);

-- Inserindo dados na tabela tb_products
INSERT INTO tb_products (active, addition_date, description_product, discount, name_product, category, url_img_product, value_product, expiration_date, quantity, restaurant_id)
VALUES 
    (TRUE, NOW(), 'Delicious sweet product', 10, 'Product One', 'DOCE', 'product1.jpg', 20.0, '2024-11-17', 100, 1),
    (TRUE, NOW(), 'Tasty savory product', 5, 'Product Two', 'SALGADO', 'product2.jpg', 30.0, '2024-11-17', 200, 2);

-- Inserindo dados na tabela tb_cart
INSERT INTO tb_cart (status, total_value, user_id)
VALUES
    ('ABERTO', 0.0, 1),
    ('ABERTO', 0.0, 2);

-- Inserindo dados na tabela tb_cart_items
INSERT INTO tb_cart_items (cart_id, product_id, quantity, unit_value, subtotal)
VALUES 
    (1, 1, 2, 20.0, 40.0),
    (1, 2, 1, 30.0, 30.0),
    (2, 1, 1, 20.0, 20.0),
    (2, 2, 2, 30.0, 60.0);


-- Inserindo dados na tabela tb_orders
INSERT INTO tb_orders (order_date, order_status, total_value, address_id, restaurant_id, user_id)
VALUES 
    (NOW(), 1, 100.0, 1, 1, 1),
    (NOW(), 2, 50.0, 2, 2, 2);

-- Inserindo dados na tabela tb_order_items
INSERT INTO tb_order_items (order_id, product_id, quantity, unit_value, subtotal)
VALUES 
    (1, 1, 2, 20.0, 40.0), -- 2 unidades do Product One no pedido 1
    (1, 2, 1, 30.0, 30.0), -- 1 unidade do Product Two no pedido 1
    (2, 1, 1, 20.0, 20.0), -- 1 unidade do Product One no pedido 2
    (2, 2, 2, 30.0, 60.0); -- 2 unidades do Product Two no pedido 2


-- Inserindo dados na tabela tb_reviews
INSERT INTO tb_reviews (rating_comment, rating_date, rating_note, restaurant_id, user_id)
VALUES 
    ('Great food!', NOW(), 5, 1, 1),
    ('Nice service', NOW(), 4, 2, 2);

-- Inserindo dados na tabela tb_favorites
INSERT INTO tb_favorites (addition_date, restaurant_id, user_id)
VALUES 
    (NOW(), 1, 1),
    (NOW(), 2, 2);

-- Inserindo dados na tabela tb_historical_orders
INSERT INTO tb_historical_orders (date_mod, order_status, order_id, restaurant_id, user_id) 
VALUES 
(NOW(), 'EMPRODUCAO', 1, 1, 1), 
(NOW(), 'ENVIADO', 2, 2, 2); 



-- Inserindo dados na tabela tb_notifications
INSERT INTO tb_notifications (msg_notification, msg_read, send_date, restaurant_id, user_id)
VALUES 
    ('Your order has been delivered.', FALSE, NOW(), 1, 1),
    ('New products available!', TRUE, NOW(), 2, 2);

-- Inserindo dados na tabela tb_transactions
INSERT INTO tb_transactions (transaction_date, transaction_status, transaction_value, order_id, card_id) 
VALUES 
(NOW(), 'PENDENTE', 100.0, 1, 1), 
(NOW(), 'APROVADA', 50.0, 2, 2); 

