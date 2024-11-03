<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2980b9;
            --background-color: #f5f6fa;
            --text-color: #2d3436;
            --card-background: #ffffff;
            --border-radius: 8px;
            --box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--background-color);
            color: var(--text-color);
            line-height: 1.6;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
        }

        .login-card,
        .product-card,
        .promotion-card {
            background-color: var(--card-background);
            border-radius: var(--border-radius);
            padding: 2rem;
            box-shadow: var(--box-shadow);
            margin-bottom: 2rem;
        }

        .form-group {
            margin-bottom: 1rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
        }

        input {
            width: 100%;
            padding: 0.75rem;
            border: 1px solid #ddd;
            border-radius: var(--border-radius);
            font-size: 1rem;
        }

        button {
            background-color: var(--primary-color);
            color: white;
            padding: 0.75rem 1.5rem;
            border: none;
            border-radius: var(--border-radius);
            cursor: pointer;
            font-size: 1rem;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: var(--secondary-color);
        }

        .price {
            font-size: 1.5rem;
            color: var(--primary-color);
            font-weight: bold;
            margin: 1rem 0;
        }

        .description {
            color: #666;
            margin-bottom: 1rem;
        }

        .menu {
            display: flex;
            gap: 1rem;
            margin-top: 2rem;
        }

        .menu a {
            color: var(--primary-color);
            text-decoration: none;
            padding: 0.5rem 1rem;
            border: 1px solid var(--primary-color);
            border-radius: var(--border-radius);
            transition: all 0.3s;
        }

        .menu a:hover {
            background-color: var(--primary-color);
            color: white;
        }

        .promotion-card .original-price {
            text-decoration: line-through;
            color: #666;
        }

        .promotion-card .discount {
            color: #e74c3c;
            font-weight: bold;
        }

        .promotion-card .final-price {
            font-size: 1.5rem;
            color: #27ae60;
            font-weight: bold;
            margin-top: 1rem;
        }

        h1 {
            margin-bottom: 2rem;
            color: var(--text-color);
    </style>
</head>
<body>
<div class="container">
    <div class="login-card">
        <h1>Login</h1>
        <form action="login" method="post">
            <div class="form-group">
                <label for="login">Usuário:</label>
                <input type="text" id="login" name="login" required>
            </div>
            <div class="form-group">
                <label for="password">Senha:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit">Entrar</button>
        </form>
    </div>
</div>
</body>
</html>