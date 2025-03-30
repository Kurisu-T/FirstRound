<html>
<head>
    <title>登录入口</title>
</head>

<body>
<h2>普通用户</h2>
<form action="/users/login" method="post">
    <p>登录</p>
    <p>
        电话：<input type="text" name="phone">
    </p>
    <p>
        密码：<input type="text" name="password">
    </p>
    <p>
        <input type="submit" name="提交">
    </p>
</form>

<form action="/users/register" method="post">
    <p>注册</p>
    <p>
        用户名<input type="text" name="name">
    </p>
    <p>
        电话：<input type="text" name="phone">
    </p>
    <p>
        密码：<input type="text" name="password">
    </p>
    <p>
        <input type="submit" name="提交">
    </p>
</form>

<hr>

<h2>管理员</h2>
<form action="/admin/login" method="post">
    <p>登录</p>
    <p>
        电话：<input type="text" name="phone">
    </p>
    <p>
        密码：<input type="text" name="password">
    </p>
    <p>
        <input type="submit" name="提交">
    </p>
</form>

<form action="/admin/register" method="post">
    <p>注册</p>
    <p>
        用户名<input type="text" name="name">
    </p>
    <p>
        电话：<input type="text" name="phone">
    </p>
    <p>
        密码：<input type="text" name="password">
    </p>
    <p>
        <input type="submit" name="提交">
    </p>
</form>

<hr>

</body>
</html>
