-- Создание таблиц
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    text TEXT
);

CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    product_id VARCHAR(36) NOT NULL,
    type VARCHAR(20) NOT NULL, -- DEPOSIT, WITHDRAWAL
    amount DECIMAL(15, 2) NOT NULL,
    transaction_date TIMESTAMP
);

CREATE TABLE IF NOT EXISTS recommendations (
    id VARCHAR(36) PRIMARY KEY,
    product_id VARCHAR(36) NOT NULL,
    rules TEXT NOT NULL
);

-- Вставка продуктов
INSERT INTO products (id, name, type, text) VALUES 
('prod-001', 'Дебетовая карта', 'DEBIT', 'Удобная дебетовая карта с кэшбэком'),
('prod-002', 'Вклад Сохраняй', 'DEPOSIT', 'Выгодный вклад с высокими процентами'),
('prod-003', 'Кредитная карта', 'CREDIT', 'Кредитная карта с льготным периодом'),
('prod-004', 'Ипотека', 'MORTGAGE', 'Выгодная ипотека на выгодных условиях'),
('prod-005', 'Накопительный счёт', 'DEPOSIT', 'Накопительный счёт с процентами');

-- Вставка рекомендаций
INSERT INTO recommendations (id, product_id, rules) VALUES 
('rec-001', 'prod-001', 'Сумма пополнений по всем продуктам типа DEBIT > 100000'),
('rec-002', 'prod-002', '(Сумма пополнений по всем продуктам типа DEBIT > 50000) ИЛИ (Сумма пополнений по всем продуктам типа DEPOSIT > 100000)'),
('rec-003', 'prod-003', 'Сумма пополнений по всем продуктам типа DEBIT > 200000');

-- Вставка пользователей
INSERT INTO users (id, name) VALUES 
('cd515076-5d8a-44be-930e-8d4fcb79f42d', 'Пользователь 1'),
('d4a4d619-9a0c-4fc5-b0cb-76c49409546b', 'Пользователь 2'),
('1f9b149c-6577-448a-bc94-16bea229b71a', 'Пользователь 3');

-- Вставка транзакций для пользователя 1 (рекомендация 1: сумма пополнений DEBIT > 100000)
INSERT INTO transactions (user_id, product_id, type, amount, transaction_date) VALUES 
('cd515076-5d8a-44be-930e-8d4fcb79f42d', 'prod-001', 'DEPOSIT', 50000.00, '2024-01-15 10:00:00'),
('cd515076-5d8a-44be-930e-8d4fcb79f42d', 'prod-001', 'DEPOSIT', 60000.00, '2024-02-15 10:00:00'),
('cd515076-5d8a-44be-930e-8d4fcb79f42d', 'prod-001', 'WITHDRAWAL', 10000.00, '2024-03-15 10:00:00');

-- Вставка транзакций для пользователя 2 (рекомендация 2: сумма DEPOSIT > 100000)
INSERT INTO transactions (user_id, product_id, type, amount, transaction_date) VALUES 
('d4a4d619-9a0c-4fc5-b0cb-76c49409546b', 'prod-002', 'DEPOSIT', 150000.00, '2024-01-20 10:00:00'),
('d4a4d619-9a0c-4fc5-b0cb-76c49409546b', 'prod-002', 'WITHDRAWAL', 20000.00, '2024-02-20 10:00:00');

-- Вставка транзакций для пользователя 3 (рекомендация 3: сумма пополнений DEBIT > 200000)
INSERT INTO transactions (user_id, product_id, type, amount, transaction_date) VALUES 
('1f9b149c-6577-448a-bc94-16bea229b71a', 'prod-001', 'DEPOSIT', 100000.00, '2024-01-25 10:00:00'),
('1f9b149c-6577-448a-bc94-16bea229b71a', 'prod-001', 'DEPOSIT', 120000.00, '2024-02-25 10:00:00'),
('1f9b149c-6577-448a-bc94-16bea229b71a', 'prod-003', 'WITHDRAWAL', 30000.00, '2024-03-25 10:00:00');
