CREATE TABLE app_users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);

CREATE TABLE accounts (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    balance INTEGER NOT NULL,
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_user_account
        FOREIGN KEY (user_id)
        REFERENCES app_users(id)
        ON DELETE CASCADE
);

CREATE TABLE categories (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    icon VARCHAR(255) NOT NULL,
    user_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_user_category
        FOREIGN KEY (user_id)
        REFERENCES app_users(id)
        ON DELETE CASCADE
);

CREATE TABLE transactions (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    amount INTEGER NOT NULL,
    date VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    checked BOOLEAN NOT NULL,
    regularization BOOLEAN NOT NULL,
    account_id UUID NOT NULL,
    category_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    CONSTRAINT fk_account_transaction
        FOREIGN KEY (account_id)
        REFERENCES accounts(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_category_transaction
        FOREIGN KEY (category_id)
        REFERENCES categories(id)
        ON DELETE CASCADE
);
