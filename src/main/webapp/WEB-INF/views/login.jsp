<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Login</title>
</head>
<body>

<h2>Iniciar Sesión</h2>

<form method="post" action="${pageContext.request.contextPath}/login">

    <input type="text" name="username" placeholder="Usuario"><br><br>

    <input type="password" name="password" placeholder="Contraseña"><br><br>

    <button type="submit">Ingresar</button>

</form>

<p style="color:red">
    ${errorLogin}
</p>

</body>
</html>